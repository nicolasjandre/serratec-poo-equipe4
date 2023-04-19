package com.serratec.domain.services;

import java.util.List;

public interface CRUDService<T> {
    void cadastrar();
    void apagar();
    void alterar();
    List<T> buscarTodos();
}
