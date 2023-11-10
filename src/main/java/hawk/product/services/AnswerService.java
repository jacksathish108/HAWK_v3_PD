package hawk.product.services;

import java.util.Map;

import hawk.dtos.ResultMapper;

public interface AnswerService {
	ResultMapper setAnswer(Map answerMap);
	ResultMapper getAnswer();
	//ResultMapper getAnswerByid(Long id);
	ResultMapper getAnswersByViewId(Long pageId,Long viewId);
	ResultMapper deleteAnswer(Long id);
	
}
