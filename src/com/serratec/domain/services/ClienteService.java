package com.serratec.domain.services;

import com.serratec.domain.models.Cliente;
import com.serratec.domain.repository.ClienteRepository;
import com.serratec.domain.repository.MainRepository;
import com.serratec.utils.Util;

import java.util.List;

public class ClienteService {
    public void imprimirClientesPeloNome(String nome) {
        ClienteRepository clienteRepository = new ClienteRepository(
                MainRepository.CONEXAO, MainRepository.SCHEMA);

        List<Cliente> clientes = clienteRepository.buscarClientesPeloNome(nome);

        Util.imprimirLinha();
        Util.imprimirCabecalhoCliente();

        for (Cliente cliente : clientes) {
            cliente.imprimirDadosCliente();
        }

        Util.imprimirLinha();
    }
}
