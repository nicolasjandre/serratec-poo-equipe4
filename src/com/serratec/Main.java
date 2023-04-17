package com.serratec;

import com.serratec.domain.services.ClienteService;

import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ClienteService clienteService = new ClienteService();
        clienteService.listarClientePeloNome("NiCoLas");
    }
}