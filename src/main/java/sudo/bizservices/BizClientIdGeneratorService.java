
package sudo.bizservices;

import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.dtos.ResultMapper;
import sudo.entities.ClientIdGenerator;
import sudo.jparepositorys.ClientIdGeneratorRepository;
import sudo.services.ClientIdGeneratorService;
import sudo.services.UsersService;
import sudo.utils.HawkResources;

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
	public String genarateClientId(String branchCode) {
		String clientId = null;
		logger.info("genarateClientId method called..." + branchCode);
		try {
			resultMapper = clientService.getuserSession();
			if (branchCode != null && resultMapper.isSessionStatus()
					&& (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
							|| HawkResources.ADMIN.equals(resultMapper.getUserRole()))) {
				Calendar now = Calendar.getInstance();
						ClientIdGenerator clientIdGenerator = clientIdGeneratorRepository.saveAndFlush(new ClientIdGenerator(new Timestamp(System.currentTimeMillis()), resultMapper.getBy(), branchCode));						
						clientIdGenerator.setClientId("HF" +branchCode+String.format("%ty", now) +String.format("%tm", now)+String.format("%02d",(clientIdGenerator.getId())));
						clientIdGenerator = clientIdGeneratorRepository.saveAndFlush(clientIdGenerator);
						return clientIdGenerator.getClientId();
			}
		} catch (Exception e) {
			logger.error("while getting error  on  deleteEnquiry>>>> " + e.getMessage());
		}
		return clientId;
	}

}
