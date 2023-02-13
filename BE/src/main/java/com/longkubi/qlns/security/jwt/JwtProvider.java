package com.longkubi.qlns.security.jwt;

import com.longkubi.qlns.security.userpincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static final String JWTSECRET = "longkubi2k@gmail.com";
    private static final int JWTEXPIRATION = 5*60*60;


    public String createToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + JWTEXPIRATION * 1000))
                .signWith(SignatureAlgorithm.HS512, JWTSECRET).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWTSECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("valid JWT signature ->Message: {0}", e);
        } catch (MalformedJwtException e) {
            logger.error("The token invalid format ->Message: {0}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token ->Message: {0}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token -> Message: {0}", e);
        } catch (IllegalArgumentException e) {
            logger.error("Jwt claims string is empty -> Message {0}", e);
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(JWTSECRET).parseClaimsJws(token).getBody().getSubject();
/*        String userName = Jwts.parser().setSigningKey(JWTSECRET).parseClaimsJws(token).getBody().getSubject();
        return userName;*/
    }
}
