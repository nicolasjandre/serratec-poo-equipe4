package com.serratec.domain.services;

import com.serratec.domain.models.PedItem;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.models.Produto;
import com.serratec.domain.repository.PedItemRepository;
import com.serratec.domain.repository.PedidoRepository;

import java.util.List;

public class PedItemService {

    public void criarPedItemAposPedido(Pedido pedido, Double desconto, List<Double> qtdVendida) {
        var pedidoRepository = new PedidoRepository();
        var pedItemRepository = new PedItemRepository();
        var ultimoPedido = pedidoRepository.buscarUltimoPedidoCriado();

        for (Produto produto : pedido.getProdutos()) {
            var pedItem = new PedItem();
            int index = pedido.getProdutos().indexOf(produto);

            pedItem.setPedido(ultimoPedido);
            pedItem.setProduto(produto);
            pedItem.setVlUnitario(produto.getVlVenda());
            pedItem.setVlDesconto(desconto);
            pedItem.setQuantidade(qtdVendida.get(index));

            pedItemRepository.incluir(pedItem);
        }
    }
}
