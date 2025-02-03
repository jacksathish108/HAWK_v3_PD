package sudo.user.services;

import sudo.user.dtos.ResultMapper;
import sudo.user.entities.FieldUpdateHistoryInfo;

public interface FieldUpdateHistoryService {
	ResultMapper setFieldUpdateHistory(FieldUpdateHistoryInfo fieldUpdateHistoryInfo);
	ResultMapper getFieldUpdateHistoryList(String module,Long id);
	ResultMapper deleteFieldUpdateHistoryList(String module,Long id);
	
}
