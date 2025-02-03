package sudo.security;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import sudo.dtos.ResultMapper;
import sudo.dtos.UserDetails;
import sudo.services.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	   private UsersService userService;

	    public CustomSuccessHandler(UsersService userService) {
	        this.userService = userService;
	    }

	private static final Logger LOG = LoggerFactory.getLogger(CustomSuccessHandler.class);
	private final GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
	boolean isLocal;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// if redirected from some specific url, need to remove the cachedRequest to
		// force use defaultTargetUrl
		//final RequestCache requestCache = new HttpSessionRequestCache();
		//final SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (!isAdminAuthority(authentication)) {
			String targetUrl = super.determineTargetUrl(request, response);
			// this logic is only for demo purpose, please do not use it on production
			// application.
			if (StringUtils.isBlank(targetUrl) || StringUtils.equals(targetUrl, "/")) {
				UserDetails userDetails = null;
				 isLocal=false;
				 Collections.unmodifiableSet(new HashSet<>(authentication.getAuthorities())).forEach(attribute->{
			if(attribute.toString().equals("USER"))
				isLocal=true;
			});
				 if(isLocal)
				 {
					 userDetails= userService.setuserSession(authentication.getName()).getUserDetails();
				 }
				 else
				 {
					 OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
						Map<String, Object> authenticationTokenMap = authenticationToken.getPrincipal().getAttributes();	
			if(authenticationTokenMap!=null&&authenticationTokenMap.get("email")!=null)
			{
				ResultMapper client_session = userService.setuserSession(authenticationTokenMap.get("email").toString());
			if(client_session.isSessionStatus())
			{
				userDetails=client_session.getUserDetails();	
				userService.attendanceEntry(0, request.getRemoteHost(), request.getSession().getId());
				targetUrl = "/home"; // we can build custom logic
			}
			else
			{
				invalidateSession(request, response);
				clearAuthenticationAttributes(request);
				request.setAttribute("status", "1");
				LOG.info("Redirecting customer to the following location {} ", "/");
				redirectStrategy.sendRedirect(request, response, "/");
				clearAuthenticationAttributes(request);
			}
			} 
				 }
						}
			//clearAuthenticationAttributes(request);
			LOG.info("Redirecting customer to the following location {} ", targetUrl);
			redirectStrategy.sendRedirect(request, response, targetUrl);

			// You can let Spring security handle it for you.
			// super.onAuthenticationSuccess(request, response, authentication);

		} else {
			// we invalidating the session for the admin user.
			invalidateSession(request, response);
			clearAuthenticationAttributes(request);
			request.setAttribute("status", "1");
			LOG.info("Redirecting customer to the following location {} ", "/");
			redirectStrategy.sendRedirect(request, response, "/");
			 super.onAuthenticationSuccess(request, response, authentication);

		}
	//	clearAuthenticationAttributes(request);
	}

	protected void invalidateSession(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		SecurityContextHolder.getContext().setAuthentication(null);
		request.getSession().invalidate();
		redirectStrategy.sendRedirect(request, response, "login");
	}

	protected boolean isAdminAuthority(final Authentication authentication) {
		return CollectionUtils.isNotEmpty(authentication.getAuthorities())
				&& authentication.getAuthorities().contains(adminAuthority);
	}
}