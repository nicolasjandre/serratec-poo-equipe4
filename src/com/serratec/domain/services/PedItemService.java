package com.serratec.domain.services;

import com.serratec.domain.models.PedItem;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.models.Produto;
import com.serratec.domain.repository.PedItemRepository;
import com.serratec.domain.repository.PedidoRepository;

public class PedItemService {

    public void criarPedItemAposPedido(Pedido pedido) {
        var pedidoRepository = new PedidoRepository();
        var ultimoPedido = pedidoRepository.buscarUltimoPedidoCriado();

        for (Produto produto : pedido.getProdutos()) {
            var pedItemRepository = new PedItemRepository();
            var pedItem = new PedItem();

            pedItem.setPedido(ultimoPedido);
            pedItem.setProduto(produto);
            pedItem.setVlUnitario(produto.getVlVenda());
            pedItem.setVlDesconto(pedido.getDesconto());
            pedItem.setQuantidade(produto.getQtdVendida());

            pedItemRepository.incluir(pedItem);
        }
    }
}
