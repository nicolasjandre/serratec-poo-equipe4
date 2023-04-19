package com.serratec.domain.services;

import com.serratec.Main;
import com.serratec.domain.models.Categoria;
import com.serratec.domain.repository.CategoriaRepository;
import com.serratec.utils.Cor;

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

    public Categoria bucarPorId() {

            var categoriaRepository = new CategoriaRepository();
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

                categoria = categoriaRepository.buscarPorId(idCategoria);

                if (categoria.getDescricao() == null || categoria.getDescricao().isBlank()) continue;

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
