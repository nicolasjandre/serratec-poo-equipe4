package com.serratec.domain.repository;

import com.serratec.domain.models.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository implements CRUDRepository<Produto> {
    PreparedStatement pInclusao = null;

    public ProdutoRepository() {
        prepararSqlInclusao();
    }

    @Override
    public void prepararSqlInclusao() {
        String sql = "insert into " + MainRepository.SCHEMA + ".produto";
        sql += " (descricao, idcategoria, estoque, vlcusto, vlvenda)";
        sql += " values ";
        sql += " (?, ?, ?, ?, ?)";

        try {
            pInclusao = MainRepository.CONEXAO.getC().prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incluir(Produto produto) {
        try {
            pInclusao.setString(1, produto.getDescricao());
            pInclusao.setInt(2, produto.getIdCategoria());
            pInclusao.setDouble(3, produto.getEstoque());
            pInclusao.setDouble(4, produto.getVlCusto());
            pInclusao.setDouble(5, produto.getVlVenda());

            pInclusao.executeUpdate();

        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("is null")) {
                System.err.println("\nProduto não incluído.\nVerifique se foi chamado o connect:\n" + e);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void alterar(Produto produto) {
        String sql = "update " +
                MainRepository.SCHEMA + ".produto set " +
                "descricao = '" + produto.getDescricao() + "'" +
                ", idcategoria = '" + produto.getIdCategoria() + "'" +
                ", estoque = '" + produto.getEstoque() + "'" +
                ", vlcusto = '" + produto.getVlCusto() + "' " +
                ", vlvenda = '" + produto.getVlVenda() + "' " +
                "where idproduto = " + produto.getIdProduto();
        MainRepository.CONEXAO.updateQuery(sql);
    }

    @Override
    public void apagarPorId(int idProduto) {
        String sql = "delete from " + MainRepository.SCHEMA + ".produto" +
                " where idproduto = " + idProduto;

        MainRepository.CONEXAO.updateQuery(sql);
    }

    @Override
    public Produto buscarPorId(int idProduto) {
        Produto produto = null;
        String sql = "SELECT * FROM " + MainRepository.SCHEMA + ".produto WHERE idproduto = " + idProduto;
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                produto = new Produto();

                produto.setIdProduto(tabela.getInt("idproduto"));
                produto.setIdCategoria(tabela.getInt("idcategoria"));
                produto.setDescricao(tabela.getString("descricao"));
                produto.setEstoque(tabela.getDouble("estoque"));
                produto.setVlCusto(tabela.getDouble("vlcusto"));
                produto.setVlVenda(tabela.getDouble("vlvenda"));
            }

            tabela.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }

    @Override
    public List<Produto> buscarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "select * from " + MainRepository.SCHEMA + ".produto order by idproduto";
        ResultSet tabela;

        tabela = MainRepository.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var produto = new Produto();

                produto.setIdProduto(tabela.getInt("idproduto"));
                produto.setIdCategoria(tabela.getInt("idcategoria"));
                produto.setDescricao(tabela.getString("descricao"));
                produto.setEstoque(tabela.getDouble("estoque"));
                produto.setVlCusto(tabela.getDouble("vlcusto"));
                produto.setVlVenda(tabela.getDouble("vlvenda"));

                produtos.add(produto);
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

        return produtos;
    }
}
