package sudo.product.services;

import java.util.List;
import java.util.Map;

import sudo.configrator.dtos.ListViewAnswerDTO;
import sudo.dtos.ResultMapper;
import sudo.product.dtos.AnswerDTO;

public interface AnswerService {
	ResultMapper setAnswer(Map answerMap);
	ResultMapper getAnswer();
	//ResultMapper getAnswerByid(Long id);
	ResultMapper getAnswersByViewId(Long pageId,Long viewId);
	ResultMapper deleteAnswer(Long id);
	ResultMapper getAnswersByQtag(String Qtag);
	List<ListViewAnswerDTO> getAnswersByListViewTargetQtags(List targetQtags);
	List<AnswerDTO> getAnswerListsByViewId(Long pageId,Long viewId);
	
}
