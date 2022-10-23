package com.jpa.service;

import com.jpa.entity.Member;
import com.jpa.entity.Order;
import com.jpa.entity.Product;
import com.jpa.repository.MemberRepositoryJpa;
import com.jpa.repository.ProductRepositoryJpa;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
@Rollback(value = false)
class OrderServiceTest {

    @Autowired
    private MemberRepositoryJpa memberRepositoryJpa;

    @Autowired
    private ProductRepositoryJpa productRepositoryJpa;

    @Test
    @Transactional
    void mappingTest() {
        for (Member member : memberRepositoryJpa.findAll()) {
            log.debug(member.toString());
        }

        for (Product product : productRepositoryJpa.findAll()) {
            log.debug(product.toString());
        }

        Member member = memberRepositoryJpa.findById(1L).orElseThrow(NoSuchElementException::new);
        List<Product> products = productRepositoryJpa.findAll();
        List<Order> orders = products.stream()
                .map(product -> Order.create(member, product))
                .collect(Collectors.toList());

//        member.removeProducts(products);
//        member.removeAllProducts();
//        member.getOrders().removeAll(member.getOrders());
//        member.getOrders().addAll(orders);
        member.addProducts(products);
    }

    @Test
    @Transactional
    void checkOrders() {
        Member member = memberRepositoryJpa.findById(1L).orElseThrow(NoSuchElementException::new);

        for (Order order : member.getOrders()) {
            log.debug(order.toString());
        }

    }

    @Test
    @Transactional
    void removeProductFromOrders() {
        Member member = memberRepositoryJpa.findById(1L).orElseThrow(NoSuchElementException::new);
        Product product = productRepositoryJpa.findById(2L).orElseThrow(NoSuchElementException::new);

        member.removeProduct(product);
    }
}