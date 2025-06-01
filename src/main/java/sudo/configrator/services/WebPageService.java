package sudo.configrator.services;

import java.util.List;

import sudo.configrator.dtos.WebPageInfoDTO;
import sudo.configrator.entities.WebPageInfo;
import sudo.dtos.ResultMapper;
public interface WebPageService {
	ResultMapper setWebPage(WebPageInfoDTO webPageInfoDTO);
	ResultMapper getWebPage();
	ResultMapper getPageCode();
	ResultMapper getWebPageByid(Long id);
	WebPageInfo getWebPageInfoByid(Long id);
	ResultMapper getWebPageInfoByCode(String code);
	ResultMapper deleteWebPage(Long id);
	ResultMapper getAllWebPageCodeAndId();
	WebPageInfoDTO getWebPageInfoByCode_1(String code);
	
}
