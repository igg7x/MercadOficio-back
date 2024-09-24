package com.example.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${spring.security.oauth2.resourceserver.jwt.audience}")
    private String audience;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/users/public/**").permitAll()
                        .requestMatchers("/api/v1/users-customers/public/**").permitAll()
                        .requestMatchers("/api/v1/users-offerings/public/**").permitAll()
                        .requestMatchers("/api/v1/users/private/**").authenticated()
                        .requestMatchers("/api/v1/users-customers/private/**").authenticated()
                        .requestMatchers("/api/v1/users-offerings/private/**").authenticated()
                        .requestMatchers("/api/v1/applications/**").authenticated()
                        .requestMatchers("/api/v1/jobs/**").authenticated()
                        .requestMatchers("/api/v1/categories/**").authenticated()
                        .requestMatchers("/api/v1/reviews/**").authenticated()

                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())))
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }

    // @Bean
    // JwtAuthenticationConverter jwtAuthenticationConverter() {
    // JwtGrantedAuthoritiesConverter converter = new
    // JwtGrantedAuthoritiesConverter();
    // converter.setAuthoritiesClaimName("https://example_yt/roles");
    // converter.setAuthorityPrefix("");

    // JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    // jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
    // return jwtConverter;
    // }
}
