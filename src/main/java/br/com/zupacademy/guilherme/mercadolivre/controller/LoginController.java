package br.com.zupacademy.guilherme.mercadolivre.controller;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.LoginResponseDto;
import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    private Date generateExpirationDate () {
        Date date = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        return date;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken loginData = loginRequestDto.toUserToken();
        System.out.println("AF5AF71A18F661C3CB10D17438422F31C45B7AC6A032A0636FD7CC6CF98B971F");
        try{
            Authentication authentication = authManager.authenticate(loginData);
            User user = (User) authentication.getPrincipal();
            String token = Jwts.builder()
                    .setIssuer("API Mercadolivre")
                    .setSubject(user.getId().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS256, "AF5AF71A18F661C3CB10D17438422F31C45B7AC6A032A0636FD7CC6CF98B971F")
                    .compact();

            return ResponseEntity.ok().body(new LoginResponseDto("Bearer", token));
        }catch(AuthenticationException e) {
            return ResponseEntity.badRequest().body(new FormErrorDto("username/password", "invalid"));
        }
    }
}
