package sudo.bizservices;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import sudo.dtos.ResultMapper;

@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value=WebApplicationContext.SCOPE_SESSION)
public class SessionData {
	private ResultMapper userSession;

	public ResultMapper getUserSession() {
		return userSession;
	}

	public void setUserSession(ResultMapper userSession) {
		this.userSession = userSession;
	}

   
}