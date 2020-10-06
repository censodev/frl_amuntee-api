package com.printway.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.logging.Logger;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtProvider {
    private final String Uri = "/api/auth/**";
    private final String header = "Authorization";
    private final String prefix = "Bearer ";
    private final int expiration = 24*60*60*1000;
    private final String secret = "jalskdjlakjdlkajsdlkjsalkdjsalkdjlksajdlksajdlksajdlkjsalkdjaslkdjlksajdlksajdl";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public String generateToken(Credentials credentials) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(credentials.getCode())
                .claim("credentials", credentials)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Credentials getCredentials(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        var credentials = new ObjectMapper().convertValue(claims.get("credentials"), Credentials.class);
        logger.info(credentials.toString());
        return credentials;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return false;
    }
}
