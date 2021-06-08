package com.smartcook.fooddeliveryapi.configuration.security.resourceserver;

import com.smartcook.fooddeliveryapi.configuration.security.authorizationserver.JwtKeyStoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!test")
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors()
			.and()
			.oauth2ResourceServer()
				.jwt()
				.decoder(jwtDecoder())
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
	
	@Bean
	public JwtDecoder jwtDecoder() {
        // It must be used the same MAC (message authentication code) that has been used to sign the token
		var secretKey = new SecretKeySpec(jwtKeyStoreProperties.getSigningKey().getBytes(), "HmacSHA256");

		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
}
