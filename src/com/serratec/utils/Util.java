package com.serratec.utils;

import com.serratec.domain.models.Cliente;
import com.serratec.domain.repository.ClienteRepository;
import com.serratec.domain.repository.MainRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Util {
    public static String formatDate(Date date) {
        String inputDate = date.toString();

        LocalDate localDate = LocalDate.parse(inputDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

        String formattedDate = localDate.format(formatter);

        return formattedDate;
    }
    public static String formatCPF(String cpf) {
        StringBuilder sb = new StringBuilder(cpf);
        sb.insert(3, '.').insert(7, '.').insert(11, '-');
        return sb.toString();
    }
    public static void imprimirCabecalhoCliente() {
        System.out.printf("%-13s %-35s %-20s %-30s %-70s %s\n",
                "CÓDIGO", "NOME", "CPF", "NASCIMENTO", "ENDEREÇO", "TELEFONE");
    }

    public static void imprimirSistemaIniciado() {
        imprimirLinha();
        System.out.printf("%104s %n%110s %n", "SISTEMA INICIADO",
                "BEM VINDO AO SERRA CONSTRUÇÕES" );
        imprimirLinha();
    }

    public static void imprimirLinha() {
        System.out.println("_ ".repeat(96));
    }

    public static void criarClientesIniciais() {
        ClienteRepository clienteRepository = new ClienteRepository(MainRepository.CONEXAO, MainRepository.SCHEMA);
        List<Cliente> clientes = clienteRepository.buscarTodosOsClientes();
        var cliente = new Cliente();

        if (clientes.size() == 0) {
            cliente.setCpf("78912345601");
            cliente.setNome("Fernanda Souza");
            cliente.setDtNascimento(Date.valueOf("1976-03-05"));
            cliente.setEndereco("Estrada do Calembe, 234, Granja Florestal, Teresópolis RJ");
            cliente.setTelefone("(21)997654321");
            clienteRepository.incluirCliente(cliente);

            cliente.setCpf("98765432102");
            cliente.setNome("Pedro Alves");
            cliente.setDtNascimento(Date.valueOf("1990-08-20"));
            cliente.setEndereco("Rua Nova Friburgo, 567, Olaria, Nova Friburgo RJ");
            cliente.setTelefone("(22)998765432");
            clienteRepository.incluirCliente(cliente);

            cliente.setCpf("12345678903");
            cliente.setNome("Maria da Silva");
            cliente.setDtNascimento(Date.valueOf("1985-05-10"));
            cliente.setEndereco("Rua João da Cunha, 123, Várzea, Teresópolis RJ");
            cliente.setTelefone("(21)987654321");
            clienteRepository.incluirCliente(cliente);

            cliente.setCpf("23456789004");
            cliente.setNome("João dos Santos");
            cliente.setDtNascimento(Date.valueOf("1998-09-20"));
            cliente.setEndereco("Rua Manuel José Lebrão, 789, Agriões, Teresópolis RJ");
            cliente.setTelefone("(21)987654321");
            clienteRepository.incluirCliente(cliente);

            cliente.setCpf("45678912305");
            cliente.setNome("Carlos Pereira");
            cliente.setDtNascimento(Date.valueOf("1976-03-05"));
            cliente.setEndereco("Estrada dos Pássaros, 234, Granja Florestal, Teresópolis RJ");
            cliente.setTelefone("(21)997654321");
            clienteRepository.incluirCliente(cliente);

        }
    }
}
