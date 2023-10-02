package com.Ecommerce.Ecommerceapp.controller;

import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    private User getprofile(@AuthenticationPrincipal User user) {
        log.info("getting profile :{}", user);
        return user;
    }

    @DeleteMapping("/delete")
    private String deleteUser(@AuthenticationPrincipal User user) {
        log.info("deleting user :{}", user);
        userService.deleteUser(user);
        return "deleted successfully";
    }

    @PutMapping("/update/{id}")
    private String updateUser(@AuthenticationPrincipal @PathVariable("id") Long id, @RequestBody User user) {
        log.info("updating profile :{}", user);
        userService.updateUser(id, user);
        return "updated Successfully";
    }
}
