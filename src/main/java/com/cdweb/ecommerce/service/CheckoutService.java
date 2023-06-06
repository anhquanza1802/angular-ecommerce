package com.cdweb.ecommerce.service;

import com.cdweb.ecommerce.dto.Purchase;
import com.cdweb.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
