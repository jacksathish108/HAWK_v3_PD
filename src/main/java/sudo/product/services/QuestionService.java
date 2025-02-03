package sudo.product.services;

import java.util.List;

import sudo.product.dtos.QuestionDTO;
import sudo.product.entities.QuestionInfo;
import sudo.user.dtos.ResultMapper;

public interface QuestionService {
	ResultMapper getQuestion();
	QuestionInfo getQuestionByid(Long id);
	QuestionInfo getQuestionByQtag(String code);
	ResultMapper getAllQtag();
	ResultMapper getQtagsByElementType(String elementType);
	QuestionDTO getByQtag(String qTag);
	
}
