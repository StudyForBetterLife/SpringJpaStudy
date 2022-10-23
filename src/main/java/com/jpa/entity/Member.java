package com.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "member_id")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public void removeProducts(List<Product> products) {
        orders.removeIf(order -> products.contains(order.getProduct()));
    }

    public void removeProduct(Product product) {
        orders.removeIf(order -> order.getProduct().equals(product));
    }

    public void removeAllProducts() {
        orders.clear();
    }

    public void addProducts(List<Product> products) {
        List<Product> productsInMember = orders.stream().map(Order::getProduct).collect(Collectors.toList());

        orders.addAll(products.stream()
                .filter(product -> !productsInMember.contains(product))
                .map(product -> Order.create(this, product))
                .collect(Collectors.toList()));
    }
}
