
package sudo.configrator.bizservices;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudo.configrator.entities.QtagGenerator;
import sudo.configrator.jparepositorys.QtagGeneratorRepository;
import sudo.configrator.services.QtagGeneratorService;
import sudo.dtos.ResultMapper;
import sudo.services.UsersService;
import sudo.utils.HawkResources;

@Service
public class BizQtagGeneratorService implements QtagGeneratorService {
	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(BizQtagGeneratorService.class);

	@Autowired
	UsersService clientService;
	@Autowired
	QtagGeneratorRepository qtagGeneratorRepository;
	@Autowired
	UsersService usersService;
	ResultMapper resultMapper;

	@Override
	public String genarateQtag(String priFixCode) {
		logger.info("genarateClientId method called..." + priFixCode);
		try {
			resultMapper = clientService.getuserSession();
			if (priFixCode != null && resultMapper.isSessionStatus()
					&& (HawkResources.SUPPERUSER.equals(resultMapper.getUserRole())
							|| HawkResources.PDADMIN.equals(resultMapper.getUserRole()))) {

				QtagGenerator clientIdGenerator = qtagGeneratorRepository.saveAndFlush(
						new QtagGenerator(new Timestamp(System.currentTimeMillis()), resultMapper.getBy(), priFixCode));
				clientIdGenerator.setQtag(priFixCode +"00" +(clientIdGenerator.getId()));
				clientIdGenerator = qtagGeneratorRepository.saveAndFlush(clientIdGenerator);
				return clientIdGenerator.getQtag();
			}
		} catch (Exception e) {
			logger.error("while getting error  on  deleteEnquiry>>>> " + e.getMessage());
		}
		return null;
	}

}
