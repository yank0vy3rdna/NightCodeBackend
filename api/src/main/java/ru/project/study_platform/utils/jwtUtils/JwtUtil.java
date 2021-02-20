package ru.project.study_platform.utils.jwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_WORD = "NIGHT_IN_ITMO";
    private final int tokenLifeTime  = 1000 * 60 * 60 + 10;

    public String extractUserEmailFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationFromToken(String token){
        return extractClaims(token, Claims::getExpiration);
    }


    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_WORD).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpirationFromToken(token).before(new Date());
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
