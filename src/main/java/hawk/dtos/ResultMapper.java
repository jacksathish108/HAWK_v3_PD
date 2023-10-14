/*
 * 
 */
package hawk.dtos;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResultMapper {


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

}
