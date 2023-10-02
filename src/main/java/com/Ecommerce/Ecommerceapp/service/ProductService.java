package com.Ecommerce.Ecommerceapp.service;

import com.Ecommerce.Ecommerceapp.entity.Product;
import com.Ecommerce.Ecommerceapp.entity.Seller;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.repository.ProductRepository;
import com.Ecommerce.Ecommerceapp.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;


    public void addProduct(Seller seller, Product product) {
        Seller newSeller = sellerRepository.findByEmail(seller.getEmail());
        Product savedProduct = Product.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .stock(product.getStock())
                .seller(newSeller)
                .build();
        productRepository.save(savedProduct);

    }

    public List<Product> getAllProduct() throws GlobalException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new GlobalException("Couldn't find any product in DB");
        }
        return products;
    }

    public Product getproductById(Long id) throws GlobalException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new GlobalException("Couldn't find any product in DB with id " + id);
    }

    public List<Product> getproductByName(String name) throws GlobalException {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new GlobalException("Couldn't find any product in DB with name " + name);
        }
        return products;
    }

    public void delproductById(Long id) throws GlobalException {
        var findById = productRepository.findById(id).get();
        if (findById != null) {
            productRepository.deleteById(id);
        }
        throw new GlobalException("Couldn't find any product with ID " + id);

    }

    public void updateproductById(Long id, Product product) throws GlobalException {
        Product savedProduct = productRepository.findById(id).get();
        if (savedProduct == null) {
            throw new GlobalException("Couldn't find any product with ID " + id);
        }
        if (Objects.nonNull(product.getBrand()) && !"".equalsIgnoreCase(product.getBrand())) {
            savedProduct.setBrand(product.getBrand());
        }
        if (Objects.nonNull(product.getName()) && !"".equalsIgnoreCase(product.getName())) {
            savedProduct.setName(product.getName());
        }
        if (Objects.nonNull(product.getDescription()) && !"".equalsIgnoreCase(product.getDescription())) {
            savedProduct.setDescription(product.getDescription());
        }
        if (Objects.nonNull(product.getImageUrl()) && !"".equalsIgnoreCase(product.getImageUrl())) {
            savedProduct.setImageUrl(product.getImageUrl());
        }
        if (Objects.nonNull(product.getStock()) && !"".equalsIgnoreCase(String.valueOf(product.getStock()))) {
            savedProduct.setStock(product.getStock());
        }
        if (Objects.nonNull(product.getPrice()) && !"".equalsIgnoreCase(String.valueOf(product.getPrice()))) {
            savedProduct.setPrice(product.getPrice());
        }
        productRepository.save(savedProduct);
    }

}
