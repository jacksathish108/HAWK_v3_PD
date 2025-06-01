package sudo.configrator.services;

import java.util.List;

import sudo.configrator.dtos.QuestionDTO;
import sudo.configrator.entities.QuestionInfo;
import sudo.dtos.ResultMapper;

public interface QuestionService {
	ResultMapper setQuestion(QuestionDTO questionDTO);
	ResultMapper setQuestion(List<QuestionDTO> questionDTO);
	ResultMapper getQuestion();
	//ResultMapper getQuestionByid(Long id);
	QuestionInfo getQuestionByid(Long id);
	QuestionInfo getQuestionByQtag(String code);
	ResultMapper getAllQtag();
	ResultMapper getQtagsByElementType(String elementType);
	ResultMapper getQtagsByAutoGenerate();
	QuestionDTO getByQtag(String qTag);
	ResultMapper deleteQuestion(Long id);
	
}
