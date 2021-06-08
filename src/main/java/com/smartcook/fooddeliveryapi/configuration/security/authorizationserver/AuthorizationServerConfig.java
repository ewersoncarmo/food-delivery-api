package com.smartcook.fooddeliveryapi.configuration.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
@Profile("!test")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			// Does not require authentication for checking token (/oauth/check_token) and token key (/oauth/token_key) url
			// If you would like it is possible to change to 'isAuthenticated()' expression
			.checkTokenAccess("permitAll()")
			.tokenKeyAccess("permitAll()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		var enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));
		
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.accessTokenConverter(jwtAccessTokenConverter())
			.tokenEnhancer(enhancerChain)
			// Indicates if a new refresh token should be generated every time a new access token is generated
			.reuseRefreshTokens(false); 
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

		// Configuration to sign token with symmetric key
		// It must be defined a MAC (message authentication code)
		jwtAccessTokenConverter.setSigningKey(jwtKeyStoreProperties.getSigningKey());

		return jwtAccessTokenConverter;
	}
}
