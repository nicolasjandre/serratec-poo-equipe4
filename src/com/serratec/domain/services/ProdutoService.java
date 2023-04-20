package com.serratec.domain.services;

import com.serratec.Main;
import com.serratec.domain.models.Produto;
import com.serratec.domain.repository.ProdutoRepository;
import com.serratec.utils.Cor;
import com.serratec.utils.Menu;
import com.serratec.utils.ResultadoBusca;
import com.serratec.utils.Util;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class ProdutoService implements CRUDService<Produto>{

    public void menuProduto() {
        String s;
        char opcao;
        boolean continua;

        do {
            continua = false;
            Menu.produtoInicial();

            s = Main.input.nextLine() + "R";
            opcao = s.charAt(0);

            switch (opcao) {
                case '1' -> cadastrar();
                case '2' -> alterar();
                case '3' -> apagar();
                case '4' -> imprimirTodosOsProdutos();
                case '0' -> {}
                default -> {
                    Cor.fontRed();
                    System.out.println("""
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                        Opção inválida, digite novamente""");
                    Cor.resetAll();
                    continua = true;
                }
            }
        } while (continua);
    }

    @Override
    public void cadastrar() {
        System.out.printf("%s %n%39s%n %s%n",
                "_ ".repeat(30), "CADASTRO DE PRODUTO", "_ ".repeat(30));

        var produto = new Produto();
        boolean continua;
        var produtoRepository = new ProdutoRepository();

        System.out.print("Digite a descrição do produto: ");
        String descricao = null;
        do {
            continua = false;
            try {
                descricao = Main.input.nextLine();

                if (descricao.isBlank()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("A descrição não pode estar vazia");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }

        } while (continua);

          var categoriaService = new CategoriaService();

          var categoria = categoriaService.bucarPorId();


        System.out.print("Digite a quantidade do produto para adicinar ao estoque: ");
        double estoque = 0.0;
        do {
            continua = false;
            try {
                estoque = Main.input.nextDouble();

                if (estoque < 0 || estoque == 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("A quantidade nao pode ser 0 ou menor");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite valor de custo do produto: ");
        Double vlcusto = 0.0;

        do {
            continua = false;
            try {
                vlcusto = Main.input.nextDouble();

                if (vlcusto < 0 || vlcusto == 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("o valor do produto não pode ser negativo ou 0");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite o valor do produto para venda: ");
        Double vlVenda = 0.0;

        do {
            continua = false;
            try {
                vlVenda = Main.input.nextDouble();

                if (vlVenda < 0 || vlVenda == 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("o valor do produto não pode ser negativo ou 0");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);


        produto.setDescricao(descricao);
        produto.setEstoque(estoque);
        produto.setVlCusto(vlcusto);
        produto.setVlVenda(vlVenda);
        produto.setIdCategoria(categoria.getIdCategoria());


        try {
            produtoRepository.incluir(produto);
            Cor.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                      PRODUTO CADASTRADO COM SUCESSO
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Cor.resetAll();
            Util.voltarAoMenu();
        } catch (Exception e) {
            Cor.fontRed();
            System.out.print("Houve um erro ao produto o cliente.");
            Cor.resetAll();
            Util.voltarAoMenu();
        }
    }

    @Override
    public void apagar() {

        int idProduto = 0;
        boolean continua;
        var produtoRepository = new ProdutoRepository();

        System.out.print("Digite o código do produto: ");

        do {
            continua = false;
            try {
                idProduto = Main.input.nextInt();

                Produto produto = produtoRepository.buscarPorId(idProduto);

                if (produto.getDescricao().isEmpty()) {
                    throw new NullPointerException();
                }

            } catch (InputMismatchException | NullPointerException e) {
                if (e instanceof InputMismatchException) {
                    System.out.print("Valor inválido, digite um número inteiro");
                    continua = true;
                    Main.input.nextLine();
                } else {
                    Util.voltarAoMenu();
                }
            }
        } while (continua);

        produtoRepository.apagarPorId(idProduto);
        Main.input.nextLine();
    }

    @Override
    public void alterar() {

        var produto = new Produto();
        boolean continua;
        var produtoRepository = new ProdutoRepository();

       produto = buscarPorId();

        System.out.print("Digite a nova descrição: ");
        String descricao = null;
        do {
            continua = false;
            try {
                descricao = Main.input.nextLine();

                if (descricao.isBlank()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("A descrição não pode estar vazia");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        var  categoriaService = new CategoriaService();

        var categoria = categoriaService.bucarPorId();

        System.out.print("Digite a quantidade do produto para adicinar ao estoque: ");
        double estoque = 0.0;
        do {
            continua = false;
            try {
                estoque = Main.input.nextDouble();

                if (estoque < 0 || estoque == 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("A quantidade nao pode ser 0 ou menor");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite valor de custo do produto: ");
        Double vlcusto = 0.0;

        do {
            continua = false;
            try {
                vlcusto = Main.input.nextDouble();

                if (vlcusto < 0 || vlcusto == 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("o valor do produto não pode ser negativo ou 0");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite o valor do produto para venda: ");
        Double vlVenda = 0.0;

        do {
            continua = false;
            try {
                vlVenda = Main.input.nextDouble();

                if (vlVenda < 0 || vlVenda == 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("o valor do produto não pode ser negativo ou 0");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        produto.setDescricao(descricao);
        produto.setEstoque(estoque);
        produto.setVlCusto(vlcusto);
        produto.setVlVenda(vlVenda);
        produto.setIdCategoria(categoria.getIdCategoria());

        try {
            produtoRepository.alterar(produto);
            Cor.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                      PRODUTO ALTERADO COM SUCESSO
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Cor.resetAll();
            Util.voltarAoMenu();
        } catch (Exception e) {
            Cor.fontRed();
            System.out.print("Houve um erro ao alterar dados do produto.");
            Cor.resetAll();
            Util.voltarAoMenu();
        }

    }

    @Override
    public List<Produto> buscarTodos() {
        var produtoRepository = new ProdutoRepository();
        return produtoRepository.buscarTodos();
    }

    public void imprimirTodosOsProdutos() {
        List<Produto> produtos = buscarTodos();

        if (produtos.size() != 0) {
            Cor.fontGreen();
            Util.imprimirLinha();
            Cor.resetAll();
            Util.imprimirCabecalhoProduto();

            for (Produto produto : produtos) {
                produto.imprimirDadosProduto();
            }
            Cor.fontGreen();
            Util.imprimirLinha();
            Cor.resetAll();
        } else {
            Util.imprimirLinha();
            Cor.fontRed();
            System.out.printf("%96s%n", "NENHUM PRODUTO ENCONTRADO");
            Cor.resetAll();
            Util.imprimirLinha();
        }
        System.out.print("Pressione qualquer tecla para voltar ao menu");
        Main.input.nextLine();
    }

    public void criarProdutosIniciais() {
        var produtoRepository = new ProdutoRepository();
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

    public Produto buscarPorId() {

        var produtoRepository = new ProdutoRepository();
        int idProduto = 0;
        char opcao = 'R';
        boolean continua;
        var produto = new Produto();

        do {
            System.out.print("Digite o código da categoria (número inteiro): ");

            do {
                continua = false;
                try {
                    idProduto = Main.input.nextInt();

                } catch (Exception e) {
                    Cor.fontRed();
                    System.out.println("Insira um número inteiro.");
                    Cor.resetAll();
                    System.out.print("Digite novamente: ");
                    continua = true;
                }
            } while (continua);

            Main.input.nextLine();

            produto = produtoRepository.buscarPorId(idProduto);

            if (produto.getDescricao() == null || produto.getDescricao().isBlank()) continue;

            System.out.println("Deseja escolher o produto " + produto.getDescricao() + "? S/N");

            do {
                String op = Main.input.nextLine().toUpperCase() + "R";
                opcao = op.charAt(0);

                switch (opcao) {
                    case 'S' -> {
                        return produto;
                    }
                    case 'N' -> {}
                    default -> System.out.print("Opção inválida, digite novamente: ");
                }
            } while (opcao != 'N');

        } while (opcao != 'N');

        return produto;

    }

    public ResultadoBusca buscarProdutosPorIdParaIncluirNoPedido() {
        ProdutoRepository produtoRepository = new ProdutoRepository();
        List<Produto> produtos = new ArrayList<>();
        List<Double> qtdVendidaProduto = new ArrayList<>();
        var produto = new Produto();
        boolean continua;
        char opcao;
        Double qtdVendida = 0.0;
        int idProduto = 0;

        do {
            continua = true;

            if (produtos.size() > 0) {
                ArrayList<Produto> jaImpresso = new ArrayList<>();
                boolean duplicado;

                Util.imprimirLinha();
                Util.imprimirCabecalhoProdutoComQtdVendida();

                for (Produto prod : produtos) {
                    duplicado = false;

                    for (Produto impresso : jaImpresso) {
                        if (impresso.getIdProduto() == prod.getIdProduto()) {
                            duplicado = true;
                            break;
                        }
                    }
                    if (!duplicado) {
                        jaImpresso.add(prod);
                        int index = produtos.indexOf(prod);
                        produto.imprimirDadosProdutoComQtdVendida(qtdVendidaProduto.get(index));
                    }
                }
                Util.imprimirLinha();
            }

            do {
                if (produtos.size() > 0) {
                    System.out.println("Digite o código do próximo produto: ");
                } else {
                    System.out.print("Digite o código do produto: ");
                }
                try {
                    idProduto = Main.input.nextInt();

                    if (idProduto <= 0) throw new Exception();
                } catch (Exception e) {
                    Cor.fontRed();
                    System.out.println("O código precisa ser um número inteiro maior que 0");
                    Cor.resetAll();
                    System.out.print("Digite novamente: ");
                    Main.input.nextLine();
                }
            } while (idProduto <= 0);
            Main.input.nextLine();

            produto = produtoRepository.buscarPorId(idProduto);

            if (produto.getDescricao() == null || produto.getDescricao().isBlank()) continue;

            System.out.print("Quantos " + produto.getDescricao() + " deseja inserir no pedido?");
            do {
                try {
                    qtdVendida = Main.input.nextDouble();

                    if (qtdVendida <= 0) throw new Exception();
                } catch (Exception e) {
                    Cor.fontRed();
                    System.out.println("A quantidade precisa ser um número inteiro maior que 0");
                    Cor.resetAll();
                    System.out.print("Digite novamente: ");
                    Main.input.nextLine();
                }
            } while (qtdVendida <= 0);
            Main.input.nextLine();

            int quant = 0;
            int index;
            for (Produto prod : produtos) {
                if (prod.getIdProduto() == produto.getIdProduto()) {
                    index = produtos.indexOf(prod);
                    quant += qtdVendidaProduto.get(index);
                }
            }
            if (quant >= 1) {
                System.out.println("Seu pedido já possui " + quant + " " + produto.getDescricao());
                System.out.print("Deseja inserir " + qtdVendida + "? S/N");
            } else {
                System.out.print("Deseja escolher o produto " + produto.getDescricao() + "? S/N");
            }

            do {
                String op = Main.input.nextLine().toUpperCase() + "R";
                opcao = op.charAt(0);

                switch (opcao) {
                    case 'S' -> {
                        boolean duplicado = false;

                        for (Produto prod : produtos) {
                            if (prod.getIdProduto() == produto.getIdProduto()) {
                                index = produtos.indexOf(prod);
                                qtdVendida += qtdVendidaProduto.get(index);
                                qtdVendidaProduto.set(index, qtdVendida);
                                duplicado = true;
                            }
                        }

                        if (!duplicado) {
                            produtos.add(produto);
                            qtdVendidaProduto.add(qtdVendida);
                        }

                        System.out.println("Produto " + produto.getDescricao() + " adicionado!");
                    }
                    case 'N' -> {}
                    default -> System.out.print("Opção inválida, digite novamente: ");
                }
            } while (opcao != 'S' && opcao != 'N');

            if (produtos.size() > 0) {
                System.out.print("Deseja inserir mais algum produto? S/N");

                do {
                    String op = Main.input.nextLine().toUpperCase() + "R";
                    opcao = op.charAt(0);

                    switch (opcao) {
                        case 'S' -> {}
                        case 'N' -> continua = false;
                        default -> System.out.print("Opção inválida, digite novamente: ");
                    }
                } while (opcao != 'N' && opcao != 'S');
            }

        } while (continua);

        for (Produto prod : produtos) {
            int index = produtos.indexOf(prod);
            Double estoqueqAtual = prod.getEstoque();
            Double qtdVend = qtdVendidaProduto.get(index);
            prod.setEstoque(estoqueqAtual - qtdVend);
        }

        return new ResultadoBusca(produtos, qtdVendidaProduto);
    }
    public void atualizarEstoque(List<Produto> produtos) {
        var produtoRepository = new ProdutoRepository();

        for (var produto : produtos) {
            produtoRepository.atualizarEstoque(produto);
        }
    }
}
