package com.Ecommerce.Ecommerceapp.controller;

import com.Ecommerce.Ecommerceapp.entity.Product;
import com.Ecommerce.Ecommerceapp.entity.Seller;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.service.ProductService;
import com.Ecommerce.Ecommerceapp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SellerService sellerService;

    @PostMapping("/add")
    public String addProduct(@AuthenticationPrincipal Seller seller, @RequestBody Product product) throws GlobalException {
       seller.getEmail();
        productService.addProduct(seller, product);
        return "product successfully added";
    }

    @GetMapping("/get/all")
    public List<Product> getAllProduct() throws GlobalException {
        return productService.getAllProduct();
    }

    @GetMapping("/get/{id}")
    public Product getproductById(@PathVariable("id") Long id) throws GlobalException {
        return productService.getproductById(id);
    }

    @GetMapping("/get/search/{name}")
    public List<Product> getproductByName(@PathVariable("name") String name) throws GlobalException {
        return productService.getproductByName(name);
    }

    @DeleteMapping("/delete/{id}")
    public String delproductById(@AuthenticationPrincipal Seller seller,
                                 @PathVariable("id") Long id) throws GlobalException {
        seller.getEmail();
        productService.delproductById(id);
        return "product successfully deleted";
    }

    @PutMapping("/update/{id}")
    public String updateproductById(@AuthenticationPrincipal Seller seller,
                                    @PathVariable("id") Long id, @RequestBody Product product) throws GlobalException {
        seller.getEmail();
        productService.updateproductById(id, product);
        return "product successfully updated";
    }
}