package com.serratec.domain.services;

import com.serratec.domain.models.Cliente;
import com.serratec.domain.repository.ClienteRepository;
import com.serratec.utils.Util;

import java.util.List;

public class ClienteService {
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
}
