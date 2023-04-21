package com.serratec.domain.DAO;

import com.serratec.domain.models.PedItem;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.models.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedItemDAO implements CrudDAO<PedItem> {
    PreparedStatement pInclusao = null;

    public PedItemDAO() {
        prepararSqlInclusao();
    }

    @Override
    public void prepararSqlInclusao() {
        String sql = "insert into " + CreateDAO.SCHEMA + ".peditem";
        sql += " (idpedido, idproduto, vlunitario, vldesconto, quantidade)";
        sql += " values ";
        sql += " (?, ?, ?, ?, ?)";

        try {
            pInclusao = CreateDAO.CONEXAO.getC().prepareStatement(sql);
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

    public List<PedItem> buscarPedItemsPorIdPedido(int idPedido) {
        List<PedItem> pedItems = new ArrayList<>();

        String sql = "SELECT * FROM " + CreateDAO.SCHEMA + ".peditem ";
        sql += "LEFT JOIN " + CreateDAO.SCHEMA + ".pedido ON pedido.idpedido = ";
        sql += "peditem.idpedido WHERE peditem.idpedido = " + idPedido + " and pedido.idpedido = " + idPedido;

        ResultSet tabela;

        tabela = CreateDAO.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var pedItem = new PedItem();
                var produto = new Produto();
                var pedido = new Pedido();

                pedido.setIdPedido(tabela.getInt("idpedido"));
                produto.setIdProduto(tabela.getInt("idproduto"));

                pedItem.setIdPedItem(tabela.getInt("idpeditem"));
                pedItem.setQuantidade(tabela.getDouble("quantidade"));
                pedItem.setVlUnitario(tabela.getDouble("vlunitario"));
                pedItem.setVlDesconto(tabela.getDouble("vldesconto"));
                pedItem.setPedido(pedido);
                pedItem.setProduto(produto);

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

    @Override
    public void alterar(PedItem pedItem) {
        String sql = "update " +
                CreateDAO.SCHEMA + ".peditem set " +
                "idpedido = '" + pedItem.getPedido().getIdPedido() + "'" +
                ", idproduto = '" + pedItem.getProduto().getIdProduto() + "'" +
                ", vlunitario = '" + pedItem.getVlUnitario() + "'" +
                ", vldesconto = '" + pedItem.getVlDesconto() + "' " +
                ", quantidade = '" + pedItem.getQuantidade() + "' " +
                "where idpeditem = " + pedItem.getIdPedItem();
        CreateDAO.CONEXAO.updateQuery(sql);
    }

    @Override
    public void apagarPorId(int idPedItem) {
        String sql = "delete from " + CreateDAO.SCHEMA + ".peditem" +
                " where idpeditem = " + idPedItem;

        CreateDAO.CONEXAO.updateQuery(sql);
    }

    @Override
    public PedItem buscarPorId(int idPedItem) {
        PedItem pedItem = new PedItem();
        String sql = "SELECT * FROM " + CreateDAO.SCHEMA + ".peditem WHERE idpeditem = " + idPedItem;
        ResultSet tabela;

        tabela = CreateDAO.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                var produto = new Produto();
                var pedido = new Pedido();

                pedido.setIdPedido(tabela.getInt("idpedido"));
                produto.setIdProduto(tabela.getInt("idproduto"));

                pedItem.setIdPedItem(tabela.getInt("idpeditem"));
                pedItem.setQuantidade(tabela.getDouble("quantidade"));
                pedItem.setVlUnitario(tabela.getDouble("vlunitario"));
                pedItem.setVlDesconto(tabela.getDouble("vldesconto"));
                pedItem.setPedido(pedido);
                pedItem.setProduto(produto);
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
        String sql = "select * from " + CreateDAO.SCHEMA + ".peditem order by peditem";
        ResultSet tabela;

        tabela = CreateDAO.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var pedidoRepository = new PedidoDAO();
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
