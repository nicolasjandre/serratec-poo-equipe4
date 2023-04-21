package com.serratec.domain.services;

import com.serratec.domain.models.PedItem;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.models.Produto;
import com.serratec.domain.DAO.PedItemDAO;
import com.serratec.domain.DAO.PedidoDAO;
import com.serratec.domain.DAO.ProdutoDAO;

import java.util.ArrayList;
import java.util.List;

public class PedItemService {

    public void criarPedItemAposPedido(Pedido pedido, Double desconto, List<Double> qtdVendida) {
        var pedidoDAO = new PedidoDAO();
        var pedItemDAO = new PedItemDAO();

        var ultimoPedido = pedidoDAO.buscarUltimoPedidoCriado();

        for (Produto produto : pedido.getProdutos()) {
            var pedItem = new PedItem();
            int index = pedido.getProdutos().indexOf(produto);

            pedItem.setPedido(ultimoPedido);
            pedItem.setProduto(produto);
            pedItem.setVlUnitario(produto.getVlVenda());
            pedItem.setVlDesconto(desconto);
            pedItem.setQuantidade(qtdVendida.get(index));

            pedItemDAO.incluir(pedItem);
        }
    }

    public void criarPedItemAposAlterarPedido(Pedido pedido) {
        var pedItemDAO = new PedItemDAO();
        var produtoService = new ProdutoService();
        List<Produto> produtos = new ArrayList<>();

        for (PedItem pedItem : pedido.getPedItems()) {
            pedItemDAO.incluir(pedItem);

            Double estoqueAtual = pedItem.getProduto().getEstoque();
            Double qtdVendida = pedItem.getQuantidade();
            Double novoEstoque = estoqueAtual - qtdVendida;
            pedItem.getProduto().setEstoque(novoEstoque);
            produtos.add(pedItem.getProduto());
            pedItemDAO.apagarPorId(pedItem.getIdPedItem());
        }

        produtoService.atualizarEstoque(produtos);

    }
    public void apagarPorPedido(Pedido pedido) {
        var pedItemDAO = new PedItemDAO();
        var produtoService = new ProdutoService();

        List<Produto> produtos = new ArrayList<>();

        var pedItems = buscarPedItemsPorIdPedido(pedido.getIdPedido());

        for (PedItem pedItem : pedItems) {
            Double estoqueAtual = pedItem.getProduto().getEstoque();
            Double qtdVendida = pedItem.getQuantidade();
            Double novoEstoque = estoqueAtual + qtdVendida;
            pedItem.getProduto().setEstoque(novoEstoque);
            produtos.add(pedItem.getProduto());
            pedItemDAO.apagarPorId(pedItem.getIdPedItem());
        }

        produtoService.atualizarEstoque(produtos);
    }
    public List<PedItem> buscarPedItemsPorIdPedido(int idPedido) {
        var pedItemsDAO = new PedItemDAO();
        var pedidoDAO = new PedidoDAO();
        var produtoDAO = new ProdutoDAO();

        List<PedItem> pedItems = pedItemsDAO.buscarPedItemsPorIdPedido(idPedido);

        for (PedItem pedItem : pedItems) {
            var produto = produtoDAO.buscarPorId(pedItem.getProduto().getIdProduto());
            var pedido = pedidoDAO.buscarPorId(pedItem.getPedido().getIdPedido());
            pedItem.setProduto(produto);
            pedItem.setPedido(pedido);
        }

        return pedItems;
    }
}
