package com.Ecommerce.Ecommerceapp.repository;

import com.Ecommerce.Ecommerceapp.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    public Seller findByEmail(String email);
}
