/*
 * 
 */
package sudo.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResultMapper implements  Serializable  {


/**
	 * 
	 */
	private static final long serialVersionUID = 2969738650699775506L;
int count;
boolean sessionStatus;
UserDetails userDetails;
HashMap<?,?> responseMap;
Object responceObject;
List responceList;
String view;
String by;
String userRole;
int statusCode;
String message;
@Override
public int hashCode() {
	return Objects.hash(by, count, message, responceList, responceObject, responseMap, sessionStatus, statusCode,
			userDetails, userRole, view);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ResultMapper other = (ResultMapper) obj;
	return Objects.equals(by, other.by) && count == other.count && Objects.equals(message, other.message)
			&& Objects.equals(responceList, other.responceList) && Objects.equals(responceObject, other.responceObject)
			&& Objects.equals(responseMap, other.responseMap) && sessionStatus == other.sessionStatus
			&& statusCode == other.statusCode && Objects.equals(userDetails, other.userDetails)
			&& Objects.equals(userRole, other.userRole) && Objects.equals(view, other.view);
}

}
