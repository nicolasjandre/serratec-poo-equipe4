package com.serratec.domain.repository;

import com.serratec.domain.models.Categoria;
import com.serratec.domain.settings.Conexao;

import java.sql.PreparedStatement;

public class CategoriaRepository {
    private Conexao conexao;
    private String schema;
    PreparedStatement pInclusao = null;

    public CategoriaRepository(Conexao conexao, String schema) {
        this.conexao = conexao;
        this.schema = schema;
        prepararSqlInclusao();
    }

    private void prepararSqlInclusao() {
        String sql = "insert into "+ this.schema + ".categoria";
        sql += " (descricao)";
        sql += " values ";
        sql += " (?)";

        try {
            pInclusao =  conexao.getC().prepareStatement(sql);
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
                System.err.println(e);
                e.printStackTrace();
            }
            return 0;
        }
    }
}
