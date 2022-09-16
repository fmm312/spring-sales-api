package com.springbootexpert.domain.repository;

import com.springbootexpert.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
