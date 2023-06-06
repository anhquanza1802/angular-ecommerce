package com.cdweb.ecommerce.service;

import com.cdweb.ecommerce.dao.CustomerRepository;
import com.cdweb.ecommerce.dto.Purchase;
import com.cdweb.ecommerce.dto.PurchaseResponse;
import com.cdweb.ecommerce.entity.Customer;
import com.cdweb.ecommerce.entity.Order;
import com.cdweb.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // lay du lieu order tu dto
        Order order = purchase.getOrder();
        // tao trackingNumber
        String orderTrackingNumber =  generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        //populate order with orderItem
        Set<OrderItem> orderItems =  purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));
        //
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());
        Customer customer = purchase.getCustomer();

        String email = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(email);

        if (customerFromDB != null){
            customer = customerFromDB;
        }
        customer.add(order);

        customerRepository.save(customer);
        return new PurchaseResponse(orderTrackingNumber) ;
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
