package com.Ecommerce.Ecommerceapp.service;

import com.Ecommerce.Ecommerceapp.entity.Cart;
import com.Ecommerce.Ecommerceapp.entity.Product;
import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.repository.CartRepository;
import com.Ecommerce.Ecommerceapp.repository.ProductRepository;
import com.Ecommerce.Ecommerceapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void addToCart(User user, Long id, Cart cart) {
        Product product = productRepository.findById(id).get();
        User saveduser = userRepository.findByEmail(user.getEmail());
        Cart saveCart = Cart.builder()
                .user(saveduser)
                .totalPrice(cart.getTotalPrice())
                .quantity(cart.getQuantity())
                .product(product)
                .build();
        cartRepository.save(saveCart);
    }

    public Cart getcartById(Long id) throws GlobalException {
        Cart cart = cartRepository.findById(id).get();
        if (cart == null) {
            throw new GlobalException("cart with Id " + id + " not found");
        }
        return cart;
    }
    public List<Cart> getAllCart() throws GlobalException {
        List<Cart> carts = cartRepository.findAll();
        if (carts == null) {
            throw new GlobalException("carts not available");
        }
        return carts;
    }
//    public void deleteItem(Long id) throws GlobalException {
//        var cart = cartRepository.findById(id);
//        if (cart==null) {
//            throw new GlobalException("cart with Id " + id + " not found to be deleted");
//        }
//        cartRepository.deleteById(id);
//    }
}
