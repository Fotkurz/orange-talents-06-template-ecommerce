package br.com.zupacademy.guilherme.mercadolivre.controller;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.request.LoginRequestDto;
import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.FormErrorDto;
import br.com.zupacademy.guilherme.mercadolivre.controller.dto.response.LoginResponseDto;
import br.com.zupacademy.guilherme.mercadolivre.repository.UserRepository;
import br.com.zupacademy.guilherme.mercadolivre.security.TokenManager;
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

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenManager tokenManager;

    @Value("${mercadolivre.jwt.secret}")
    private String secret;

    private Date generateExpirationDate () {
        Date date = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        return date;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken loginData = loginRequestDto.toUserToken();
        try{
            Authentication authentication = authManager.authenticate(loginData);
            String token = tokenManager.generateNewToken(authentication);
            return ResponseEntity.ok().body(new LoginResponseDto("Bearer", token));
        }catch(AuthenticationException e) {
            return ResponseEntity.badRequest().body(new FormErrorDto("username/password", "invalid"));
        }
    }
}
