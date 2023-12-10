package hawk.configrator.services;

import hawk.configrator.dtos.QuestionDTO;
import hawk.configrator.entities.QuestionInfo;
import hawk.dtos.ResultMapper;

public interface QuestionService {
	ResultMapper setQuestion(QuestionDTO questionDTO);
	ResultMapper getQuestion();
	//ResultMapper getQuestionByid(Long id);
	QuestionInfo getQuestionByid(Long id);
	QuestionInfo getQuestionByQtag(String code);
	ResultMapper getAllQtag();
	QuestionDTO getByQtag(String qTag);
	ResultMapper deleteQuestion(Long id);
	
}
