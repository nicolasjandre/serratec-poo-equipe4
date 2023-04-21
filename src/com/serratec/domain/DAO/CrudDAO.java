package com.serratec.domain.DAO;

import java.util.List;

public interface CrudDAO<T> {
    void prepararSqlInclusao();
    void incluir(T t);
    void alterar(T t);
    void apagarPorId(int id);
    T buscarPorId(int id);
    List<T> buscarTodos();
}
