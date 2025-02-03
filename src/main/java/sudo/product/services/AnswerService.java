package sudo.product.services;

import java.util.List;
import java.util.Map;

import sudo.product.dtos.AnswerDTO;
import sudo.product.dtos.ListViewAnswerDTO;
import sudo.user.dtos.ResultMapper;

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
