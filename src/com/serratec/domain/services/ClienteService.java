package com.serratec.domain.services;

import com.serratec.domain.models.Cliente;
import com.serratec.domain.repository.ClienteRepository;
import com.serratec.utils.Menu;
import com.serratec.utils.Util;

import java.sql.Date;
import java.util.List;

public class ClienteService {
    public void menuCliente() {
        Menu.clienteInicial();
    }
    public void cadastrarCliente() {
        System.out.printf("%s %n%30s%n %s",
                "_ ".repeat(30), "CADASTRO DE CLIENTE", "_ ".repeat(30));
    }

    public void imprimirClientesPeloNome(String nome) {
        ClienteRepository clienteRepository = new ClienteRepository();

        List<Cliente> clientes = clienteRepository.buscarPorNome(nome);

        Util.imprimirLinha();
        Util.imprimirCabecalhoCliente();

        for (Cliente cliente : clientes) {
            cliente.imprimirDadosCliente();
        }

        Util.imprimirLinha();
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
