package hawk.configrator.services;

import hawk.configrator.dtos.QuestionDTO;
import hawk.dtos.ResultMapper;

public interface QuestionService {
	ResultMapper setQuestion(QuestionDTO questionDTO);
	ResultMapper getQuestion();
	ResultMapper getQuestionByid(Long id);
	QuestionDTO getQuestionByQtag(String code);
	ResultMapper deleteQuestion(Long id);
	
}
