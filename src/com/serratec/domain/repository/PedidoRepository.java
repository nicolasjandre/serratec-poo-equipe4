package com.serratec.domain.repository;

import com.serratec.domain.models.Cliente;
import com.serratec.domain.models.Pedido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository implements CRUDRepository <Pedido>{

    PreparedStatement pInclusao = null;
    public PedidoRepository() {
        prepararSqlInclusao();
    }

    @Override
    public void prepararSqlInclusao() {
        String sql = "insert into " + MainRepository.SCHEMA + ".pedido";
        sql += " (dtemissao, dtentrega, valortotal, valorbruto, observacao, idcliente)";
        sql += " values ";
        sql += " (?, ?, ?, ?, ?, ?)";

        try {
            pInclusao = MainRepository.CONEXAO.getC().prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incluir(Pedido pedido) {
        try {
            pInclusao.setDate(1, pedido.getDtEmissao());
            pInclusao.setDate(2, pedido.getDtEntrega());
            pInclusao.setDouble(3, pedido.getValorTotal());
            pInclusao.setDouble(4, pedido.getValorBruto());
            pInclusao.setString(5, pedido.getObervacao());
            pInclusao.setInt(6, pedido.getCliente().getIdCliente());

            pInclusao.executeUpdate();

        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("is null")) {
                System.err.println("\nPedido não incluído.\nVerifique se foi chamado o connect:\n" + e);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void alterar(Pedido pedido) {
        String sql = "update " +
                MainRepository.SCHEMA + ".pedido set " +
                "dtemissao = '" + pedido.getDtEmissao() + "'" +
                ", dtentrega = '" + pedido.getDtEntrega() + "'" +
                ", valortotal = '" + pedido.getValorTotal() + "'" +
                ", valorbruto = '" + pedido.getValorBruto() + "'" +
                ", observacao = '" + pedido.getObervacao() + "' " +
                ", idcliente = '" + pedido.getCliente().getIdCliente() + "' " +
                "where idpedido = " + pedido.getIdPedido();
        MainRepository.CONEXAO.updateQuery(sql);
    }

    @Override
    public void apagarPorId(int idPedido) {
        String sql = "delete from " + MainRepository.SCHEMA + ".pedido" +
                " where idpedido = " + idPedido;

        MainRepository.CONEXAO.updateQuery(sql);
    }

    @Override
    public Pedido buscarPorId(int idPedido) {
        Pedido pedido = null;
        String sql = "SELECT * FROM " + MainRepository.SCHEMA + ".pedido WHERE idpedido = " + idPedido;
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                pedido = new Pedido();

                pedido.setIdPedido(tabela.getInt("idpedido"));
                pedido.setDtEmissao(tabela.getDate("dtemissao"));
                pedido.setDtEntrega(tabela.getDate("dtentrega"));
                pedido.setValorTotal(tabela.getDouble("valortotal"));
                pedido.setValorBruto(tabela.getDouble("valorbruto"));
                pedido.setObervacao(tabela.getString("observacao"));

                var clienteRepository = new ClienteRepository();
                Cliente cliente = clienteRepository.buscarPorId(tabela.getInt("idcliente"));
                pedido.setCliente(cliente);
            }

            tabela.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedido;
    }

    public List<Pedido> buscarPorCliente(Cliente cliente) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql;
        ResultSet tabela;

        sql = "SELECT * FROM " + MainRepository.SCHEMA + ".pedido WHERE idcliente = " + cliente.getIdCliente();

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var pedido = new Pedido();

                pedido.setIdPedido(tabela.getInt("idpedido"));
                pedido.setDtEmissao(tabela.getDate("dtemissao"));
                pedido.setDtEntrega(tabela.getDate("dtentrega"));
                pedido.setValorBruto(tabela.getDouble("valorbruto"));
                pedido.setValorTotal(tabela.getDouble("valortotal"));
                pedido.setObervacao(tabela.getString("observacao"));
                pedido.setCliente(cliente);

                pedidos.add(pedido);
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

        return pedidos;
    }

    @Override
    public List<Pedido> buscarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "select * from " + MainRepository.SCHEMA + ".pedido order by idpedido";
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var pedido = new Pedido();
                var cliente = new Cliente();

                cliente.setIdCliente(tabela.getInt("idcliente"));

                pedido.setIdPedido(tabela.getInt("idpedido"));
                pedido.setDtEmissao(tabela.getDate("dtemissao"));
                pedido.setDtEntrega(tabela.getDate("dtentrega"));
                pedido.setValorTotal(tabela.getDouble("valortotal"));
                pedido.setValorBruto(tabela.getDouble("valorbruto"));
                pedido.setObervacao(tabela.getString("observacao"));

                pedido.setCliente(cliente);
                pedidos.add(pedido);
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

        return pedidos;
    }

    public List<Pedido> buscarPorData(String data1, String data2) {
        List<Pedido> pedidos = new ArrayList<>();
        ResultSet tabela;
        String sql;

        sql = "SELECT * FROM " + MainRepository.SCHEMA + ".pedido WHERE pedido.dtemissao ";
        sql += "BETWEEN '" + data1 + " 00:00:00' AND '" + data2 + " 23:59:59'";

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var pedido = new Pedido();
                var cliente = new Cliente();

                cliente.setIdCliente(tabela.getInt("idcliente"));

                pedido.setIdPedido(tabela.getInt("idpedido"));
                pedido.setDtEmissao(tabela.getDate("dtemissao"));
                pedido.setDtEntrega(tabela.getDate("dtentrega"));
                pedido.setValorTotal(tabela.getDouble("valortotal"));
                pedido.setValorBruto(tabela.getDouble("valorbruto"));
                pedido.setObervacao(tabela.getString("observacao"));

                pedido.setCliente(cliente);
                pedidos.add(pedido);
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

        return pedidos;
    }

    public Pedido buscarUltimoPedidoCriado() {

        Pedido pedido = null;
        String sql = "SELECT * FROM " + MainRepository.SCHEMA + ".pedido ORDER BY idpedido DESC LIMIT 1 ";
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                pedido = new Pedido();

                pedido.setIdPedido(tabela.getInt("idpedido"));
                pedido.setDtEmissao(tabela.getDate("dtemissao"));
                pedido.setDtEntrega(tabela.getDate("dtentrega"));
                pedido.setValorTotal(tabela.getDouble("valortotal"));
                pedido.setValorBruto(tabela.getDouble("valorbruto"));
                pedido.setObervacao(tabela.getString("observacao"));

                var clienteRepository = new ClienteRepository();
                Cliente cliente = clienteRepository.buscarPorId(tabela.getInt("idcliente"));
                pedido.setCliente(cliente);
            }

            tabela.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedido;

    }
}

