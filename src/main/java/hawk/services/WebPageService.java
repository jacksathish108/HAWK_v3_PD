package hawk.services;

import hawk.dtos.ResultMapper;
import hawk.dtos.WebPageInfoDTO;
import hawk.entities.WebPageInfo;

public interface WebPageService {
	ResultMapper setWebPage(WebPageInfoDTO webPageInfoDTO);
	ResultMapper getWebPage();
	ResultMapper getWebPageByid(Long id);
	WebPageInfo getWebPageInfoByid(Long id);
	WebPageInfo getWebPageInfoByCode(String code);
	ResultMapper deleteWebPage(Long id);
	
}
