package be.ucll.se.courses.backend.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties({JwtProperties.class})
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults()) //for front-end (CORS)
                .authorizeHttpRequests (
                        authorizeRequests ->
                                authorizeRequests
                                        // Allow all access to health check
                                        .requestMatchers("/status"). permitAll()
                                        // Allow all access to error endpoints
                                        .requestMatchers("/error","/error/**"). permitAll()
                                        // Allow all to login and signup
                                        .requestMatchers ("/users/login", "/users/signup", "/users/logout").permitAll()
                                        // Allow OpenAPI access
                                        .requestMatchers("/v3/api-docs/**").permitAll()
                                        // Allow Swagger UI
                                        .requestMatchers ("/swagger-ui/**", "/swagger-ui.html").permitAll()
                                        //.requestMatchers ("/test-utils/**").permitAll()
                                        .anyRequest () .authenticated ())
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(resourceServer ->
                                resourceServer.jwt(Customizer.withDefaults())
                                        .bearerTokenResolver(new CookieBearerTokenResolver()))
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
