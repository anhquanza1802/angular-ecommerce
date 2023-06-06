package com.cdweb.ecommerce.dto;

import com.cdweb.ecommerce.entity.Address;
import com.cdweb.ecommerce.entity.Customer;
import com.cdweb.ecommerce.entity.Order;
import com.cdweb.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;
}
