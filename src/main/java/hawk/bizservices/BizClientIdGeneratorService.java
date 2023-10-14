
package hawk.bizservices;

import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hawk.dtos.ResultMapper;
import hawk.entities.ClientIdGenerator;
import hawk.jparepositorys.ClientIdGeneratorRepository;
import hawk.services.ClientIdGeneratorService;
import hawk.services.UsersService;
import hawk.utils.HawkResources;

@Service
public class BizClientIdGeneratorService implements ClientIdGeneratorService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizClientIdGeneratorService.class);

	@Autowired
	UsersService clientService;
	@Autowired
	ClientIdGeneratorRepository clientIdGeneratorRepository;
	@Autowired
	UsersService usersService;
	ResultMapper resultMapper;

	@Override
	public String genarateClientId(String WebPageCode) {
		String clientId = null;
		logger.info("genarateClientId method called..." + WebPageCode);
		try {
			resultMapper = clientService.getuserSession();
			if (WebPageCode != null && resultMapper.isSessionStatus()
					&& (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
							|| HawkResources.ADMIN.equals(resultMapper.getUserRole()))) {
				Calendar now = Calendar.getInstance();
						ClientIdGenerator clientIdGenerator = clientIdGeneratorRepository.saveAndFlush(new ClientIdGenerator(new Timestamp(System.currentTimeMillis()), resultMapper.getBy(), WebPageCode));						
						clientIdGenerator.setClientId("HF" +WebPageCode+String.format("%ty", now) +String.format("%tm", now)+String.format("%02d",(clientIdGenerator.getId())));
						clientIdGenerator = clientIdGeneratorRepository.saveAndFlush(clientIdGenerator);
						return clientIdGenerator.getClientId();
			}
		} catch (Exception e) {
			logger.error("while getting error  on  deleteEnquiry>>>> " + e.getMessage());
		}
		return clientId;
	}

}
