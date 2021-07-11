package br.com.zupacademy.guilherme.mercadolivre.controller.filter;

import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private UserRepository userRepository;

    public TokenAuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenRecovery(request);
        if(isTokenValid(token)) {
            isUserValid(token);
        }

        filterChain.doFilter(request, response);
    }

    public String tokenRecovery(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7, authorizationHeader.length());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey("AF5AF71A18F661C3CB10D17438422F31C45B7AC6A032A0636FD7CC6CF98B971F").parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void isUserValid(String token) {
        Claims claims = Jwts.parser().setSigningKey("AF5AF71A18F661C3CB10D17438422F31C45B7AC6A032A0636FD7CC6CF98B971F").parseClaimsJws(token).getBody();
        Long subjectId = Long.parseLong(claims.getSubject());
        User user = userRepository.findById(subjectId).get();
        UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
