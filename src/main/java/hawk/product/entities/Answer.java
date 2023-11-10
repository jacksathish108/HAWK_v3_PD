package hawk.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class Answer {
	String qTag;
	String type;
	String ansValue;
	
}
