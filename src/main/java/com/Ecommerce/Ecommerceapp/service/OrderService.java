package com.Ecommerce.Ecommerceapp.service;
import com.Ecommerce.Ecommerceapp.entity.*;
import com.Ecommerce.Ecommerceapp.exception.GlobalException;
import com.Ecommerce.Ecommerceapp.repository.AddressRepository;
import com.Ecommerce.Ecommerceapp.repository.CartRepository;
import com.Ecommerce.Ecommerceapp.repository.OrderRepository;
import com.Ecommerce.Ecommerceapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public void webOrder(Long id, WebOrder webOrder) {
        Cart savedCart = cartRepository.findById(id).get();
        var address = webOrder.getAddress();
        Address newaddress = Address.builder()
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .town(address.getTown())
                .phone(address.getPhone())
                .build();
        var savedAddress = addressRepository.save(newaddress);
        WebOrder saveOrder = WebOrder
                .builder()
                .address(savedAddress)
                .status(OrderStatus.REQUESTED)
                .timeRequested(LocalDate.now())
                .cart(savedCart)
                .build();
        orderRepository.save(saveOrder);

    }

    public List<WebOrder> getallOrder() throws GlobalException {
        List<WebOrder> webOrders = orderRepository.findAll();
        if (webOrders.isEmpty()) {
            throw new GlobalException("no orders to dispaly");
        }
        return webOrders;
    }

    public WebOrder getByidwebOrder(Long id) throws GlobalException {
        Optional<WebOrder> webOrder = orderRepository.findById(id);
        if (webOrder.isPresent()) {
            return webOrder.get();
        }
        throw new GlobalException("no order to dispaly with id " + id);
    }

    public void cancelrequest(Long id, WebOrder webOrder) throws GlobalException {
        WebOrder cancelledOrder = orderRepository.findById(id).get();
        if (cancelledOrder != null) {
            cancelledOrder.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(cancelledOrder);
        }
        throw new GlobalException("no order to cancel with id" + id);
    }

}
