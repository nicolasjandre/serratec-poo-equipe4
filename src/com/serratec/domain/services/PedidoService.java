package com.serratec.domain.services;

import com.serratec.Main;
import com.serratec.domain.models.Cliente;
import com.serratec.domain.models.PedItem;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.models.Produto;
import com.serratec.domain.DAO.ClienteDAO;
import com.serratec.domain.DAO.PedidoDAO;
import com.serratec.domain.DAO.ProdutoDAO;
import com.serratec.utils.Cor;
import com.serratec.utils.Menu;
import com.serratec.utils.ResultadoBusca;
import com.serratec.utils.Util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

public class PedidoService implements CRUDService<Pedido> {
    public void menuPedido() {
        String s;
        char opcao;
        boolean continua;

        do {
            continua = false;
            Menu.pedidoInicial();

            s = Main.input.nextLine() + "R";
            opcao = s.charAt(0);

            switch (opcao) {
                case '1' -> cadastrar();
                case '2' -> alterar();
                case '3' -> apagar();
                case '4' -> imprimirPedidosPorCliente();
                case '5' -> imprimirPedidosPorPeriodo();
                case '6' -> imprimirTodosOsPedidos();
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
        var pedidoDAO = new PedidoDAO();
        var pedItemService = new PedItemService();
        var produtoService= new ProdutoService();
        Double desconto = 0.0;

        System.out.printf("%s %n%39s%n %s%n",
                "_ ".repeat(30), "CADASTRO DE PEDIDO", "_ ".repeat(30));

        System.out.print("Quantos % de desconto deseja incluir no pedido?");
        do {
            try {
                desconto = Main.input.nextDouble();

                if (desconto < 0 || desconto > 25) throw new Exception();
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O desconto não pode ser maior que 25% ou menor que 0");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                Main.input.nextLine();
            }
        } while (desconto < 0);
        Main.input.nextLine();

        ResultadoBusca resultadoBusca = pedirDadosParaCriarPedido(desconto);
        Pedido pedido = resultadoBusca.getPedido();
        List<Double> qtdVendida = resultadoBusca.getQtdVendida();

        try {
            pedidoDAO.incluir(pedido);
            pedItemService.criarPedItemAposPedido(pedido, desconto, qtdVendida);
            produtoService.atualizarEstoque(pedido.getProdutos());
            Cor.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                        PEDIDO SALVO COM SUCESSO
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Cor.resetAll();
            Util.voltarAoMenu();
        } catch (Exception e) {
            Cor.fontRed();
            System.out.print("Houve um erro ao criar o pedido.");
            Cor.resetAll();
            Util.voltarAoMenu();
        }
    }

    public ResultadoBusca pedirDadosParaCriarPedido(Double desconto) {
        var clienteService = new ClienteService();
        var produtoService = new ProdutoService();
        var cliente = new Cliente();
        var pedido = new Pedido();
        boolean continua;
        char opcao;

        System.out.println("""
                Inclua o cliente:
                1) Por código
                2) Por nome
                3) Por CPF""");

        do {
            continua = false;
            String op = Main.input.nextLine() + "R";
            opcao = op.charAt(0);

            switch (opcao) {
                case '1' -> cliente = clienteService.buscarClientesPeloId();
                case '2' -> cliente = clienteService.buscarClientePeloNome();
                case '3' -> cliente = clienteService.buscarClientePorCpf();
                default -> {
                    System.out.print("Opção inválida, digite novamente.");
                    continua = true;
                }
            }
        } while (continua);
        System.out.println("Cliente " + cliente.getNome() + " adicionado ao pedido.");

        System.out.println("Insira os produtos do seu pedido");
        ResultadoBusca resultadoBusca = produtoService.buscarProdutosPorIdParaIncluirNoPedido();
        List<Produto> produtos = resultadoBusca.getProdutos();
        List<Double> qtdVendida = resultadoBusca.getQtdVendida();

        Double valorTotal;
        Double valorBruto = 0.0;
        for (Produto produto : produtos) {
            int index = produtos.indexOf(produto);
            valorBruto += qtdVendida.get(index) * produto.getVlVenda();
        }
        valorTotal = valorBruto * (1 - desconto / 100);

        Util.imprimirLinha();
        Util.imprimirCabecalhoProdutoComQtdVendida();
        for (Produto produto : produtos) {
            int index = produtos.indexOf(produto);
            produto.imprimirDadosProdutoComQtdVendida(qtdVendida.get(index));
        }
        Util.imprimirLinha();

        System.out.print("Digite a data de emissão (dd/MM/yyyy): ");
        Date dtEmissao = Util.pedirData();

        System.out.print("Digite a previsão de entrega (dd/MM/yyyy): ");
        Date dtEntrega = Util.pedirData();

        System.out.println("Escreva a observação do pedido: ");
        String observacao = Main.input.nextLine();

        pedido.setCliente(cliente);
        pedido.setObervacao(observacao);
        pedido.setDtEmissao(dtEmissao);
        pedido.setDtEntrega(dtEntrega);
        pedido.setProdutos(produtos);
        pedido.setValorBruto(valorBruto);
        pedido.setValorTotal(valorTotal);

        return new ResultadoBusca(qtdVendida, pedido);
    }

    @Override
    public void apagar() {
        var pedItemService = new PedItemService();
        var pedidoDAO = new PedidoDAO();
        Pedido pedido = new Pedido();
        int idPedido = 0;
        boolean continua;

        System.out.print("Digite o código do pedido: ");

        do {
            continua = false;
            try {
                idPedido = Main.input.nextInt();

                pedido = pedidoDAO.buscarPorId(idPedido);

                if (pedido.getCliente().getNome() == null || pedido.getCliente().getNome().isEmpty()) {
                    throw new NullPointerException();
                }

            } catch (InputMismatchException | NullPointerException e) {
                if (e instanceof InputMismatchException) {
                    System.out.print("Valor inválido, digite um número inteiro");
                    continua = true;
                    Main.input.nextLine();
                } else {
                    Main.input.nextLine();
                    Cor.fontRed();
                    System.out.println("Nenhum pedido com este código");
                    Cor.resetAll();

                    char opcao;
                    System.out.print("Deseja procurar por outro código? S para sim ou qualquer outro digito para não");
                    String string = Main.input.nextLine().toUpperCase() + "R";
                    opcao = string.charAt(0);

                    switch (opcao) {
                        case 'S' -> continua = true;
                        default -> {
                            return;
                        }
                    }
                }
            }
        } while (continua);

        pedItemService.apagarPorPedido(pedido);
        pedidoDAO.apagarPorId(idPedido);
        Main.input.nextLine();
    }

    @Override
    public void alterar() {
        Pedido pedido = new Pedido();
        boolean continua;

        do {
            Menu.alterarPedido(pedido);
            continua = true;

            String s = Main.input.nextLine() + "R";
            char opcao = s.charAt(0);

            if (pedido.getCliente() != null) {
                switch (opcao) {
                    case '1' -> pedido = alterarClientePedido(pedido);
                    case '2' -> pedido = alterarProdutosPedido(pedido);
                    case '3' -> pedido = alterarObservacao(pedido);
                    case '4' -> pedido = alterarDataEmissaoPedido(pedido);
                    case '5' -> pedido = alterarDataEntregaPedido(pedido);
                    case '6' -> imprimirPedidoComProdutos(pedido);
                    case '7' -> salvarAlteracaoPedido(pedido);
                    case '0' -> continua = false;
                    default -> {
                        Cor.fontRed();
                        System.out.println("""
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                        Opção inválida, digite novamente""");
                        Cor.resetAll();
                    }
                }
            } else {
                switch (opcao) {
                    case '1' -> pedido = buscarPedidoPorId();
                    case '0' -> continua = false;
                    default -> {
                        Cor.fontRed();
                        System.out.println("""
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                        Opção inválida, digite novamente""");
                        Cor.resetAll();
                    }
                }
            }
        } while (continua);

    }

    public Pedido buscarPedidoPorId() {
        var pedidoDAO = new PedidoDAO();
        var pedItemService = new PedItemService();
        var pedido = new Pedido();
        char opcao;
        int idPedido = 0;

        do {
            System.out.print("Digite o código do pedido: ");

            do {
                try {
                    idPedido = Main.input.nextInt();

                    if (idPedido <= 0) throw new Exception();
                } catch (Exception e) {
                    Cor.fontRed();
                    System.out.println("O código precisa ser um número inteiro maior que 0");
                    Cor.resetAll();
                    System.out.print("Digite novamente: ");
                    Main.input.nextLine();
                }
            } while (idPedido <= 0);
            Main.input.nextLine();

            pedido = pedidoDAO.buscarPorId(idPedido);

            if (pedido == null || pedido.getCliente() == null || pedido.getCliente().getNome().isBlank()) {
                opcao = 'N';
                continue;
            }

            System.out.println("Deseja escolher o pedido Nº" + pedido.getIdPedido() + "? S/N");

            do {
                String op = Main.input.nextLine().toUpperCase() + "R";
                opcao = op.charAt(0);

                switch (opcao) {
                    case 'S' -> {
                        List<Produto> produtos = new ArrayList<>();
                        List<PedItem> pedItems = pedItemService.buscarPedItemsPorIdPedido(pedido.getIdPedido());

                        for (PedItem pedItem : pedItems) {
                            produtos.add(pedItem.getProduto());
                        }

                        pedido.setProdutos(produtos);
                        pedido.setPedItems(pedItems);

                        return pedido;
                    }
                    case 'N' -> {}
                    default -> System.out.print("Opção inválida, digite novamente: ");
                }
            } while (opcao != 'N');

        } while (opcao == 'N');

        return pedido;
    }

    public Pedido alterarClientePedido(Pedido pedido) {
        var clienteService = new ClienteService();
        var cliente = new Cliente();
        boolean continua;
        char opcao;

        System.out.println("""
                Você deseja:
                1) Adicionar/sobrepor um cliente
                2) Excluir cliente
                0) Voltar""");

        do {
            continua = false;
            String op = Main.input.nextLine() + "R";
            opcao = op.charAt(0);

            switch (opcao) {
                case '1' -> {}
                case '2' -> {
                    pedido.setCliente(new Cliente());
                    return pedido;
                }
                case '0' -> {
                    return pedido;
                }
                default -> {
                    System.out.print("Opção inválida, digite novamente.");
                    continua = true;
                }
            }
        } while (continua);

        System.out.println("""
                Inclua o novo cliente:
                1) Por código
                2) Por nome
                3) Por CPF""");

        do {
            continua = false;
            String op = Main.input.nextLine() + "R";
            opcao = op.charAt(0);

            switch (opcao) {
                case '1' -> cliente = clienteService.buscarClientesPeloId();
                case '2' -> cliente = clienteService.buscarClientePeloNome();
                case '3' -> cliente = clienteService.buscarClientePorCpf();
                default -> {
                    System.out.print("Opção inválida, digite novamente.");
                    continua = true;
                }
            }
        } while (continua);

        System.out.println("Cliente alterado para " + cliente.getNome() + ".");

        pedido.setCliente(cliente);
        return pedido;
    }

    public Pedido alterarProdutosPedido(Pedido pedido) {
        boolean continua;
        char opcao;

        do {
            Menu.alterarProdutosPedido();
            continua = true;
            String op = Main.input.nextLine() + "R";
            opcao = op.charAt(0);


            switch (opcao) {
                case '1' -> {
                    return adicionarProdutoNoPedido(pedido);
                }
                case '2' -> {
                    return trocarUmProdutoPorOutro(pedido);
                }
                case '3' -> {
                    return removerUmProdutoDoPedido(pedido);
                }
                case '4' -> {
                    return removerTodosOsProdutos(pedido);
                }
                case '5' -> {
                    return alterarCamposDeUmProdutoDoPedido(pedido);
                }
                case '6' -> imprimirPedidoComProdutos(pedido);
                case '0' -> {
                    return pedido;
                }
                default -> System.out.print("Opção inválida, digite novamente.");

            }
        } while (continua);

        return pedido;
    }

    public Pedido alterarCamposDeUmProdutoDoPedido(Pedido pedido) {
        var produtoDAO = new ProdutoDAO();
        var produto = new Produto();
        int codProduto;
        char opcao;
        int index = 0;

        System.out.print("Digite o código do produto que deseja alterar: ");
        do {
            int cont = 0;
            try {
                codProduto = Main.input.nextInt();

                if (codProduto <= 0) throw new Exception();

                produto = produtoDAO.buscarPorId(codProduto);

                if (produto.getDescricao() == null || produto.getDescricao().isBlank()) throw new Exception();

                for (Produto prodPed : pedido.getProdutos()) {

                    if (produto.getIdProduto() == prodPed.getIdProduto()) {
                        index = pedido.getProdutos().indexOf(prodPed);
                        cont++;
                    }

                }
                if (cont == 0) throw new Exception();

            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O código precisa ser um número inteiro maior que 0 e precisa estar no pedido");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                codProduto = 0;
                Main.input.nextLine();
            }
        } while (codProduto <= 0);
        Main.input.nextLine();

        System.out.print("Deseja alterar " + produto.getDescricao() + "? S/N");
        do {
            String op = Main.input.nextLine().toUpperCase() + "R";
            opcao = op.charAt(0);

            switch (opcao) {
                case 'S' -> opcao = 'N';
                case 'N' -> {
                    return pedido;
                }
                default -> System.out.print("Opção inválida, digite novamente: ");
            }
        } while (opcao != 'N');

        for (PedItem pedItem : pedido.getPedItems()) {
            if (pedItem.getProduto().getIdProduto() == pedido.getProdutos().get(index).getIdProduto()) {

                System.out.print("Digite o novo valor unitário: ");
                pedItem.setVlUnitario(Util.pedirDoubleMaiorQueZero(
                        "O valor unitario precisa ser maior ou igual a zero", 0, -1));

                System.out.print("Digite o novo desconto (0 a 100%): ");
                pedItem.setVlDesconto(Util.pedirDoubleMaiorQueZero(
                        "O desconto deve estar entre 0 e 100", 0, 100));

                System.out.print("Digite o nova quantidade deste produto no pedido: ");
                pedItem.setQuantidade(Util.pedirDoubleMaiorQueZero(
                        "A quantidade precisa ser maior ou igual a 0", 0, -1));
            }
        }

        atualizarValoresPedido(pedido);
        return pedido;
    }

    public Pedido removerTodosOsProdutos(Pedido pedido) {
        char opcao;

        Cor.fontRed();
        System.out.println("ATENÇÃO!!!");
        Cor.resetAll();

        System.out.print("Tem certeza que deseja remover todos os produtos? S/N");
        do {
            String op = Main.input.nextLine().toUpperCase() + "R";
            opcao = op.charAt(0);

            switch (opcao) {
                case 'S' -> opcao = 'N';
                case 'N' -> {
                    return pedido;
                }
                default -> System.out.print("Opção inválida, digite novamente: ");
            }
        } while (opcao != 'N');

        for (Iterator<PedItem> iterator = pedido.getPedItems().iterator(); iterator.hasNext();) {
            PedItem pedItem = iterator.next();
            iterator.remove();
        }

        for (Iterator<Produto> iterator = pedido.getProdutos().iterator(); iterator.hasNext();) {
            Produto produto = iterator.next();
            iterator.remove();
        }

        pedido = atualizarValoresPedido(pedido);
        return pedido;
    }

    public Pedido trocarUmProdutoPorOutro(Pedido pedido) {
        var produtoDAO = new ProdutoDAO();
        List<Produto> produtos =  produtoDAO.buscarTodos();

        if (produtos.size() == pedido.getProdutos().size()) {
            Cor.fontRed();
            System.out.printf("%nEste pedido contém todos os produtos possíveis%n");
            Cor.resetAll();
            return pedido;
        }

        var produto = new Produto();
        var novoProduto = new Produto();
        Double qtdProduto = 1.0;
        int codProduto = 0;
        int index = 0;
        char opcao;

        imprimirPedidoComProdutos(pedido);

        System.out.print("Digite o código do produto que deseja trocar: ");
        do {
            int cont = 0;
            try {
                codProduto = Main.input.nextInt();

                if (codProduto <= 0) throw new Exception();

                produto = produtoDAO.buscarPorId(codProduto);

                if (produto.getDescricao() == null || produto.getDescricao().isBlank()) throw new Exception();

                for (Produto prodPed : pedido.getProdutos()) {

                    if (produto.getIdProduto() == prodPed.getIdProduto()) {
                        index = pedido.getProdutos().indexOf(prodPed);
                        cont++;
                    }

                }
                if (cont == 0) throw new Exception();

            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O código precisa ser um número inteiro maior que 0 e precisa estar no pedido");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                Main.input.nextLine();
            }
        } while (codProduto <= 0);
        Main.input.nextLine();

        do {
            System.out.print("Digite o código do novo produto: ");
            try {
                codProduto = Main.input.nextInt();

                if (codProduto <= 0) throw new Exception();

                novoProduto = produtoDAO.buscarPorId(codProduto);

                if (produto.getDescricao() == null || produto.getDescricao().isBlank()) throw new Exception();

                if (produto.getIdProduto() == novoProduto.getIdProduto()) {
                    Cor.fontRed();
                    System.out.println("Você não pode trocar o produto pelo mesmo produto.");
                    Cor.resetAll();
                    codProduto = 0;
                    continue;
                }

                boolean flag = false;
                for (Produto prodPed : pedido.getProdutos()) {
                    if (novoProduto.getIdProduto() == prodPed.getIdProduto()) {
                        Cor.fontRed();
                        System.out.println("Você não pode trocar por um produto que já consta em seu pedido.");
                        Cor.resetAll();
                        codProduto = 0;
                        flag = true;
                    }
                }
                if (flag) continue;

                System.out.print("Quantos " + novoProduto.getDescricao() + " deseja inserir no pedido?");
                do {
                    try {
                        qtdProduto = Main.input.nextDouble();

                        if (qtdProduto <= 0) throw new Exception();
                    } catch (Exception e) {
                        Cor.fontRed();
                        System.out.println("A quantidade de produtos deve ser maior que 0.");
                        Cor.resetAll();
                        System.out.print("Digite novamente: ");
                        Main.input.nextLine();
                    }
                } while (qtdProduto <= 0);
                Main.input.nextLine();

                System.out.print("Deseja trocar " + produto.getDescricao() + " por " + novoProduto.getDescricao() + "? S/N");
                do {
                    String op = Main.input.nextLine().toUpperCase() + "R";
                    opcao = op.charAt(0);

                    switch (opcao) {
                        case 'S' -> opcao = 'N';
                        case 'N' -> {
                            return pedido;
                        }
                        default -> System.out.print("Opção inválida, digite novamente: ");
                    }
                } while (opcao != 'N');

            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O código precisa ser um número inteiro maior que 0");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                Main.input.nextLine();
                codProduto = 0;
            }
        } while (codProduto <= 0);

        pedido.getProdutos().set(index, novoProduto);

        for (PedItem pedItem : pedido.getPedItems()) {
            if (pedItem.getProduto().getIdProduto() == produto.getIdProduto()) {
                pedItem.setProduto(novoProduto);
                pedItem.setVlUnitario(novoProduto.getVlVenda());
                pedItem.setVlDesconto(0.0);
                pedItem.setQuantidade(qtdProduto);
            }
        }

        pedido = atualizarValoresPedido(pedido);
        return pedido;
    }

    public Pedido adicionarProdutoNoPedido(Pedido pedido) {
        var produtoService = new ProdutoService();

        System.out.println("Insira mais produtos do seu pedido");
        ResultadoBusca resultadoBusca = produtoService.buscarProdutosPorIdParaIncluirNoPedido();
        List<Produto> produtos = resultadoBusca.getProdutos();
        List<Double> qtdVendida = resultadoBusca.getQtdVendida();

        for (Produto produto : produtos) {
            int pedItemCount = 0;
            boolean jaExisteNaLista = false;
            int index = produtos.indexOf(produto);

            for (PedItem pedItem : pedido.getPedItems()) {
               if (pedItem.getProduto().getIdProduto() == produto.getIdProduto()) {
                   pedItemCount++;
               }
            }

            if (pedItemCount == 0) {
                var pedItem = new PedItem();
                pedItem.setPedido(pedido);
                pedItem.setProduto(produto);
                pedItem.setQuantidade(qtdVendida.get(index));
                pedItem.setVlUnitario(produto.getVlVenda());
                pedItem.setVlDesconto(0.0);
                pedido.getPedItems().add(pedItem);
            }

            for (Produto produtoPed : pedido.getProdutos()) {
                if (produto.getIdProduto() == produtoPed.getIdProduto()) {
                    jaExisteNaLista = true;
                }
            }

            if (!jaExisteNaLista) {
                pedido.getProdutos().add(produto);
            } else {
                Cor.fontRed();
                System.out.println("O produto " + produto.getDescricao() + "já estava na lista.");
                Cor.resetAll();
            }
        }

        pedido = atualizarValoresPedido(pedido);

        return pedido;
    }

    public Pedido atualizarValoresPedido(Pedido pedido) {
        Double valorTotal = 0.0;
        Double valorBruto = 0.0;
        for (Produto prodPed : pedido.getProdutos()) {
            for (PedItem pedItem : pedido.getPedItems()) {
                if (pedItem.getProduto().getIdProduto() == prodPed.getIdProduto()) {
                    valorBruto += pedItem.getQuantidade() * pedItem.getVlUnitario();
                    valorTotal += (pedItem.getQuantidade() * pedItem.getVlUnitario())
                            * ( 1 - (pedItem.getVlDesconto() != 0 ? (pedItem.getVlDesconto() / 100) : 0) );
                }
            }
        }

        pedido.setValorTotal(valorTotal);
        pedido.setValorBruto(valorBruto);
        return pedido;
    }

    public Pedido alterarDataEmissaoPedido(Pedido pedido) {
        System.out.print("Digite a data de emissão (dd/MM/yyyy): ");
        Date dtEmissao = Util.pedirData();

        pedido.setDtEmissao(dtEmissao);
        return pedido;
    }

    public Pedido alterarDataEntregaPedido(Pedido pedido) {
        System.out.print("Digite a data de entrega (dd/MM/yyyy): ");
        Date dtEntrega = Util.pedirData();

        pedido.setDtEntrega(dtEntrega);
        return pedido;
    }

    public Pedido alterarObservacao(Pedido pedido) {
        System.out.println("Digite a nova observação: ");
        String obs = Main.input.nextLine();

        pedido.setObervacao(obs);
        return pedido;
    }

    public void imprimirPedidoComProdutos(Pedido pedido) {
        String pedidoNumero = "PEDIDO NÚMERO " + pedido.getIdPedido();
        String emissao = "Emissão : " + Util.formatDateddMMyyyy(pedido.getDtEmissao());
        String entrega = "Entrega: " + Util.formatDateddMMyyyy(pedido.getDtEntrega());
        String cliente = "Cliente: " + pedido.getCliente().getNome();
        String valorBruto = "Valor Bruto: R$" + pedido.getValorBruto();
        String valorTotal = "Valor Total: R$" + pedido.getValorTotal();

        Util.imprimirLinha();
        System.out.printf("""
                %-40s %-40s %-40s %-40s%n""",
                pedidoNumero, emissao, entrega, cliente);

        Util.imprimirLinha();
        Util.imprimirCabecalhoProdutoComQtdVendidaEDesconto();

        for (Produto produto : pedido.getProdutos()) {
            for (PedItem pedItem : pedido.getPedItems()) {
                if (pedItem.getProduto().getIdProduto() == produto.getIdProduto()) {
                    produto.imprimirDadosProdutoComQtdVendidaEDesconto(pedItem.getQuantidade(), pedItem.getVlDesconto(), pedItem.getVlUnitario());
                }
            }
        }
        Util.imprimirLinha();
        System.out.printf("""
                %-30s %-30s%n""",
                valorBruto, valorTotal);

        if (pedido.getObervacao() != null && !pedido.getObervacao().isBlank()) {
            Util.imprimirLinha();
            System.out.println("Obs: " + pedido.getObervacao());
            Util.imprimirLinha();
        }

        System.out.println("Pressione qualquer tecla para sair da impressão do pedido");
    }

    public Pedido removerUmProdutoDoPedido(Pedido pedido) {
        var produtoDAO = new ProdutoDAO();
        var produto = new Produto();
        int codProduto;
        char opcao;
        int index = 0;

        System.out.print("Digite o código do produto que deseja remover: ");
        do {
            int cont = 0;
            try {
                codProduto = Main.input.nextInt();

                if (codProduto <= 0) throw new Exception();

                produto = produtoDAO.buscarPorId(codProduto);

                if (produto.getDescricao() == null || produto.getDescricao().isBlank()) throw new Exception();

                for (Produto prodPed : pedido.getProdutos()) {

                    if (produto.getIdProduto() == prodPed.getIdProduto()) {
                        index = pedido.getProdutos().indexOf(prodPed);
                        cont++;
                    }

                }
                if (cont == 0) throw new Exception();

            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O código precisa ser um número inteiro maior que 0 e precisa estar no pedido");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                codProduto = 0;
                Main.input.nextLine();
            }
        } while (codProduto <= 0);
        Main.input.nextLine();

        System.out.print("Deseja remover " + produto.getDescricao() + "? S/N");
        do {
            String op = Main.input.nextLine().toUpperCase() + "R";
            opcao = op.charAt(0);

            switch (opcao) {
                case 'S' -> opcao = 'N';
                case 'N' -> {
                    return pedido;
                }
                default -> System.out.print("Opção inválida, digite novamente: ");
            }
        } while (opcao != 'N');
        pedido.getProdutos().remove(index);

        for (Iterator<PedItem> iterator = pedido.getPedItems().iterator(); iterator.hasNext();) {
            PedItem pedItem = iterator.next();
            if (pedItem.getProduto().getIdProduto() == produto.getIdProduto()) {
                iterator.remove();
            }
        }

        pedido = atualizarValoresPedido(pedido);
        return pedido;
    }

    public void salvarAlteracaoPedido(Pedido pedido) {
        var pedidoDAO = new PedidoDAO();
        var pedItemService = new PedItemService();

        if (pedido.getCliente() == null || pedido.getCliente().getNome() == null
                || pedido.getCliente().getNome().isBlank()) {
            Cor.fontRed();
            System.out.println("Você precisa inserir um cliente para salvar o pedido.");
            Cor.resetAll();
            return;
        } else if (pedido.getProdutos() == null
                || pedido.getProdutos().size() == 0) {
            Cor.fontRed();
            System.out.println("Você precisa inserir ao menos um produto para salvar o pedido.");
            Cor.resetAll();
            return;
        }

        try {
            pedidoDAO.alterar(pedido);
            pedItemService.apagarPorPedido(pedido);
            pedItemService.criarPedItemAposAlterarPedido(pedido);

            Cor.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                     PEDIDO ALTERADO COM SUCESSO!!!
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Cor.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pedido> buscarTodos() {
        var pedidoDAO = new PedidoDAO();
        var pedItemService = new PedItemService();
        var clienteDAO = new ClienteDAO();

        var pedidos = pedidoDAO.buscarTodos();

        for (Pedido pedido : pedidos) {
            List<Produto> produtos = new ArrayList<>();
            List<PedItem> pedItems = pedItemService.buscarPedItemsPorIdPedido(pedido.getIdPedido());
            pedido.setPedItems(pedItems);

            for (PedItem pedItem : pedItems) {
               produtos.add(pedItem.getProduto());
            }

            var cliente = clienteDAO.buscarPorId(pedido.getCliente().getIdCliente());
            pedido.setCliente(cliente);
            pedido.setProdutos(produtos);
        }

        return pedidos;
    }

    public void imprimirPedidosPorCliente() {
        var pedidos = buscarPorCliente();

        if (pedidos.size() == 0) {
            Cor.fontRed();
            System.out.println("Este cliente não possui nenhum pedido");
            Cor.resetAll();
            return;
        }

        if (Util.imprimirPedidoSemProdutos()) {
            for (Pedido pedido : pedidos) {
                imprimirPedidoSemProdutos(pedido);
                Cor.backgroundGrey();
                Util.imprimirLinha();
                Cor.resetAll();
            }
        } else {
            for (Pedido pedido : pedidos) {
                imprimirPedidoComProdutos(pedido);
                Cor.backgroundGrey();
                Util.imprimirLinha();
                Cor.resetAll();
            }
        }
    }

    public void imprimirPedidosPorPeriodo() {
        var pedidos = buscarPorPeriodo();

        if (pedidos.size() == 0) {
            Cor.fontRed();
            System.out.println("Não há pedidos neste período");
            Cor.resetAll();
            return;
        }

        if (Util.imprimirPedidoSemProdutos()) {
            for (Pedido pedido : pedidos) {
                imprimirPedidoSemProdutos(pedido);
                Cor.backgroundGrey();
                Util.imprimirLinha();
                Cor.resetAll();
            }
        } else {
            for (Pedido pedido : pedidos) {
                imprimirPedidoComProdutos(pedido);
                Cor.backgroundGrey();
                Util.imprimirLinha();
                Cor.resetAll();
            }
        }
    }

    public void imprimirTodosOsPedidos() {
        var pedidos = buscarTodos();

        if (pedidos.size() == 0) {
            Cor.fontRed();
            System.out.println("Não há pedidos cadastrados");
            Cor.resetAll();
            return;
        }

        if (Util.imprimirPedidoSemProdutos()) {
            for (Pedido pedido : pedidos) {
                imprimirPedidoSemProdutos(pedido);
                Cor.backgroundGrey();
                Util.imprimirLinha();
                Cor.resetAll();
            }
        } else {
            for (Pedido pedido : pedidos) {
                imprimirPedidoComProdutos(pedido);
                Cor.backgroundGrey();
                Util.imprimirLinha();
                Cor.resetAll();
            }
        }
    }

    public void imprimirPedidoSemProdutos(Pedido pedido) {
        String pedidoNumero = "PEDIDO NÚMERO " + pedido.getIdPedido();
        String emissao = "Emissão : " + Util.formatDateddMMyyyy(pedido.getDtEmissao());
        String entrega = "Entrega: " + Util.formatDateddMMyyyy(pedido.getDtEntrega());
        String cliente = "Cliente: " + pedido.getCliente().getNome();
        String valorBruto = "Valor Bruto: R$" + pedido.getValorBruto();
        String valorTotal = "Valor Total: R$" + pedido.getValorTotal();

        Util.imprimirLinha();
        System.out.printf("""
                %-40s %-40s %-40s %-40s%n""",
                pedidoNumero, emissao, entrega, cliente);
        Util.imprimirLinha();
        System.out.printf("""
                %-30s %-30s%n""",
                valorBruto, valorTotal);

        if (pedido.getObervacao() != null && !pedido.getObervacao().isBlank()) {
            Util.imprimirLinha();
            System.out.println("Obs: " + pedido.getObervacao());
            Util.imprimirLinha();
        }
    }

    public List<Pedido> buscarPorPeriodo() {
        var pedidoDAO = new PedidoDAO();
        var clienteDAO = new ClienteDAO();
        var pedItemService = new PedItemService();

        System.out.print("Escreva a data inicial dd/MM/yyyy: ");
        Date data1 = Util.pedirData();

        System.out.println("Escreva a data final dd/MM/yyyy: ");
        Date data2 = Util.pedirData();

        var pedidos = pedidoDAO.buscarPorData(data1.toString(), data2.toString());

        for (Pedido pedido : pedidos) {
            List<Produto> produtos = new ArrayList<>();
            List<PedItem> pedItems = pedItemService.buscarPedItemsPorIdPedido(pedido.getIdPedido());
            pedido.setPedItems(pedItems);

            for (PedItem pedItem : pedItems) {
                produtos.add(pedItem.getProduto());
            }

            var cliente = clienteDAO.buscarPorId(pedido.getCliente().getIdCliente());
            pedido.setCliente(cliente);
            pedido.setProdutos(produtos);
        }

        return pedidos;
    }

    public List<Pedido> buscarPorCliente() {
        var pedidoDAO = new PedidoDAO();
        var pedItemService = new PedItemService();
        var clienteService = new ClienteService();

        var cliente = clienteService.buscarClientePeloNome();

        var pedidos = pedidoDAO.buscarPorCliente(cliente);

        for (Pedido pedido : pedidos) {
            List<Produto> produtos = new ArrayList<>();
            List<PedItem> pedItems = pedItemService.buscarPedItemsPorIdPedido(pedido.getIdPedido());
            pedido.setPedItems(pedItems);

            for (PedItem pedItem : pedItems) {
                produtos.add(pedItem.getProduto());
            }

            pedido.setCliente(cliente);
            pedido.setProdutos(produtos);
        }

        return pedidos;
    }
}
