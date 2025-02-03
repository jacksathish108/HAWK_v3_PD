package sudo.product.services;


import sudo.product.dtos.WebPageInfoDTO;
import sudo.product.entities.WebPageInfo;
import sudo.user.dtos.ResultMapper;
public interface WebPageService {
	ResultMapper getWebPage();
	ResultMapper getPageCode();
	ResultMapper getWebPageByid(Long id);
	WebPageInfo getWebPageInfoByid(Long id);
	ResultMapper getWebPageInfoByCode(String code);
	ResultMapper getAllWebPageCode();
	WebPageInfoDTO getWebPageInfoByCode_1(String code);
}
