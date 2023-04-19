package com.serratec.domain.services;

import com.serratec.domain.models.Produto;
import com.serratec.domain.repository.CategoriaRepository;
import com.serratec.domain.repository.ProdutoRepository;
import com.serratec.utils.Menu;

import java.util.List;

public class ProdutoService {
    public void menuProduto() {
        Menu.produtoInicial();
    }
    public void criarProdutosIniciais() {
        var produtoRepository = new ProdutoRepository();
        var categoriaRepository = new CategoriaRepository();
        List<Produto> produtos = produtoRepository.buscarTodos();
        var produto = new Produto();

        if (produtos.size() == 0) {
            produto.setDescricao("Tinta acrílica branca 18L");
            produto.setEstoque(20D);
            produto.setVlVenda(250.00);
            produto.setVlCusto(180.00);
            produto.setIdCategoria(1);
            produtoRepository.incluir(produto);

            produto.setDescricao("Porcelanato Bege 60x60cm");
            produto.setEstoque(50D);
            produto.setVlVenda(89.90);
            produto.setVlCusto(75.00);
            produto.setIdCategoria(2);
            produtoRepository.incluir(produto);

            produto.setDescricao("Argamassa AC III 20kg");
            produto.setEstoque(100D);
            produto.setVlVenda(24.90);
            produto.setVlCusto(18.50);
            produto.setIdCategoria(3);
            produtoRepository.incluir(produto);

            produto.setDescricao("Torneira para cozinha com filtro");
            produto.setEstoque(30D);
            produto.setVlVenda(189.90);
            produto.setVlCusto(120.00);
            produto.setIdCategoria(4);
            produtoRepository.incluir(produto);

            produto.setDescricao("Chuveiro elétrico 220v 7500W");
            produto.setEstoque(40D);
            produto.setVlVenda(74.90);
            produto.setVlCusto(50.00);
            produto.setIdCategoria(5);
            produtoRepository.incluir(produto);
        }
    }
}
