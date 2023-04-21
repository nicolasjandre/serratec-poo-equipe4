package com.serratec.domain.DAO;

import com.serratec.domain.models.Categoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    PreparedStatement pInclusao = null;

    public CategoriaDAO() {
        prepararSqlInclusao();
    }

    private void prepararSqlInclusao() {
        String sql = "insert into "+ CreateDAO.SCHEMA + ".categoria";
        sql += " (descricao)";
        sql += " values ";
        sql += " (?)";

        try {
            pInclusao =  CreateDAO.CONEXAO.getC().prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int incluirCategoria(Categoria categoria) {
        try {
            pInclusao.setString(1, categoria.getDescricao());

            return pInclusao.executeUpdate();
        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("is null")) {
                System.err.println("\nCategoria não incluída.\nVerifique se foi chamado o connect:\n" + e);
            } else {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public Categoria buscarPorId(int idCategoria) {
        var categoria = new Categoria();
        ResultSet tabela;

        String sql = "select * from " + CreateDAO.SCHEMA + ".categoria where idcategoria = " + idCategoria;

        tabela = CreateDAO.CONEXAO.query(sql);

        try {
            if (tabela.next()) {
                categoria.setIdCategoria(tabela.getInt("idcategoria"));
                categoria.setDescricao(tabela.getString("descricao"));
            } else
                System.out.println("Categoria com o ID: [" + idCategoria + "] não localizado.");

            tabela.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoria;
    }

    public List<Categoria> buscarTodos() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "select * from " + CreateDAO.SCHEMA + ".categoria order by idcategoria";
        ResultSet tabela;

        tabela = CreateDAO.CONEXAO.query(sql);

        try {
            while (tabela.next()) {
                var categoria = new Categoria();

                categoria.setIdCategoria(tabela.getInt("idcategoria"));
                categoria.setDescricao(tabela.getString("descricao"));

                categorias.add(categoria);
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

        return categorias;
    }
}
