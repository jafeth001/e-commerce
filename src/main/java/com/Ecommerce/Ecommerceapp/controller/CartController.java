package com.Ecommerce.Ecommerceapp.controller;

import com.Ecommerce.Ecommerceapp.entity.Cart;
import com.Ecommerce.Ecommerceapp.entity.Product;
import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.service.CartService;
import com.Ecommerce.Ecommerceapp.service.ProductService;
import com.Ecommerce.Ecommerceapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/add/{id}")
    public String addToCart(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @RequestBody Cart cart) throws GlobalException {
        user.getEmail();
        Product product = productService.getproductById(id);
        cartService.addToCart(user, id, cart);
        return "product added to cart";
    }

    @GetMapping("/get/{id}")
    public Cart getCartById(@AuthenticationPrincipal User user, @PathVariable("id") Long id) throws GlobalException {
        user.getEmail();
        return cartService.getcartById(id);
    }

    @GetMapping("/get/all")
    public List<Cart> getallcart(@AuthenticationPrincipal User user) throws GlobalException {
        user.getEmail();
        return cartService.getAllCart();
    }

//    @DeleteMapping("/delete/{id}")
//    public String deleteById(@AuthenticationPrincipal User User,
//                                 @PathVariable("id") Long id) throws GlobalException {
//        cartService.deleteItem(id);
//        return "product removed from cart";
//    }
}
