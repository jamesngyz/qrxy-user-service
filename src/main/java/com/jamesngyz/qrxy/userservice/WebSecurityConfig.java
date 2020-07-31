package com.jamesngyz.qrxy.userservice;

import static org.springframework.http.HttpMethod.POST;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Environment environment;
	private final String apiAudience;
	private final String issuer;
	
	public WebSecurityConfig(
			Environment environment,
			@Value(value = "${auth0.apiAudience}") String apiAudience,
			@Value(value = "${auth0.issuer}") String issuer) {
		
		this.environment = environment;
		this.apiAudience = apiAudience;
		this.issuer = issuer;
	}
	
	@Override
	public void configure(WebSecurity web) {
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			web.ignoring().antMatchers("/**");
		}
	}
	
	//@formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JwtWebSecurityConfigurer
				.forRS256(apiAudience, issuer)
				.configure(http)
					.httpBasic().disable()
					.cors().disable()
					.csrf().disable()
					.authorizeRequests()
						.antMatchers(POST, "/v1/users").hasRole("ADMIN");
	}
	//@formatter:on
	
}
