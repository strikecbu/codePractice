package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class CheckoutService {

    private final PriceValidatorService validateService;

    public CheckoutService(PriceValidatorService validateService) {
        this.validateService = validateService;
    }

    public CheckoutResponse checkout(Cart cart) {
        startTimer();
        List<CartItem> validCartItem = cart.getCartItemList()
                .parallelStream()
                .peek(cartItem -> cartItem.setExpired(validateService.isCartItemInvalid(cartItem)))
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        timeTaken();
        if (validCartItem.size() > 0) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, validCartItem);
        }
        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }
}
