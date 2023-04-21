package com.serratec.domain.services;

import com.serratec.Main;
import com.serratec.domain.models.Cliente;
import com.serratec.domain.DAO.ClienteDAO;
import com.serratec.utils.Cor;
import com.serratec.utils.Menu;
import com.serratec.utils.Util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;

public class ClienteService implements CRUDService<Cliente> {
    @Override
    public void cadastrar() {
        System.out.printf("%s %n%39s%n %s%n",
                "_ ".repeat(30), "CADASTRO DE CLIENTE", "_ ".repeat(30));

        var clienteDAO = new ClienteDAO();

        Cliente cliente = pedirDadosParaCriarCliente();

        try {
            clienteDAO.incluir(cliente);
            Cor.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                      CLIENTE CADASTRADO COM SUCESSO
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Cor.resetAll();
            Util.voltarAoMenu();
        } catch (Exception e) {
            Cor.fontRed();
            System.out.print("Houve um erro ao cadastrar o cliente.");
            Cor.resetAll();
            Util.voltarAoMenu();
        }
    }

    public Cliente pedirDadosParaCriarCliente() {
        var cliente = new Cliente();
        boolean continua;

        System.out.print("Digite o nome do cliente: ");
        String nome = null;
        do {
            continua = false;
            try {
                nome = Main.input.nextLine();

                if (nome.isBlank()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O nome não pode estar vazio");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite o CPF (use somente números): ");
        String cpf = "A";
        do {
            continua = false;
            try {
                cpf = Main.input.nextLine();

                if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O CPF precisa ter 11 caracteres.");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite endereço: ");
        var endereco = Main.input.nextLine();

        System.out.println("Digite telefone.");
        String DDD = null;
        String telefone = null;

        System.out.print("DDD: ");
        do {
            continua = false;
            try {
                DDD = Main.input.nextLine();

                if (!DDD.matches("\\d+") || DDD.length() != 2) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O DDD deve conter apenas números e duas casas. Ex: 21");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Número: ");
        do {
            continua = false;
            try {
                telefone = Main.input.nextLine();

                if (!telefone.matches("\\d+")) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println("O número de telefone deve conter apenas números.");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        telefone = "(" + DDD + ")" + telefone;

        System.out.print("Digite a nova data de nascimento (dd/MM/yyyy): ");
        java.util.Date utilDate = null;

        do {
            continua = false;
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateStr = Main.input.nextLine();
                utilDate = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                Cor.fontRed();
                System.out.println("Formato inválido, certifique-se de usar o formato dd/MM/yyyy.");

                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        Date dtNascimento = new Date(utilDate.getTime());

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setDtNascimento(dtNascimento);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);
        return cliente;

    }
    @Override
    public void apagar() {
        int idCliente = 0;
        boolean continua;
        var clienteDAO = new ClienteDAO();

        System.out.print("Digite o código do cliente: ");

        do {
            continua = false;
            try {
                idCliente = Main.input.nextInt();

                Cliente cliente = clienteDAO.buscarPorId(idCliente);

                if (cliente.getNome().isEmpty()) {
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

        clienteDAO.apagarPorId(idCliente);
        Main.input.nextLine();
    }
    @Override
    public void alterar() {
        var clienteQueSeraAlterado = new Cliente();
        int idCliente;
        boolean continua;
        var clienteDAO = new ClienteDAO();

        System.out.print("Digite o código do cliente: ");

        do {
            continua = false;
            try {
                idCliente = Main.input.nextInt();

                clienteQueSeraAlterado = clienteDAO.buscarPorId(idCliente);

                if (clienteQueSeraAlterado.getNome().isEmpty()) {
                    throw new NullPointerException();
                }

            } catch (InputMismatchException | NullPointerException e) {
                if (e instanceof InputMismatchException) {
                    Cor.fontRed();
                    System.out.println("Valor inválido, você deve usar números inteiros");
                    Cor.resetAll();
                    System.out.print("Digite novamente: ");
                    continua = true;
                    Main.input.nextLine();
                } else {
                    Util.voltarAoMenu();
                    Main.input.nextLine();
                    return;
                }
            }
        } while (continua);
        Main.input.nextLine();

        Cliente clienteNovo = pedirDadosParaCriarCliente();
        clienteQueSeraAlterado.setEndereco(clienteNovo.getEndereco());
        clienteQueSeraAlterado.setTelefone(clienteNovo.getTelefone());
        clienteQueSeraAlterado.setCpf(clienteNovo.getCpf());
        clienteQueSeraAlterado.setDtNascimento(clienteNovo.getDtNascimento());
        clienteQueSeraAlterado.setNome(clienteNovo.getNome());

        try {
            clienteDAO.alterar(clienteQueSeraAlterado);

            Cor.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                      CLIENTE ALTERADO COM SUCESSO
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Cor.resetAll();
            Util.voltarAoMenu();
        } catch (Exception e) {
            Cor.fontRed();
            System.out.print("Houve um erro ao alterar dados do cliente.");
            Cor.resetAll();
            Util.voltarAoMenu();
        }
    }
    @Override
    public List<Cliente> buscarTodos() {
        var clienteDAO = new ClienteDAO();
        return clienteDAO.buscarTodos();
    }
    public void menuCliente() {
        String s;
        char opcao;
        boolean continua;

        do {
            continua = false;
            Menu.clienteInicial();

            s = Main.input.nextLine() + "R";
            opcao = s.charAt(0);

            switch (opcao) {
                case '1' -> cadastrar();
                case '2' -> alterar();
                case '3' -> apagar();
                case '4' -> imprimirClientesPeloNome();
                case '5' -> imprimirTodosOsClientes();
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
    public void imprimirTodosOsClientes() {
        List<Cliente> clientes = buscarTodos();

        if (clientes.size() != 0) {
            Cor.fontGreen();
            Util.imprimirLinha();
            Cor.resetAll();
            Util.imprimirCabecalhoCliente();

            for (Cliente cliente : clientes) {
                cliente.imprimirDadosCliente();
            }
            Cor.fontGreen();
            Util.imprimirLinha();
            Cor.resetAll();
        } else {
            Util.imprimirLinha();
            Cor.fontRed();
            System.out.printf("%96s%n", "NENHUM CLIENTE ENCONTRADO");
            Cor.resetAll();
            Util.imprimirLinha();
        }
        System.out.print("Pressione qualquer tecla para voltar ao menu");
        Main.input.nextLine();
    }
    public void imprimirClientesPeloNome() {
        String nome;
        ClienteDAO clienteDAO = new ClienteDAO();
        System.out.print("Digite o nome do cliente: ");
        nome = Main.input.nextLine();

        List<Cliente> clientes = clienteDAO.buscarPorNome(nome);

        if (clientes.size() != 0) {
            Cor.fontGreen();
            Util.imprimirLinha();
            Cor.resetAll();
            Util.imprimirCabecalhoCliente();

            for (Cliente cliente : clientes) {
                cliente.imprimirDadosCliente();
            }
            Cor.fontGreen();
            Util.imprimirLinha();
            Cor.resetAll();
        } else {
            Util.imprimirLinha();
            Cor.fontRed();
            System.out.printf("%96s%n", "NENHUM CLIENTE ENCONTRADO");
            Cor.resetAll();
            Util.imprimirLinha();
        }
        System.out.print("Pressione qualquer tecla para voltar ao menu");
        Main.input.nextLine();
    }
    public Cliente buscarClientePorCpf() {
        ClienteDAO clienteDAO = new ClienteDAO();
        String cpf = "A";
        char opcao = 'R';
        boolean continua;
        var cliente = new Cliente();

        imprimirTodosOsClientes();

        do {
            System.out.print("Digite o CPF (use somente números): ");

            do {
                continua = false;
                try {
                    cpf = Main.input.nextLine();

                    if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    Cor.fontRed();
                    System.out.println("O CPF precisa ter 11 caracteres.");
                    Cor.resetAll();
                    System.out.print("Digite novamente: ");
                    continua = true;
                }
            } while (continua);

            cliente = clienteDAO.buscarPorCpf(cpf);

            if (cliente == null || cliente.getNome() == null || cliente.getNome().isBlank()) continue;

            System.out.println("Deseja escolher o cliente " + cliente.getNome() + "? S/N");

            do {
                String op = Main.input.nextLine().toUpperCase() + "R";
                opcao = op.charAt(0);

                switch (opcao) {
                    case 'S' -> {
                        return cliente;
                    }
                    case 'N' -> {}
                    default -> System.out.print("Opção inválida, digite novamente: ");
                }
            } while (opcao != 'N');

        } while (opcao != 'N');

        return cliente;
    }
    public Cliente buscarClientePeloNome() {
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes;
        var cliente = new Cliente();
        String nome;
        char opcao;

        do {
            do {
                System.out.print("Digite o nome do cliente: ");
                nome = Main.input.nextLine();

                clientes = clienteDAO.buscarPorNome(nome);

                if (clientes.size() != 0) {
                    Cor.fontGreen();
                    Util.imprimirLinha();
                    Cor.resetAll();
                    Util.imprimirCabecalhoCliente();

                    for (Cliente clienteL : clientes) {
                        clienteL.imprimirDadosCliente();
                    }
                    Cor.fontGreen();
                    Util.imprimirLinha();
                    Cor.resetAll();

                    if (clientes.size() != 1) {
                        System.out.println("Seja específico no nome do cliente até restar somente 1");
                    }

                } else {
                    Util.imprimirLinha();
                    Cor.fontRed();
                    System.out.printf("%96s%n", "NENHUM CLIENTE ENCONTRADO");
                    Cor.resetAll();
                    Util.imprimirLinha();
                }
            } while (clientes.size() != 1);

            cliente = clientes.get(0);
            System.out.println("Deseja escolher o cliente " + cliente.getNome() + "? S/N");

            do {
                String op = Main.input.nextLine().toUpperCase() + "R";
                opcao = op.charAt(0);

                switch (opcao) {
                    case 'S' -> {
                        return cliente;
                    }
                    case 'N' -> {}
                    default -> System.out.print("Opção inválida, digite novamente: ");
                }
            } while (opcao != 'N');
        } while (opcao != 'S');

        return cliente;
    }
    public Cliente buscarClientesPeloId() {
        ClienteDAO clienteDAO = new ClienteDAO();
        var cliente = new Cliente();
        char opcao;
        int idCliente = 0;

        do {
            System.out.print("Digite o código do cliente: ");

            do {
                try {
                    idCliente = Main.input.nextInt();

                    if (idCliente <= 0) throw new Exception();
                } catch (Exception e) {
                    Cor.fontRed();
                    System.out.println("O código precisa ser um número inteiro maior que 0");
                    Cor.resetAll();
                    System.out.print("Digite novamente: ");
                    Main.input.nextLine();
                }
            } while (idCliente <= 0);
            Main.input.nextLine();

            cliente = clienteDAO.buscarPorId(idCliente);

            if (cliente == null || cliente.getNome() == null || cliente.getNome().isBlank()) {
                opcao = 'N';
                continue;
            }

            System.out.println("Deseja escolher o cliente " + cliente.getNome() + "? S/N");

            do {
                String op = Main.input.nextLine().toUpperCase() + "R";
                opcao = op.charAt(0);

                switch (opcao) {
                    case 'S' -> {
                        return cliente;
                    }
                    case 'N' -> {}
                    default -> System.out.print("Opção inválida, digite novamente: ");
                }
            } while (opcao != 'N');

        } while (opcao == 'N');

        return cliente;
    }
    public void criarClientesIniciais() {
        var clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.buscarTodos();
        var cliente = new Cliente();

        if (clientes.size() == 0) {
            cliente.setCpf("78912345601");
            cliente.setNome("Fernanda Souza");
            cliente.setDtNascimento(Date.valueOf("1976-03-05"));
            cliente.setEndereco("Estrada do Calembe, 234, Granja Florestal, Teresópolis RJ");
            cliente.setTelefone("(21)997654321");
            clienteDAO.incluir(cliente);

            cliente.setCpf("98765432102");
            cliente.setNome("Pedro Alves");
            cliente.setDtNascimento(Date.valueOf("1990-08-20"));
            cliente.setEndereco("Rua Nova Friburgo, 567, Olaria, Nova Friburgo RJ");
            cliente.setTelefone("(22)998765432");
            clienteDAO.incluir(cliente);

            cliente.setCpf("12345678903");
            cliente.setNome("Maria da Silva");
            cliente.setDtNascimento(Date.valueOf("1985-05-10"));
            cliente.setEndereco("Rua João da Cunha, 123, Várzea, Teresópolis RJ");
            cliente.setTelefone("(21)987654321");
            clienteDAO.incluir(cliente);

            cliente.setCpf("23456789004");
            cliente.setNome("João dos Santos");
            cliente.setDtNascimento(Date.valueOf("1998-09-20"));
            cliente.setEndereco("Rua Manuel José Lebrão, 789, Agriões, Teresópolis RJ");
            cliente.setTelefone("(21)987654321");
            clienteDAO.incluir(cliente);

            cliente.setCpf("45678912305");
            cliente.setNome("Carlos Pereira");
            cliente.setDtNascimento(Date.valueOf("1976-03-05"));
            cliente.setEndereco("Estrada dos Pássaros, 234, Granja Florestal, Teresópolis RJ");
            cliente.setTelefone("(21)997654321");
            clienteDAO.incluir(cliente);
        }
    }
}
