package sudo.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import jakarta.servlet.ServletException;
import sudo.user.services.UsersService;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutHandler {
	
	 private UsersService userService;

	    public LogoutSuccessHandler(UsersService userService) {
	        this.userService = userService;
	    }

   public void onLogoutSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
        super.onLogoutSuccess(request, response, authentication);
   }

//@Override
//public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//	userService.attendanceEntry(1,request.getRemoteHost(),request.getSession().getId());
//}

@Override
public void logout(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
		Authentication authentication) {
	userService.attendanceEntry(1,request.getRemoteHost(),request.getSession().getId());
	
}
}