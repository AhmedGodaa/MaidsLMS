package cc.maids.lms.config.utils;

import cc.maids.lms.config.userInstance.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static cc.maids.lms.common.Constants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${ClientService.app.jwtSecret}")
    private String jwtSecret;


    public String getJwtFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        } else {
            return null;
        }
    }

    public String generateJwtToken(UserDetailsImpl userPrincipal) {
//        this username is the email
        return generateTokenFromEmail(userPrincipal.getUsername());
    }

    public String getEmailFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new SignatureException(e.getMessage());

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException(e.getMessage());

        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());

        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new UnsupportedJwtException(e.getMessage());

        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());

        }
        return false;
    }


    public String generateTokenFromEmail(String email) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.builder()
                .claims()
                .add("email", email)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 100 * 60 + 24))
                .and()
                .signWith(key, Jwts.SIG.HS256)
                .compact();


    }
}