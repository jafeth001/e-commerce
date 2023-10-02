package com.Ecommerce.Ecommerceapp.repository;

import com.Ecommerce.Ecommerceapp.entity.Cart;
import com.Ecommerce.Ecommerceapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
}
