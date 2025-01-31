package com.discount.discountclient.api;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.discount.discountclient.entity.Discount;
import com.discount.discountclient.service.impl.DiscountService;

@CrossOrigin
@RestController
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @PostMapping("/add/coupon/{code}/{percentage}")
    public ResponseEntity<Discount> createCoupon(@PathVariable String code, @PathVariable Integer percentage) {

        return ResponseEntity.ok(discountService.createCoupon(code, percentage));
    }

    @GetMapping("/coupon/list")
    public Page<Discount> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Discount> discountPage;
        discountPage = discountService.findAll(request);
        return discountPage;
    }

    @GetMapping("/coupon/alllist")
    public List<Discount> orderList() {

        return discountService.findAll();
    }

    @PostMapping("/delete/coupon/{code}")
    public ResponseEntity<Discount> deleteCoupon(@PathVariable("code") String code) {
        discountService.deleteCoupon(code);
        return ResponseEntity.ok(null);
    }

}
