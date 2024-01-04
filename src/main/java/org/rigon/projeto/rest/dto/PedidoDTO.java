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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private  Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}
