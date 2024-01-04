package org.rigon.projeto.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.rigon.projeto.domain.entity.Cliente;
import org.rigon.projeto.domain.entity.ItemPedido;
import org.rigon.projeto.domain.entity.Pedido;
import org.rigon.projeto.domain.entity.Produto;
import org.rigon.projeto.domain.enums.StatusPedido;
import org.rigon.projeto.domain.repository.ClienteRepository;
import org.rigon.projeto.domain.repository.ItemPedidoRepository;
import org.rigon.projeto.domain.repository.PedidoRepository;
import org.rigon.projeto.domain.repository.ProdutoRepository;
import org.rigon.projeto.domain.service.PedidoService;
import org.rigon.projeto.exception.PedidoNaoEncontradoException;
import org.rigon.projeto.exception.RegraNegocioException;
import org.rigon.projeto.rest.dto.ItemPedidoDTO;
import org.rigon.projeto.rest.dto.PedidoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImp implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedidos = converterItems(pedido, pedidoDTO.getItems());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedidos);
        pedido.setItens(itemsPedidos);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return  pedidoRepository.save(pedido);
                }).orElseThrow(() ->
                        new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items.stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));


                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }


}
