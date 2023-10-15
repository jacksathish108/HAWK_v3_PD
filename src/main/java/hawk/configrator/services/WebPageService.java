package hawk.configrator.services;

import hawk.configrator.dtos.WebPageInfoDTO;
import hawk.configrator.entities.WebPageInfo;
import hawk.dtos.ResultMapper;
public interface WebPageService {
	ResultMapper setWebPage(WebPageInfoDTO webPageInfoDTO);
	ResultMapper getWebPage();
	ResultMapper getWebPageByid(Long id);
	WebPageInfo getWebPageInfoByid(Long id);
	WebPageInfo getWebPageInfoByCode(String code);
	ResultMapper deleteWebPage(Long id);
	
}
