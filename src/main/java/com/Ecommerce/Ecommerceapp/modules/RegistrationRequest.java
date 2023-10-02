package com.Ecommerce.Ecommerceapp.modules;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    @Valid
    @NotNull(message = "firstname is mandatory")
    @NotBlank(message = "firstname is mandatory")
    @Size(min = 4, max = 25, message = "firstname must be between 4 and 25 characters")
    private String firstname;
    @NotNull(message = "lastname is mandatory")
    @NotBlank(message = "lastname is mandatory")
    @Size(min = 4, max = 25, message = "lastname must be between 4 and 25 characters")
    private String lastname;
    @NotNull(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotNull(message = "password is mandatory")
    @NotBlank(message = "password is mandatory")
    @Size(min = 4, max = 5, message = "password must be between 4 and 25 characters")
    private String password;
}
