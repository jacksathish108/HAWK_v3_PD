package sudo.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import sudo.security.CustomSuccessHandler;
import sudo.security.LogoutSuccessHandler;
import sudo.security.UserDetailsServiceImpl;
import sudo.user.services.UsersService;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UsersService userService;
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	//http.csrf().disable().authorizeHttpRequests().requestMatchers().permitAll().anyRequest().permitAll();

    	http.csrf().disable().authorizeRequests()
         .requestMatchers("**/", "**/login", "**/logout", "**/error").permitAll()
         .requestMatchers("**/resources**").permitAll()
         .requestMatchers("**/*.js").permitAll()
         .requestMatchers("/Hawk_api_01/**","/pages/v1/**","**/admin/**","/authorization/**","/getClientDashbord/**", "/home", "/login/oauth2/**", "/logout",
        		 "**/loginFailure", "/").authenticated().and().formLogin()
         .loginPage("/login").successHandler(new CustomSuccessHandler(userService)).permitAll()
			.and().oauth2Login().loginPage("/login")
			.successHandler(new CustomSuccessHandler(userService)).permitAll().
			and().logout().addLogoutHandler(new LogoutSuccessHandler(userService))
			.permitAll();
//    	http.sessionManagement((session) -> session
//    	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) );
//    	 http
//         .csrf().disable();
    	
        return http.build();
    }

   
}
