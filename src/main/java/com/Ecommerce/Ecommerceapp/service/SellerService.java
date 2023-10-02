package com.Ecommerce.Ecommerceapp.service;

import com.Ecommerce.Ecommerceapp.entity.Seller;
import com.Ecommerce.Ecommerceapp.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    public void deletingSeller(Seller seller) {
        sellerRepository.delete(seller);
    }

    public void updateSeller(Long id, Seller seller) {
        var newSeller = sellerRepository.findById(id).get();
        if (Objects.nonNull(seller.getFirstname()) && !"".equalsIgnoreCase(seller.getFirstname())) {
            newSeller.setFirstname(seller.getFirstname());
        }
        if (Objects.nonNull(seller.getLastname()) && !"".equalsIgnoreCase(seller.getLastname())) {
            newSeller.setLastname(seller.getLastname());
        }
        if (Objects.nonNull(seller.getEmail()) && !"".equalsIgnoreCase(seller.getEmail())) {
            newSeller.setEmail(seller.getEmail());
        }
        if (Objects.nonNull(seller.getPassword()) && !"".equalsIgnoreCase(seller.getPassword())) {
            newSeller.setFirstname(seller.getPassword());
        }
        sellerRepository.save(newSeller);
    }
}

