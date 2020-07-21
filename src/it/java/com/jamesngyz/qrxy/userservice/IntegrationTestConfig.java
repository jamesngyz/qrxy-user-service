package com.jamesngyz.qrxy.userservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.Data;

@Profile("integration-test")
@Configuration
@Data
public class IntegrationTestConfig {
	
	private final String clientId;
	private final String clientSecret;
	private final String issuer;
	private final String audience;
	
	public IntegrationTestConfig(
			@Value("${app.test.security.clientId}") String clientId,
			@Value("${app.test.security.clientSecret}") String clientSecret,
			@Value("${auth0.issuer}") String issuer,
			@Value("${auth0.apiAudience}") String audience) {
		
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.issuer = issuer;
		this.audience = audience;
	}
	
}
