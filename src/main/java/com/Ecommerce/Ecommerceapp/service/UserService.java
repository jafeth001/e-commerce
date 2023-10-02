package com.Ecommerce.Ecommerceapp.service;

import com.Ecommerce.Ecommerceapp.entity.User;
import com.Ecommerce.Ecommerceapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    public void updateUser(Long id, User user) {
        var newUser = userRepository.findById(id).get();
        if (Objects.nonNull(user.getFirstname()) && !"".equalsIgnoreCase(user.getFirstname())) {
            newUser.setFirstname(user.getFirstname());
        }
        if (Objects.nonNull(user.getLastname()) && !"".equalsIgnoreCase(user.getLastname())) {
            newUser.setLastname(user.getLastname());
        }
        if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
            newUser.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())) {
            newUser.setFirstname(user.getPassword());
        }
        userRepository.save(newUser);
    }
}