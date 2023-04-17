package com.serratec.domain.repository;

import com.serratec.utils.Util;
import com.serratec.domain.models.Cliente;
import com.serratec.domain.settings.Conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private Conexao conexao;
    private String schema;
    PreparedStatement pInclusao = null;

    public ClienteRepository(Conexao conexao, String schema) {
        this.conexao = conexao;
        this.schema = schema;
        prepararSqlInclusao();
    }

    private void prepararSqlInclusao() {
        String sql = "insert into "+ this.schema + ".cliente";
        sql += " (nome, cpf, dtnascimento, endereco, telefone)";
        sql += " values ";
        sql += " (?, ?, ?, ?, ?)";

        try {
            pInclusao =  conexao.getC().prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int incluirCliente(Cliente cliente) {
        try {
            pInclusao.setString(1, cliente.getNome());
            pInclusao.setString(2, cliente.getCpf());
            pInclusao.setDate(3, cliente.getDtNascimento());
            pInclusao.setString(4, cliente.getEndereco());
            pInclusao.setString(5, cliente.getTelefone());

            return pInclusao.executeUpdate();
        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("is null")) {
                System.err.println("\nCliente não incluído.\nVerifique se foi chamado o connect:\n" + e);
            } else {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public void alterarCliente(Cliente cliente) {
        String sql = "update " +
                this.schema + ".cliente set " +
                "nome = '" + cliente.getNome() + "'" +
                ", cpf = '" + cliente.getCpf() + "'" +
                ", dtnascimento = '" + cliente.getDtNascimento() + "'" +
                ", endereco = '" + cliente.getEndereco() + "' " +
                ", telefone = '" + cliente.getTelefone() + "' " +
                "where idcliente = " + cliente.getIdCliente();
        conexao.query(sql);
    }

    public Cliente buscarClientePorId(int idCliente) {
        Cliente cliente = new Cliente();
        ResultSet tabela;

        String sql = "select * from " + this.schema + ".cliente where idcliente = " + idCliente;

        tabela = conexao.query(sql);

        try {
            if (tabela.next()) {
                cliente.setIdCliente(tabela.getInt("idcliente"));
                cliente.setNome(tabela.getString("nome"));
                cliente.setCpf(tabela.getString("cpf"));
                cliente.setDtNascimento(tabela.getDate("dtnascimento"));
                cliente.setEndereco(tabela.getString("endereco"));
                cliente.setTelefone(tabela.getString("telefone"));
            } else
                System.out.println("Cliente com o ID: [" + idCliente + "] não localizado.");

            tabela.close();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        return cliente;
    }

    public void apagarClientePorId(int idCliente) {
        String sql = "delete from " + this.schema + ".cliente" +
                " where idcliente = " + idCliente;

        conexao.query(sql);
    }

    public List<Cliente> buscarClientesPeloNome(String nome) {
        List<Cliente> clientes = new ArrayList<>();
        String sql;
        ResultSet tabela;

        sql = "SELECT * FROM " + this.schema + ".cliente WHERE LOWER(nome) ILIKE '%" + nome.toLowerCase() + "%'";

        tabela = conexao.query(sql);

        try {
            while (tabela.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(tabela.getInt("idcliente"));
                cliente.setNome(tabela.getString("nome"));
                cliente.setCpf(tabela.getString("cpf"));
                cliente.setDtNascimento(tabela.getDate("dtnascimento"));
                cliente.setEndereco(tabela.getString("endereco"));
                cliente.setTelefone(tabela.getString("telefone"));

                clientes.add(cliente);
            }

            tabela.close();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        return clientes;
    }

    public void listarClientes() {
        ResultSet tabela;

        String sql = "select * from " + this.schema + ".cliente order by idcliente";

        tabela = conexao.query(sql);

        try {
            tabela.last();
            int rowCount = tabela.getRow();
            System.out.println("Quantidade de clientes: " +rowCount);

            if (rowCount > 0) {
                System.out.printf("%-13s %-35s %-20s %-30s %-70s %s\n",
                        "CÓDIGO", "NOME", "CPF", "NASCIMENTO", "ENDEREÇO", "TELEFONE");
            } else {
                System.out.println("\nNão possui dados.");
                return;
            }

            tabela.beforeFirst();

            while (tabela.next()) {
                System.out.printf("%-13s %-35s %-20s %-30s %-70s %s\n",
                        tabela.getInt("idcliente"),
                        tabela.getString("nome"),
                        Util.formatCPF(tabela.getString("cpf")),
                        Util.formatDate(tabela.getDate("dtnascimento")),
                        tabela.getString("endereco"),
                        tabela.getString("telefone")
                );
            }

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
