package com.online.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.site.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
