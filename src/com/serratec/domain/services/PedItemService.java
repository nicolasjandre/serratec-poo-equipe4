package com.serratec.domain.services;

import com.serratec.domain.models.PedItem;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.models.Produto;
import com.serratec.domain.repository.PedItemRepository;
import com.serratec.domain.repository.PedidoRepository;
import com.serratec.domain.repository.ProdutoRepository;

import java.util.ArrayList;
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

    public void criarPedItemAposAlterarPedido(Pedido pedido) {
        var pedItemRepository = new PedItemRepository();
        var produtoService = new ProdutoService();
        List<Produto> produtos = new ArrayList<>();

        for (PedItem pedItem : pedido.getPedItems()) {
            pedItemRepository.incluir(pedItem);

            Double estoqueAtual = pedItem.getProduto().getEstoque();
            Double qtdVendida = pedItem.getQuantidade();
            Double novoEstoque = estoqueAtual - qtdVendida;
            pedItem.getProduto().setEstoque(novoEstoque);
            produtos.add(pedItem.getProduto());
            pedItemRepository.apagarPorId(pedItem.getIdPedItem());
        }

        produtoService.atualizarEstoque(produtos);

    }
    public void apagarPorPedido(Pedido pedido) {
        var pedItemRepository = new PedItemRepository();
        var produtoService = new ProdutoService();

        List<Produto> produtos = new ArrayList<>();

        var pedItems = buscarPedItemsPorIdPedido(pedido.getIdPedido());

        for (PedItem pedItem : pedItems) {
            Double estoqueAtual = pedItem.getProduto().getEstoque();
            Double qtdVendida = pedItem.getQuantidade();
            Double novoEstoque = estoqueAtual + qtdVendida;
            pedItem.getProduto().setEstoque(novoEstoque);
            produtos.add(pedItem.getProduto());
            pedItemRepository.apagarPorId(pedItem.getIdPedItem());
        }

        produtoService.atualizarEstoque(produtos);
    }
    public List<PedItem> buscarPedItemsPorIdPedido(int idPedido) {
        var pedItemsRepository = new PedItemRepository();
        var pedidoRepository = new PedidoRepository();
        var produtoRepository = new ProdutoRepository();

        List<PedItem> pedItems = pedItemsRepository.buscarPedItemsPorIdPedido(idPedido);

        for (PedItem pedItem : pedItems) {
            var produto = produtoRepository.buscarPorId(pedItem.getProduto().getIdProduto());
            var pedido = pedidoRepository.buscarPorId(pedItem.getPedido().getIdPedido());
            pedItem.setProduto(produto);
            pedItem.setPedido(pedido);
        }

        return pedItems;
    }
}
