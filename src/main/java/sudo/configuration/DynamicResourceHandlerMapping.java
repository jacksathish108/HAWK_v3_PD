package sudo.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudo.configrator.bizservices.BizQuestionService;
import sudo.configrator.dtos.QuestionDTO;
import sudo.utils.CommonUtil;

import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Order(0)
public class DynamicResourceHandlerMapping implements HandlerMapping {

    private static final Logger logger = LoggerFactory.getLogger(DynamicResourceHandlerMapping.class);

    private final Map<String, ResourceHttpRequestHandler> handlers = new ConcurrentHashMap<>();

    @Autowired
    private BizQuestionService questionService;

    @PostConstruct
    public void preloadDirectories() {
        List<QuestionDTO> fileQuestions = questionService.getQtagsByDataType("File", 1);
        for (QuestionDTO dto : fileQuestions) {
            try {
                registerResourceHandler(dto);
            } catch (MalformedURLException e) {
                logger.error("Failed to map file resource for DTO: {}", dto.getName(), e);
            }
        }
    }

    /**
     * Registers a handler for serving static file resources based on the DTO.
     */
    public void registerResourceHandler(QuestionDTO dto) throws MalformedURLException {
        if (!"File".equalsIgnoreCase(dto.getDataType()) || dto.getElementType() == null) {
            return;
        }

        String dirPath = CommonUtil.extractAttributes(dto.getAttributes());
        if (dirPath == null || dirPath.isEmpty()) {
            logger.warn("Missing 'dir' attribute for DTO: {}", dto.getName());
            return;
        }

        // Ensure directory path ends with /
        if (!dirPath.endsWith("/")) {
            dirPath += "/";
        }

        String resourceLocation = "file:/" + dirPath;
        String pathPrefix = "/files/" + dto.getQTag() + "/";

        addMapping(pathPrefix, resourceLocation);
    }

    /**
     * Extracts the 'dir' value from a comma-separated key=value attribute string.
     */



    /**
     * Adds a resource handler for a specific URL path prefix.
     */
    public void addMapping(String pathPrefix, String resourceLocation) throws MalformedURLException {
        UrlResource urlResource = new UrlResource(resourceLocation);
        if (!urlResource.exists() || !urlResource.isFile()) {
            logger.warn("Resource path does not exist or is not a file: {}", resourceLocation);
            return;
        }

        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
        handler.setLocations(Collections.singletonList(urlResource));
        handler.setCacheSeconds(3600); // 1 hour cache

        try {
            handler.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize ResourceHttpRequestHandler", e);
        }

        handlers.put(pathPrefix, handler);
        logger.info("Mapped dynamic file handler: {} -> {}", pathPrefix, resourceLocation);
    }

    /**
     * Intercepts requests and maps them to dynamically registered handlers.
     */
    @Override
    @Nullable
    public HandlerExecutionChain getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        for (Map.Entry<String, ResourceHttpRequestHandler> entry : handlers.entrySet()) {
            String pathPrefix = entry.getKey();

            if (requestURI.startsWith(pathPrefix)) {
                String relativePath = requestURI.substring(pathPrefix.length());
                if (relativePath.startsWith("/")) {
                    relativePath = relativePath.substring(1);
                }

                request.setAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/" + relativePath);
                return new HandlerExecutionChain(entry.getValue());
            }
        }

        return null;
    }
}
