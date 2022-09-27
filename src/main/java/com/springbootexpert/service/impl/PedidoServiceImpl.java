package com.springbootexpert.service.impl;

import com.springbootexpert.domain.entity.Cliente;
import com.springbootexpert.domain.entity.ItemPedido;
import com.springbootexpert.domain.entity.Pedido;
import com.springbootexpert.domain.entity.Produto;
import com.springbootexpert.domain.repository.Clientes;
import com.springbootexpert.domain.repository.ItensPedido;
import com.springbootexpert.domain.repository.Pedidos;
import com.springbootexpert.domain.repository.Produtos;
import com.springbootexpert.exception.RegraNegocioException;
import com.springbootexpert.rest.dto.ItemPedidoDTO;
import com.springbootexpert.rest.dto.PedidoDTO;
import com.springbootexpert.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidosRepository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itensPedidos = converterItens(pedido, dto.getItens());
        pedidosRepository.save(pedido);
        itensPedidoRepository.saveAll(itensPedidos);
        pedido.setItens(itensPedidos);
        return pedido;
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if(itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar pedido sem itens");
        }

        return itens
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException( "Código de produto inválido" + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
