package com.serratec.domain.services;

import com.serratec.Main;
import com.serratec.domain.models.Cliente;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.repository.PedidoRepository;
import com.serratec.utils.Cor;
import com.serratec.utils.Menu;
import com.serratec.utils.Util;

import java.sql.Date;
import java.util.InputMismatchException;
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
        System.out.printf("%s %n%39s%n %s%n",
                "_ ".repeat(30), "CADASTRO DE PEDIDO", "_ ".repeat(30));

        var pedidoRepository = new PedidoRepository();

        Pedido pedido = pedirDadosParaCriarPedido();

        try {
            pedidoRepository.incluir(pedido);
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

    public Pedido pedirDadosParaCriarPedido() {
        var clienteService = new ClienteService();
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
        pedido.setValorTotal(0d);
        return pedido;
    }

    @Override
    public void apagar() {
        int idPedido = 0;
        boolean continua;
        var pedidoRepository = new PedidoRepository();

        System.out.print("Digite o código do pedido: ");

        do {
            continua = false;
            try {
                idPedido = Main.input.nextInt();

                Pedido pedido = pedidoRepository.buscarPorId(idPedido);

                if (pedido.getCliente().getNome().isEmpty()) {
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

        pedidoRepository.apagarPorId(idPedido);
        Main.input.nextLine();
    }

    @Override
    public void alterar() {

    }

    @Override
    public List<Pedido> buscarTodos() {
        return null;
    }

    public void imprimirPedidosPorCliente() {

    }

    public void imprimirPedidosPorPeriodo() {

    }

    public void imprimirTodosOsPedidos() {

    }
}
