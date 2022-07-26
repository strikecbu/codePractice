package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService validatorService = new PriceValidatorService();
    CheckoutService service = new CheckoutService(validatorService);

    @Test
    void checkCores() {
        System.out.println("No of Processors: " + Runtime.getRuntime().availableProcessors());
    }

    @Test
    void checkout_6() {
        Cart cart = DataSet.createCart(6);

        CheckoutResponse checkoutResponse = service.checkout(cart);

        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());

    }
    @Test
    void checkout_13() {
        Cart cart = DataSet.createCart(13);

        CheckoutResponse checkoutResponse = service.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());

    }
}
