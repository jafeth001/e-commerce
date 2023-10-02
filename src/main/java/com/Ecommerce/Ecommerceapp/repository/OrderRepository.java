package com.Ecommerce.Ecommerceapp.repository;

import com.Ecommerce.Ecommerceapp.entity.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<WebOrder,Long> {
}
