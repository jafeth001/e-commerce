package com.Ecommerce.Ecommerceapp.service;

import com.Ecommerce.Ecommerceapp.config.JwtService;
import com.Ecommerce.Ecommerceapp.entity.Role;
import com.Ecommerce.Ecommerceapp.entity.Seller;
import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.modules.AuthenticationRequest;
import com.Ecommerce.Ecommerceapp.modules.AuthenticationResponse;
import com.Ecommerce.Ecommerceapp.modules.RegistrationRequest;
import com.Ecommerce.Ecommerceapp.repository.SellerRepository;
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
public class AuthSellerService {

    private final SellerRepository sellerRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse sellerRegister(RegistrationRequest request) throws GlobalException {
        String email = request.getEmail();
        Seller regSeller = sellerRepository.findByEmail(request.getEmail());
        if (regSeller == null) {
            var seller = Seller
                    .builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.SELLER)
                    .build();
            sellerRepository.save(seller);
            var token = jwtService.generatesingletoken(seller);
            extractedSellerToken(seller, token);
            return AuthenticationResponse
                    .builder()
                    .token(token)
                    .message("Account Created Successfully, " + request.getEmail())
                    .role(Role.SELLER)
                    .build();
        }
        throw new GlobalException("seller with email " + email + " already exists");
    }

    public AuthenticationResponse sellerAuthenticate(AuthenticationRequest request) throws GlobalException {
        Seller email = sellerRepository.findByEmail(request.getEmail());
        if (email != null ) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var seller = sellerRepository.findByEmail(request.getEmail());
            var token = jwtService.generatesingletoken(seller);
            revokeAllSellerToken(seller);
            extractedSellerToken(seller, token);
            return AuthenticationResponse
                    .builder()
                    .token(token)
                    .message("Account Login Successfully, " + request.getEmail())
                    .role(Role.SELLER)
                    .build();
        }
        throw new GlobalException("invalid email");
    }

    private void extractedSellerToken(Seller seller, String token) {
        var savedToken = Token.builder().seller(seller).tokenTypen(TokenType.BEARER).token(token).revoked(false).expired(false).build();
        tokenRepository.save(savedToken);
    }

    private void revokeAllSellerToken(Seller seller) {
        var validSellerToken = tokenRepository.findAllValidTokensBySelller(seller.getId());
        if (validSellerToken.isEmpty()) {
            return;
        }
        validSellerToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validSellerToken);
    }
}
