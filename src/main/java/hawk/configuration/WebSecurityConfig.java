//package hawk.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import hawk.security.CustomSuccessHandler;
//import hawk.security.LogoutSuccessHandler;
//import hawk.security.UserDetailsServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfiguration {
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder());
//
//		return authProvider;
//	}
//
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}
//
//	
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers("/Hawk_api_01/**","/pages/v1/**","/admin/**","/authorization/**")
//		.fullyAuthenticated()
//				.antMatchers("/getClientDashbord/**", "/home", "/login/oauth2/**", "/logout",
//						"/loginFailure", "/")
//				.fullyAuthenticated().and().formLogin().loginPage("/login").successHandler(successHandler()).permitAll()
//				.and().oauth2Login().loginPage("/login")
//				.successHandler(successHandler()).permitAll().
//				and().logout().addLogoutHandler(logoutHandler())
//				.permitAll();
//		
//
////		 http
////         .authorizeRequests()
////         .antMatchers("/login").permitAll()
////         .anyRequest().authenticated()
////         .and()
////         .formLogin().loginPage("/login")
////         .and().csrf().disable()
////         .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
////         .and().oauth2Login().loginPage("/login")
////         .successHandler(successHandler());
//
// http.headers().frameOptions().disable();
//	}
//
//	@Bean
//	public CustomSuccessHandler successHandler() {
//		return new CustomSuccessHandler();
//	}
//
//	@Bean
//	public LogoutSuccessHandler logoutHandler() {
//		return new LogoutSuccessHandler("/logout");
//	}
//}
