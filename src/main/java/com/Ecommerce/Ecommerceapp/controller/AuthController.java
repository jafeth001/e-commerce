package com.Ecommerce.Ecommerceapp.controller;

import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.modules.*;
import com.Ecommerce.Ecommerceapp.service.AuthSellerService;
import com.Ecommerce.Ecommerceapp.service.AuthUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthUserService authUserService;
    private final AuthSellerService authSellerService;

    /**
     * User regstration and authentication
     */
    @PostMapping("/user/register")
    public ResponseEntity<AuthenticationResponse> userRegister
    (@Valid @RequestBody RegistrationRequest request) throws GlobalException {
        log.info("user registration :{}", request);
        return ResponseEntity.ok(authUserService.userRegister(request));
    }

    @PostMapping("/user/authenticate")
    public ResponseEntity<AuthenticationResponse> userAuthenticate
            (@RequestBody AuthenticationRequest request) throws GlobalException {
        log.info("user authentication :{}", request);
        return ResponseEntity.ok(authUserService.userAuthenticate(request));
    }

    /**
     * Seller regstration and authentication
     */
    @PostMapping("/seller/register")
    public ResponseEntity<AuthenticationResponse> sellerRegister
    (@Valid @RequestBody RegistrationRequest request) throws GlobalException {
        log.info("seller registration :{}", request);
        return ResponseEntity.ok(authSellerService.sellerRegister(request));
    }

    @PostMapping("/seller/authenticate")
    public ResponseEntity<AuthenticationResponse> sellerAuthenticate
            (@RequestBody AuthenticationRequest request) throws GlobalException {
        log.info("seller authentication :{}", request);
        return ResponseEntity.ok(authSellerService.sellerAuthenticate(request));
    }

    /**
     * handle of bad request from validation dependency
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlesvalidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((objectError) -> {
            String fieldname = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            errors.put(fieldname, message);
        });
        return errors;
    }
}

