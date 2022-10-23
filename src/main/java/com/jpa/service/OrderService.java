package com.jpa.service;

import com.jpa.entity.Member;
import com.jpa.entity.Order;
import com.jpa.entity.Product;
import com.jpa.repository.MemberRepositoryJpa;
import com.jpa.repository.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final MemberRepositoryJpa memberRepositoryJpa;
    private final ProductRepositoryJpa productRepositoryJpa;

    @Transactional
    public void saveOrders(Long memberId, List<Long> productIds) {
        final Member member = memberRepositoryJpa.findById(memberId).orElseThrow(NoSuchElementException::new);
        final List<Product> products = productRepositoryJpa.findAllById(productIds);

        makeOrders(member, products);
    }

    private void makeOrders(final Member member, List<Product> products) {
        final List<Order> orders = products.stream()
                .distinct()
                .map(product -> Order.create(member, product))
                .collect(Collectors.toList());

        member.getOrders().addAll(orders);
    }
}
