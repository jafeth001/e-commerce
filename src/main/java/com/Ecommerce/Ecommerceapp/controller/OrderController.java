package com.Ecommerce.Ecommerceapp.controller;

import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.entity.WebOrder;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.service.CartService;
import com.Ecommerce.Ecommerceapp.service.OrderService;
import com.Ecommerce.Ecommerceapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/request/{id}")
    public String webOrder(@AuthenticationPrincipal User user, @PathVariable("id") Long id,
                           @RequestBody WebOrder webOrder) throws GlobalException {
        user.getEmail();
        cartService.getcartById(id);
        orderService.webOrder(id, webOrder);
        return "You have successfully requested Order";
    }

    @GetMapping("/get/all")
    public List<WebOrder> getallOrder(@AuthenticationPrincipal User user) throws GlobalException {
        user.getEmail();
        return orderService.getallOrder();
    }

    @GetMapping("/get/{id}")
    public WebOrder getByidwebOrder
            (@AuthenticationPrincipal User user, @PathVariable("id") Long id) throws GlobalException {
        user.getEmail();
        return orderService.getByidwebOrder(id);

    }

    @PutMapping("/cancel/{id}")
    public String cancelrequest(@AuthenticationPrincipal User user, @RequestBody WebOrder webOrder, @PathVariable("id") Long id) throws GlobalException {
        user.getEmail();
        orderService.cancelrequest(id, webOrder);
        return "order request cancelled successfully";
    }

}
