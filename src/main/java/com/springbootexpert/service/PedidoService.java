package com.springbootexpert.service;

import com.springbootexpert.domain.entity.Pedido;
import com.springbootexpert.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
