package org.rigon.projeto.domain.service;

import org.rigon.projeto.domain.entity.Pedido;
import org.rigon.projeto.domain.enums.StatusPedido;
import org.rigon.projeto.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizarStatus(Integer id, StatusPedido statusPedido);
}
