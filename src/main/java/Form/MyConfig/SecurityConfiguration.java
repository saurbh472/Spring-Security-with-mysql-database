package Form.MyConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
	
	private Form.service.ServiceImpl.CustomUserDetails customUserDetails;
	
	@Bean
	public SecurityFilterChain filtertChain(HttpSecurity http) throws Exception{
		
		http
		.csrf().disable()
		.cors().disable()
		.authorizeRequests()
		.antMatchers("/signin").permitAll()
		.antMatchers("/normal/**").hasRole("NORMAL")
		.antMatchers("/admin/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/signin")
		.loginProcessingUrl("/dologin")
		.defaultSuccessUrl("/normal/users");

		return http.build();
		
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception
	{
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetails).passwordEncoder(passwordEncoder());
		return null;
		
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
		
	}
	

}
