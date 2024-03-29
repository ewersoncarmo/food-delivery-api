package com.smartcook.fooddeliveryapi.configuration.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!test")
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors()
			.and()
			// Configuration to use with opaque tokens
			//.oauth2ResourceServer().opaqueToken();
			.oauth2ResourceServer()
			// Configuration to use with transparent tokens
				.jwt() 
				// Configuration to define a decoder when token has been signed using symmetric key
				//.decoder(jwtDecoder())
				.jwtAuthenticationConverter(jwtAuthenticationConverter());
	}
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			// Loads the client's authorities
			var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
			Collection<GrantedAuthority> scopesAuthorities = jwtGrantedAuthoritiesConverter.convert(jwt);

			// Loads the user's authorities
			var authorities = jwt.getClaimAsStringList("authorities");
			
			if (authorities == null) {
				authorities = Collections.emptyList();
			}
			
			List<SimpleGrantedAuthority> userAuthorities = authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			// Creates the authorities collection with client's authorities plus user's authorities
			scopesAuthorities.addAll(userAuthorities);
			
			return scopesAuthorities;
		});
		
		return jwtAuthenticationConverter;
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
//	@Bean
//	public JwtDecoder jwtDecoder() {
//        // It must be used the same MAC (message authentication code) that has been used to sign the token
//		var secretKey = new SecretKeySpec("asdkfljsaçflksajdflçksafjsakdfljaskflsajfdlçkdfjaskçfljsaflkj".getBytes(), "HmacSHA256");
//		
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}
}
