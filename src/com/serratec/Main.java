package com.serratec;

import com.serratec.domain.repository.MainRepository;
import com.serratec.domain.services.CategoriaService;
import com.serratec.domain.services.ClienteService;
import com.serratec.domain.services.PedidoService;
import com.serratec.domain.services.ProdutoService;
import com.serratec.utils.Menu;

import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        MainRepository.iniciarConexaoComBanco();

        var clienteService = new ClienteService();
        var produtoService = new ProdutoService();
        var pedidoService = new PedidoService();
        var categoriaService = new CategoriaService();
        clienteService.criarClientesIniciais();
        categoriaService.criarCategoriasIniciais();
        produtoService.criarProdutosIniciais();

        String op;
        char opcao;

        do {
            Menu.inicial();
            op = input.nextLine() + "R";
            opcao = op.charAt(0);

            switch (opcao) {
                case '1' -> clienteService.menuCliente();
                case '2' -> produtoService.menuProduto();
                case '3' -> pedidoService.menuPedido();
                case '0' -> System.out.println("Obrigado por usar o Serratec Construções!");
                default -> System.out.println("""
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                        Opção inválida, digite novamente""");
            }
        } while (opcao != '0');
    }
}