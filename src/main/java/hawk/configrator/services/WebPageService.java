package hawk.configrator.services;

import java.util.List;

import hawk.configrator.dtos.WebPageInfoDTO;
import hawk.configrator.entities.WebPageInfo;
import hawk.dtos.ResultMapper;
public interface WebPageService {
	ResultMapper setWebPage(WebPageInfoDTO webPageInfoDTO);
	ResultMapper getWebPage();
	ResultMapper getPageCode();
	ResultMapper getWebPageByid(Long id);
	WebPageInfo getWebPageInfoByid(Long id);
	ResultMapper getWebPageInfoByCode(String code);
	ResultMapper deleteWebPage(Long id);
	ResultMapper getAllWebPageCode();
	
}
