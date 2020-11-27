package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.user.MyUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SercurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyUserDetailsService myUserDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtRequestFilter JwtRequestFilter;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//configure AuthenticationManager so that it knows from where to load
		//user for matching credentials
		//user BcryptPasswordEncoder
		auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/*.js").permitAll()
			.antMatchers("/*.css").permitAll()
			.antMatchers("/static/**").permitAll()
			.antMatchers("/**.woff2").permitAll()
			.antMatchers("/**.woff?v=1.8.36").permitAll()
			.antMatchers("/**.ttf?v=1.8.36").permitAll()
			.antMatchers("/assets/**").permitAll()
			.antMatchers("/admin/**").permitAll()
			.antMatchers("/index.html").permitAll()		
			.antMatchers("/rest/user/login").permitAll()
			.antMatchers("/rest/device/submit").permitAll()
			.antMatchers("/rest/device/access").permitAll()			
			.antMatchers("/rest/device/log/download").permitAll()
			.antMatchers("/rest/user/download").permitAll()
			.antMatchers("/rest/user/import").permitAll()
			.antMatchers("/rest/modify/users").permitAll()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(JwtRequestFilter, UsernamePasswordAuthenticationFilter.class);				
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

