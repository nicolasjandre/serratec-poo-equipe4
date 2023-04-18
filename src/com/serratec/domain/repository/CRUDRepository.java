package com.serratec.domain.repository;

import java.util.List;

public interface CRUDRepository<T> {
    void prepararSqlInclusao();
    void incluir(T t);
    void alterar(T t);
    void apagarPorId(int id);
    T buscarPorId(int id);
    List<T> buscarTodos();
}
