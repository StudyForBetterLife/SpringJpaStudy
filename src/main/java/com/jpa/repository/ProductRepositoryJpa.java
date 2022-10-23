package com.jpa.repository;

import com.jpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryJpa extends JpaRepository<Product, Long> {
}