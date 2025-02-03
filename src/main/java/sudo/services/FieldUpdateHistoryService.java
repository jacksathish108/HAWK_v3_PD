package sudo.services;

import sudo.dtos.ResultMapper;
import sudo.entities.FieldUpdateHistoryInfo;

public interface FieldUpdateHistoryService {
	ResultMapper setFieldUpdateHistory(FieldUpdateHistoryInfo fieldUpdateHistoryInfo);
	ResultMapper getFieldUpdateHistoryList(String module,Long id);
	ResultMapper deleteFieldUpdateHistoryList(String module,Long id);
	
}
