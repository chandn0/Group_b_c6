package com.online.site.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.site.entity.Cart;
import com.online.site.entity.User;
import com.online.site.enums.ResultEnum;
import com.online.site.exception.MyException;
import com.online.site.repository.CartRepository;
import com.online.site.repository.UserRepository;
import com.online.site.service.UserService;

import java.util.Collection;
import java.util.List;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    @Override
    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Collection<User> findByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    @Transactional
    public User save(User user) {
        // register
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User savedUser = userRepository.save(user);

            // initial Cart
            Cart savedCart = cartRepository.save(new Cart(savedUser));
            savedUser.setCart(savedCart);
            return userRepository.save(savedUser);

        } catch (Exception e) {
            throw new MyException(ResultEnum.VALID_ERROR);
        }

    }

    @Override
    @Transactional
    public User update(User user) {
        User oldUser = userRepository.findByEmail(user.getEmail());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        oldUser.setAddress(user.getAddress());
        return userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Object update(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public User update(String email) {
        User user = findOne(email);
        user.setRole("ROLE_MANAGER");

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Page<User> findAll(PageRequest request) {
        return userRepository.findAll(request);
    }

    @Override
    @Transactional
    public User removeAdmin(String email) {
        User user = findOne(email);
        user.setRole("ROLE_CUSTOMER");

        return userRepository.save(user);
    }

}
