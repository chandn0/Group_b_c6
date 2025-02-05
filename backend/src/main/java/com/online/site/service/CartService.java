package com.online.site.service;

import java.util.Collection;

import com.online.site.entity.Cart;
import com.online.site.entity.ProductInOrder;
import com.online.site.entity.User;

public interface CartService {
    Cart getCart(User user);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);

    void delete(String itemId, User user);

    void checkout(User user);
}
