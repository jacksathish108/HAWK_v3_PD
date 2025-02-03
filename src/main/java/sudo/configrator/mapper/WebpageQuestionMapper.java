package sudo.configrator.mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WebpageQuestionMapper {

	private Long id;

public WebpageQuestionMapper(Long id) {
		super();
		this.id = id;
	}
	
}
