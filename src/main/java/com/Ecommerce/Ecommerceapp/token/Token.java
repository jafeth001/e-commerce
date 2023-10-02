package com.Ecommerce.Ecommerceapp.token;

import com.Ecommerce.Ecommerceapp.entity.Seller;
import com.Ecommerce.Ecommerceapp.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenTypen;
    private boolean expired;
    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
