package com.springbootexpert.rest.dto;
import com.springbootexpert.domain.entity.ItemPedido;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedido> itens;
}
