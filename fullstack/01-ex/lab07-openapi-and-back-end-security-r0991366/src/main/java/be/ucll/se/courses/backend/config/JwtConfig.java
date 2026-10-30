package be.ucll.se.courses.backend.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    private static final Logger log = LoggerFactory.getLogger(JwtConfig.class);

    @Bean
    public SecretKey secretKey(JwtProperties jwtProperties) throws NoSuchAlgorithmException {
        final var secretKeyProperty = jwtProperties.secretKey();

        if (secretKeyProperty == null || secretKeyProperty.isBlank()) {
            log.warn("No secret key configured, generating a random key");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            return keyGenerator.generateKey();
        } else {
            byte[] bytes = Base64.getDecoder().decode(secretKeyProperty);
            // Even though we're doing HMAC-SHA256, not AES, we still need to provide an algorithm
            //uhh imma just use HMAC-SHA256 everywhere
            return new SecretKeySpec(bytes, "HmacSHA256");
        }
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {
        JWK jwk = new OctetSequenceKey.Builder(secretKey)
                .algorithm(JWSAlgorithm.HS256)
                .build();

        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {
        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}