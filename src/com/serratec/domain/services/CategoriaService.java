package com.serratec.domain.services;

import com.serratec.Main;
import com.serratec.domain.models.Categoria;
import com.serratec.domain.DAO.CategoriaDAO;
import com.serratec.utils.Cor;

import java.util.List;

public class CategoriaService {
    public void criarCategoriasIniciais() {
        var categoriaDAO = new CategoriaDAO();
        List<Categoria> categorias = categoriaDAO.buscarTodos();
        var categoria = new Categoria();

        if (categorias.size() == 0) {
            categoria.setDescricao("Tintas");
            categoriaDAO.incluirCategoria(categoria);

            categoria.setDescricao("Pisos e Revestimentos");
            categoriaDAO.incluirCategoria(categoria);

            categoria.setDescricao("Argamassas e Rejunte");
            categoriaDAO.incluirCategoria(categoria);

            categoria.setDescricao("Metais Sanitários");
            categoriaDAO.incluirCategoria(categoria);

            categoria.setDescricao("Aquecedores e Chuveiros");
            categoriaDAO.incluirCategoria(categoria);
        }
    }

    public Categoria bucarPorId() {

            var categoriaDAO = new CategoriaDAO();
            int idCategoria = 0;
            char opcao = 'R';
            boolean continua;
            var categoria = new Categoria();

            do {
                System.out.print("Digite o código da categoria (número inteiro): ");

                do {
                    continua = false;
                    try {
                        idCategoria = Main.input.nextInt();

                    } catch (Exception e) {
                        Cor.fontRed();
                        System.out.println("Insira um número inteiro.");
                        Cor.resetAll();
                        System.out.print("Digite novamente: ");
                        continua = true;
                    }
                } while (continua);

                Main.input.nextLine();

                categoria = categoriaDAO.buscarPorId(idCategoria);

                if (categoria == null || categoria.getDescricao() == null || categoria.getDescricao().isBlank()) continue;

                System.out.println("Deseja escolher a categoria " + categoria.getDescricao() + "? S/N");

                do {
                    String op = Main.input.nextLine().toUpperCase() + "R";
                    opcao = op.charAt(0);

                    switch (opcao) {
                        case 'S' -> {
                            return categoria;
                        }
                        case 'N' -> {}
                        default -> System.out.print("Opção inválida, digite novamente: ");
                    }
                } while (opcao != 'N');

            } while (opcao != 'N');

            return categoria;

    }
}
