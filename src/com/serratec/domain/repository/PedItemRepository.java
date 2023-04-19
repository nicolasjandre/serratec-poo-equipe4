package com.serratec.domain.repository;

import com.serratec.domain.models.PedItem;
import com.serratec.domain.models.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedItemRepository implements CRUDRepository<PedItem> {
    PreparedStatement pInclusao = null;

    public PedItemRepository() {
        prepararSqlInclusao();
    }

    @Override
    public void prepararSqlInclusao() {
        String sql = "insert into " + MainRepository.SCHEMA + ".peditem";
        sql += " (idpedido, idproduto, vlunitario, vldesconto, quantidade)";
        sql += " values ";
        sql += " (?, ?, ?, ?, ?)";

        try {
            pInclusao = MainRepository.CONEXAO.getC().prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incluir(PedItem pedItem) {
        try {
            pInclusao.setInt(1, pedItem.getPedido().getIdPedido());
            pInclusao.setInt(2, pedItem.getProduto().getIdProduto());
            pInclusao.setDouble(3, pedItem.getVlUnitario());
            pInclusao.setDouble(4, pedItem.getVlDesconto());
            pInclusao.setDouble(5, pedItem.getQuantidade());

            pInclusao.executeUpdate();
        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("is null")) {
                System.err.println("\nPedItem não incluído.\nVerifique se foi chamado o connect:\n" + e);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void alterar(PedItem pedItem) {
        String sql = "update " +
                MainRepository.SCHEMA + ".peditem set " +
                "idpedido = '" + pedItem.getPedido().getIdPedido() + "'" +
                ", idproduto = '" + pedItem.getProduto().getIdProduto() + "'" +
                ", vlunitario = '" + pedItem.getVlUnitario() + "'" +
                ", vldesconto = '" + pedItem.getVlDesconto() + "' " +
                ", quantidade = '" + pedItem.getQuantidade() + "' " +
                "where idpeditem = " + pedItem.getIdPedItem();
        MainRepository.CONEXAO.updateQuery(sql);
    }

    @Override
    public void apagarPorId(int idPedItem) {
        String sql = "delete from " + MainRepository.SCHEMA + ".peditem" +
                " where idpeditem = " + idPedItem;

        MainRepository.CONEXAO.query(sql);
    }

    @Override
    public PedItem buscarPorId(int idPedItem) {
        PedItem pedItem = null;
        String sql = "SELECT * FROM " + MainRepository.SCHEMA + ".peditem WHERE idpeditem = " + idPedItem;
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                var pedidoRepository = new PedidoRepository();
                var produto = new Produto();
                var pedido = pedidoRepository.buscarPorId(tabela.getInt("idpedido"));
                
                pedItem = new PedItem();

                pedItem.setProduto(produto);
                pedItem.setPedido(pedido);
                pedItem.setIdPedItem(tabela.getInt("idpedItem"));
                pedItem.setQuantidade(tabela.getDouble("valortotal"));
                pedItem.setVlUnitario(tabela.getDouble("vlunitario"));
                pedItem.setVlDesconto(tabela.getDouble("vldesconto"));
            }

            tabela.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedItem;
    }

    @Override
    public List<PedItem> buscarTodos() {
        List<PedItem> pedItems = new ArrayList<>();
        String sql = "select * from " + MainRepository.SCHEMA + ".peditem order by peditem";
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var pedidoRepository = new PedidoRepository();
                var produto = new Produto();
                var pedido = pedidoRepository.buscarPorId(tabela.getInt("idpedido"));

                var pedItem = new PedItem();

                pedItem.setProduto(produto);
                pedItem.setPedido(pedido);
                pedItem.setIdPedItem(tabela.getInt("idpedItem"));
                pedItem.setQuantidade(tabela.getDouble("valortotal"));
                pedItem.setVlUnitario(tabela.getDouble("vlunitario"));
                pedItem.setVlDesconto(tabela.getDouble("vldesconto"));

                pedItems.add(pedItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                tabela.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return pedItems;
    }


}
