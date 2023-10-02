package com.Ecommerce.Ecommerceapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String brand;
    private String description;
    private String imageUrl;
    private double price;
    private int stock;
    @JsonIgnore
   @OneToOne
    private Cart cart;
//    @JsonIgnore
    @ManyToOne
    private Seller seller;
}
