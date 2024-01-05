package org.rigon.projeto.rest.dto;

//{
//        "cliente" : 1,
//        "total" : 100,
//        "items" : [
//            {
//                "produto" : 1,
//                "quantidade" : 10
//            }
//        ]
//}

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rigon.projeto.validation.NotEmptyList;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private  Integer cliente;

    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;

    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> items;
}
