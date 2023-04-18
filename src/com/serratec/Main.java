package com.serratec;

import com.serratec.domain.repository.MainRepository;
import com.serratec.domain.services.ClienteService;
import com.serratec.utils.Util;

import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        MainRepository.iniciarConexaoComBanco();
        Util.criarClientesIniciais();

        ClienteService clienteService = new ClienteService();
        clienteService.imprimirClientesPeloNome("a");
    }
}