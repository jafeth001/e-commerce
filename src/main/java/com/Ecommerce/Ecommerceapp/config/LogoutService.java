package com.Ecommerce.Ecommerceapp.config;

import com.Ecommerce.Ecommerceapp.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String jwt;
        final String authheader = request.getHeader("Authorization");
        if (authheader == null || !authheader.startsWith("Bearer ")) {
            return;
        }
        jwt = authheader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setRevoked(true);
            storedToken.setExpired(true);
            tokenRepository.save(storedToken);
        }
    }
}
