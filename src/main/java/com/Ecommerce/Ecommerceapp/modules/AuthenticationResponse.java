package com.Ecommerce.Ecommerceapp.modules;

import com.Ecommerce.Ecommerceapp.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String message;
    @Enumerated(EnumType.STRING)
    private Role role;
}
