package sudo.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnswersDTO {
	Long answerInfo_Id;
	String qTag;
	String type;
	String ansValue;

	public AnswersDTO(long answerInfo_Id, String qTag, String ansValue) {
		super();
		this.answerInfo_Id = answerInfo_Id;
		this.qTag = qTag;
		this.ansValue = ansValue;
	}
}
