package hawk.services;

import hawk.dtos.ResultMapper;
import hawk.entities.FieldUpdateHistoryInfo;

public interface FieldUpdateHistoryService {
	ResultMapper setFieldUpdateHistory(FieldUpdateHistoryInfo fieldUpdateHistoryInfo);
	ResultMapper getFieldUpdateHistoryList(String module,Long id);
	ResultMapper deleteFieldUpdateHistoryList(String module,Long id);
	
}
