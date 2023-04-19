package com.serratec.domain.repository;

import com.serratec.domain.models.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository implements CRUDRepository<Cliente> {
    PreparedStatement pInclusao = null;

    public ClienteRepository() {
        prepararSqlInclusao();
    }

    @Override
    public void prepararSqlInclusao() {
        String sql = "insert into " + MainRepository.SCHEMA + ".cliente";
        sql += " (nome, cpf, dtnascimento, endereco, telefone)";
        sql += " values ";
        sql += " (?, ?, ?, ?, ?)";

        try {
            pInclusao = MainRepository.CONEXAO.getC().prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incluir(Cliente cliente) {
        try {
            pInclusao.setString(1, cliente.getNome());
            pInclusao.setString(2, cliente.getCpf());
            pInclusao.setDate(3, cliente.getDtNascimento());
            pInclusao.setString(4, cliente.getEndereco());
            pInclusao.setString(5, cliente.getTelefone());

            pInclusao.executeUpdate();
        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("is null")) {
                System.err.println("\nCliente não incluído.\nVerifique se foi chamado o connect:\n" + e);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void alterar(Cliente cliente) {
        String sql = "update " +
                MainRepository.SCHEMA + ".cliente set " +
                "nome = '" + cliente.getNome() + "'" +
                ", cpf = '" + cliente.getCpf() + "'" +
                ", dtnascimento = '" + cliente.getDtNascimento() + "'" +
                ", endereco = '" + cliente.getEndereco() + "' " +
                ", telefone = '" + cliente.getTelefone() + "' " +
                "where idcliente = " + cliente.getIdCliente();
        MainRepository.CONEXAO.updateQuery(sql);
    }

    @Override
    public Cliente buscarPorId(int idCliente) {
        var cliente = new Cliente();
        ResultSet tabela;

        String sql = "select * from " + MainRepository.SCHEMA + ".cliente where idcliente = " + idCliente;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                cliente.setIdCliente(tabela.getInt("idcliente"));
                cliente.setNome(tabela.getString("nome"));
                cliente.setCpf(tabela.getString("cpf"));
                cliente.setDtNascimento(tabela.getDate("dtnascimento"));
                cliente.setEndereco(tabela.getString("endereco"));
                cliente.setTelefone(tabela.getString("telefone"));
            } else {
                System.err.println("Cliente com o ID: [" + idCliente + "] não localizado.");
            }

            tabela.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public Cliente buscarPorCpf(String cpf) {
        var cliente = new Cliente();
        ResultSet tabela;

        String sql = "select * from " + MainRepository.SCHEMA + ".cliente where cpf = " + cpf;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                cliente.setIdCliente(tabela.getInt("idcliente"));
                cliente.setNome(tabela.getString("nome"));
                cliente.setCpf(tabela.getString("cpf"));
                cliente.setDtNascimento(tabela.getDate("dtnascimento"));
                cliente.setEndereco(tabela.getString("endereco"));
                cliente.setTelefone(tabela.getString("telefone"));
            } else
                System.out.println("Cliente com o CPF: [" + cpf + "] não localizado.");

            tabela.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public void apagarPorId(int idCliente) {
        String sql = "delete from " + MainRepository.SCHEMA + ".cliente" +
                " where idcliente = " + idCliente;

        MainRepository.CONEXAO.updateQuery(sql);
    }

    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> clientes = new ArrayList<>();
        String sql;
        ResultSet tabela;

        sql = "SELECT * FROM " + MainRepository.SCHEMA + ".cliente WHERE LOWER(nome) ILIKE '%" + nome.toLowerCase() + "%'";

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var cliente = new Cliente();
                cliente.setIdCliente(tabela.getInt("idcliente"));
                cliente.setNome(tabela.getString("nome"));
                cliente.setCpf(tabela.getString("cpf"));
                cliente.setDtNascimento(tabela.getDate("dtnascimento"));
                cliente.setEndereco(tabela.getString("endereco"));
                cliente.setTelefone(tabela.getString("telefone"));

                clientes.add(cliente);
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

        return clientes;
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "select * from " + MainRepository.SCHEMA + ".cliente order by idcliente";
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var cliente = new Cliente();
                cliente.setIdCliente(tabela.getInt("idcliente"));
                cliente.setNome(tabela.getString("nome"));
                cliente.setCpf(tabela.getString("cpf"));
                cliente.setDtNascimento(tabela.getDate("dtnascimento"));
                cliente.setEndereco(tabela.getString("endereco"));
                cliente.setTelefone(tabela.getString("telefone"));

                clientes.add(cliente);
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

        return clientes;
    }
}