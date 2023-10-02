package com.Ecommerce.Ecommerceapp.controller;

import com.Ecommerce.Ecommerceapp.entity.Seller;
import com.Ecommerce.Ecommerceapp.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("seller")
@Slf4j
public class SellerController {
    private final SellerService sellerService;

    @GetMapping("/profile")
    private Seller getprofile(@AuthenticationPrincipal Seller seller) {
        log.info("getting profile :{}", seller);
        return seller;
    }

    @DeleteMapping("/delete")
    private String deletingSeller(@AuthenticationPrincipal Seller seller) {
        log.info("deleting Seller :{}", seller);
        sellerService.deletingSeller(seller);
        return "deleted successfully";
    }

    @PutMapping("/update/{id}")
    private String updateSeller(@AuthenticationPrincipal @PathVariable("id") Long id, @RequestBody Seller seller) {
        log.info("updating profile :{}", seller);
        sellerService.updateSeller(id, seller);
        return "updated successfully";
    }
}
