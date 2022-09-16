package com.springbootexpert.domain.repository;

import com.springbootexpert.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
}
