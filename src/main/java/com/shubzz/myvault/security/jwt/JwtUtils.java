package com.shubzz.myvault.security.jwt;


import com.shubzz.myvault.repository.BannedTokenRepository;
import com.shubzz.myvault.security.jwt.exception.TokenBannedException;
import com.shubzz.myvault.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Autowired
    BannedTokenRepository bannedTokenRepository;
    @Value("${myvault.app.jwtSecret}")
    private String jwtSecret;

    @Value("${myvault.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
//                .setId(userPrincipal.getId())
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setIssuer("MyVault")
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .claim("authorities", JSONObject.valueToString(userPrincipal.getAuthorities()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            if (Boolean.TRUE.equals(bannedTokenRepository.existsByToken(authToken)))
                throw new TokenBannedException();
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (TokenBannedException e) {
            logger.error("JWT token is banned: {}", e.getMessage());
        }
        return false;
    }
}
