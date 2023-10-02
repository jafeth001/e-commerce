package com.Ecommerce.Ecommerceapp.service;

import com.Ecommerce.Ecommerceapp.config.JwtService;
import com.Ecommerce.Ecommerceapp.entity.Role;
import com.Ecommerce.Ecommerceapp.entity.Seller;
import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.modules.AuthenticationRequest;
import com.Ecommerce.Ecommerceapp.modules.AuthenticationResponse;
import com.Ecommerce.Ecommerceapp.modules.RegistrationRequest;
import com.Ecommerce.Ecommerceapp.repository.UserRepository;
import com.Ecommerce.Ecommerceapp.token.Token;
import com.Ecommerce.Ecommerceapp.token.TokenRepository;
import com.Ecommerce.Ecommerceapp.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse userRegister(RegistrationRequest request) throws GlobalException {
        String email = request.getEmail();
        User regUser = userRepository.findByEmail(request.getEmail());
        if (regUser == null) {
            var user = User
                    .builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            var token = jwtService.generatesingletoken(user);
            extractedUserToken(user, token);
            return AuthenticationResponse.builder()
                    .token(token)
                    .message("Account Created Successfully, " + request.getEmail())
                    .role(Role.USER)
                    .build();
        }
        throw new GlobalException("user with email " + email + " already exists");
    }

    public AuthenticationResponse userAuthenticate(AuthenticationRequest request) throws GlobalException {
        User email = userRepository.findByEmail(request.getEmail());
        if (email != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail());
            var token = jwtService.generatesingletoken(user);
            revokeAllUserToken(user);
            extractedUserToken(user, token);
            return AuthenticationResponse.builder()
                    .token(token)
                    .message("Account Login Successfully, " + request.getEmail())
                    .role(Role.USER)
                    .build();
        }
        throw new GlobalException("invalid email");
    }

    private void extractedUserToken(User user, String token) {
        var savedToken = Token.builder()
                .user(user)
                .tokenTypen(TokenType.BEARER)
                .token(token)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(savedToken);
    }

    private void revokeAllUserToken(User user) {
        var validUserToken = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserToken.isEmpty()) {
            return;
        }
        validUserToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserToken);
    }
}
