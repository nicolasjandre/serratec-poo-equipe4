package com.serratec.domain.services;

import com.serratec.domain.models.Categoria;
import com.serratec.domain.repository.CategoriaRepository;

import java.util.List;

public class CategoriaService {
    public void criarCategoriasIniciais() {
        var categoriaRepository = new CategoriaRepository();
        List<Categoria> categorias = categoriaRepository.buscarTodos();
        var categoria = new Categoria();

        if (categorias.size() == 0) {
            categoria.setDescricao("Tintas");
            categoriaRepository.incluirCategoria(categoria);

            categoria.setDescricao("Pisos e Revestimentos");
            categoriaRepository.incluirCategoria(categoria);

            categoria.setDescricao("Argamassas e Rejunte");
            categoriaRepository.incluirCategoria(categoria);

            categoria.setDescricao("Metais Sanitários");
            categoriaRepository.incluirCategoria(categoria);

            categoria.setDescricao("Aquecedores e Chuveiros");
            categoriaRepository.incluirCategoria(categoria);
        }
    }
}
