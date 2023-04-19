package com.serratec.domain.services;

import com.serratec.Main;
import com.serratec.domain.models.Cliente;
import com.serratec.domain.repository.ClienteRepository;
import com.serratec.utils.Color;
import com.serratec.utils.Menu;
import com.serratec.utils.Util;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.List;

public class ClienteService implements CRUDService<Cliente> {
    @Override
    public void cadastrar() {
        System.out.printf("%s %n%39s%n %s%n",
                "_ ".repeat(30), "CADASTRO DE CLIENTE", "_ ".repeat(30));

        var cliente = new Cliente();
        boolean continua;
        var clienteRepository = new ClienteRepository();

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
                Color.fontRed();
                System.out.println("O nome não pode estar vazio");
                Color.resetAll();
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

                if (cpf.length() != 11) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Color.fontRed();
                System.out.println("O CPF precisa ter 11 caracteres.");
                Color.resetAll();
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
                Color.fontRed();
                System.out.println("O DDD deve conter apenas números e duas casas. Ex: 21");
                Color.resetAll();
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
                Color.fontRed();
                System.out.println("O número de telefone deve conter apenas números.");
                Color.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        telefone = "(" + DDD + ")" + telefone;

        System.out.print("Digite a data de nascimento (YYYY-MM-DD): ");
        Date dtNascimento = null;

        do {
            continua = false;
            try {
                dtNascimento = Date.valueOf(Main.input.nextLine());
            } catch (Exception e) {
                Color.fontRed();
                System.out.println("Formato inválido, certifique-se de usar o formato YYYY-MM-DD.");
                Color.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setDtNascimento(dtNascimento);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);

        try {
            clienteRepository.incluir(cliente);
            Color.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                      CLIENTE CADASTRADO COM SUCESSO
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Color.resetAll();
            Util.voltarAoMenu();
        } catch (Exception e) {
            Color.fontRed();
            System.out.print("Houve um erro ao cadastrar o cliente.");
            Color.resetAll();
            Util.voltarAoMenu();
        }
    }
    @Override
    public void apagar() {
        int idCliente = 0;
        boolean continua;
        var clienteRepository = new ClienteRepository();

        System.out.print("Digite o código do cliente: ");

        do {
            continua = false;
            try {
                idCliente = Main.input.nextInt();

                Cliente cliente = clienteRepository.buscarPorId(idCliente);

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

        clienteRepository.apagarPorId(idCliente);
        Main.input.nextLine();
    }
    @Override
    public void alterar() {
        var cliente = new Cliente();
        int idCliente;
        boolean continua;
        var clienteRepository = new ClienteRepository();

        System.out.print("Digite o código do cliente: ");

        do {
            continua = false;
            try {
                idCliente = Main.input.nextInt();

                cliente = clienteRepository.buscarPorId(idCliente);

                if (cliente.getNome().isEmpty()) {
                    throw new NullPointerException();
                }

            } catch (InputMismatchException | NullPointerException e) {
                if (e instanceof InputMismatchException) {
                    Color.fontRed();
                    System.out.println("Valor inválido, você deve usar números inteiros");
                    Color.resetAll();
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

        System.out.print("Digite o novo nome: ");
        String nome = null;
        do {
            continua = false;
            try {
                nome = Main.input.nextLine();

                if (nome.isBlank()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Color.fontRed();
                System.out.println("O nome não pode estar vazio");
                Color.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite o novo CPF (use somente números): ");
        String cpf = "A";
        do {
            continua = false;
            try {
                cpf = Main.input.nextLine();

                if (cpf.length() != 11) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Color.fontRed();
                System.out.println("O CPF precisa ter 11 caracteres.");
                Color.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        System.out.print("Digite o novo endereço: ");
        var endereco = Main.input.nextLine();

        System.out.println("Digite o novo telefone.");
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
                Color.fontRed();
                System.out.println("O DDD deve conter apenas números e duas casas. Ex: 21");
                Color.resetAll();
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
                Color.fontRed();
                System.out.println("O número de telefone deve conter apenas números.");
                Color.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        telefone = "(" + DDD + ")" + telefone;

        System.out.print("Digite a nova data de nascimento (YYYY-MM-DD): ");
        Date dtNascimento = null;

        do {
            continua = false;
            try {
                dtNascimento = Date.valueOf(Main.input.nextLine());
            } catch (Exception e) {
                Color.fontRed();
                System.out.println("Formato inválido, certifique-se de usar o formato YYYY-MM-DD.");
                Color.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setDtNascimento(dtNascimento);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);

        try {
            clienteRepository.alterar(cliente);
            Color.fontGreen();
            System.out.print("""
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                      CLIENTE ALTERADO COM SUCESSO
                    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                    """);
            Color.resetAll();
            Util.voltarAoMenu();
        } catch (Exception e) {
            Color.fontRed();
            System.out.print("Houve um erro ao alterar dados do cliente.");
            Color.resetAll();
            Util.voltarAoMenu();
        }
    }
    @Override
    public List<Cliente> buscarTodos() {
        var clienteRepository = new ClienteRepository();
        return clienteRepository.buscarTodos();
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
                    Color.fontRed();
                    System.out.println("""
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                        Opção inválida, digite novamente""");
                    Color.resetAll();
                    continua = true;
                }
            }
        } while (continua);
    }
    public void imprimirTodosOsClientes() {
        List<Cliente> clientes = buscarTodos();

        if (clientes.size() != 0) {
            Util.imprimirLinha();
            Util.imprimirCabecalhoCliente();

            for (Cliente cliente : clientes) {
                cliente.imprimirDadosCliente();
            }

            Util.imprimirLinha();
        } else {
            Util.imprimirLinha();
            System.out.printf("%96s%n", "NENHUM CLIENTE ENCONTRADO");
            Util.imprimirLinha();
        }
        System.out.print("Pressione qualquer tecla para voltar ao menu");
        Main.input.nextLine();
    }
    public void imprimirClientesPeloNome() {
        String nome;
        ClienteRepository clienteRepository = new ClienteRepository();

        System.out.print("Digite o nome do cliente: ");
        nome = Main.input.nextLine();

        List<Cliente> clientes = clienteRepository.buscarPorNome(nome);

        if (clientes.size() != 0) {
            Util.imprimirLinha();
            Util.imprimirCabecalhoCliente();

            for (Cliente cliente : clientes) {
                cliente.imprimirDadosCliente();
            }

            Util.imprimirLinha();
        } else {
            Util.imprimirLinha();
            System.out.printf("%96s%n", "NENHUM CLIENTE ENCONTRADO");
            Util.imprimirLinha();
        }
        System.out.print("Pressione qualquer tecla para voltar ao menu");
        Main.input.nextLine();
    }
    public void criarClientesIniciais() {
        var clienteRepository = new ClienteRepository();
        List<Cliente> clientes = clienteRepository.buscarTodos();
        var cliente = new Cliente();

        if (clientes.size() == 0) {
            cliente.setCpf("78912345601");
            cliente.setNome("Fernanda Souza");
            cliente.setDtNascimento(Date.valueOf("1976-03-05"));
            cliente.setEndereco("Estrada do Calembe, 234, Granja Florestal, Teresópolis RJ");
            cliente.setTelefone("(21)997654321");
            clienteRepository.incluir(cliente);

            cliente.setCpf("98765432102");
            cliente.setNome("Pedro Alves");
            cliente.setDtNascimento(Date.valueOf("1990-08-20"));
            cliente.setEndereco("Rua Nova Friburgo, 567, Olaria, Nova Friburgo RJ");
            cliente.setTelefone("(22)998765432");
            clienteRepository.incluir(cliente);

            cliente.setCpf("12345678903");
            cliente.setNome("Maria da Silva");
            cliente.setDtNascimento(Date.valueOf("1985-05-10"));
            cliente.setEndereco("Rua João da Cunha, 123, Várzea, Teresópolis RJ");
            cliente.setTelefone("(21)987654321");
            clienteRepository.incluir(cliente);

            cliente.setCpf("23456789004");
            cliente.setNome("João dos Santos");
            cliente.setDtNascimento(Date.valueOf("1998-09-20"));
            cliente.setEndereco("Rua Manuel José Lebrão, 789, Agriões, Teresópolis RJ");
            cliente.setTelefone("(21)987654321");
            clienteRepository.incluir(cliente);

            cliente.setCpf("45678912305");
            cliente.setNome("Carlos Pereira");
            cliente.setDtNascimento(Date.valueOf("1976-03-05"));
            cliente.setEndereco("Estrada dos Pássaros, 234, Granja Florestal, Teresópolis RJ");
            cliente.setTelefone("(21)997654321");
            clienteRepository.incluir(cliente);
        }
    }
}
