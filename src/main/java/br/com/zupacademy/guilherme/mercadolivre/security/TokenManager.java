package br.com.zupacademy.guilherme.mercadolivre.security;

import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class TokenManager {

    @Value("${mercadolivre.jwt.secret}")
    private String secret;

    private Date generateExpirationDate() {
        Date date = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        return date;
    }

    public String generateNewToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String token = Jwts.builder()
                .setIssuer("API mercadolivre")
                .setAudience(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
        return token;
    }

    public String getTokenAudience(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getAudience();
    }

    public String tokenRecovery(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7, authorizationHeader.length());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
