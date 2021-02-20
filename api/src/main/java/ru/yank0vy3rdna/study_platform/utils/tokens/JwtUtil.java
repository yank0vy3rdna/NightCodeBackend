package ru.yank0vy3rdna.study_platform.utils.tokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import ru.yank0vy3rdna.study_platform.model.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_WORD = "NIGHT_IN_ITMO";
    private final int tokenLifeTime  = 1000 * 60 * 60 + 10;

    public String extractUserIdFromToken(String token){
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

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user);
    }

    private String createToken(Map<String, Object> claims, User user){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifeTime))
                .signWith(SignatureAlgorithm.HS512, SECRET_WORD).compact();
    }

    public Boolean validateToken(String token, User user){
        final Long userId = Long.parseLong(extractUserIdFromToken(token));
        return (userId.equals(user.getId()) && !isTokenExpired(token));
    }
}
