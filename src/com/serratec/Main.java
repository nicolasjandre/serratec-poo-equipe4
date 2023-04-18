package com.serratec;

import com.serratec.domain.models.Cliente;
import com.serratec.domain.models.Pedido;
import com.serratec.domain.repository.ClienteRepository;
import com.serratec.domain.repository.MainRepository;
import com.serratec.domain.repository.PedidoRepository;
import com.serratec.domain.services.ClienteService;
import com.serratec.utils.Util;

import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        MainRepository.iniciarConexaoComBanco();
        Util.criarClientesIniciais();

        ClienteService clienteService = new ClienteService();
        clienteService.imprimirClientesPeloNome("a");

        var pedidoRepository = new PedidoRepository();
        var pedido = new Pedido();
        var clienteRepository = new ClienteRepository();
        Cliente cliente = clienteRepository.buscarPorId(2);
        pedido.setCliente(cliente);
        pedido.setObervacao("volte sempre");
        pedido.setValorTotal(1200.00);
        pedido.setDtEntrega(Date.valueOf("2023-03-05"));
        pedido.setDtEmissao(Date.valueOf("2022-03-05"));

        var pedido3 = pedidoRepository.buscarPorCliente(cliente);
        System.out.println(pedido3.get(0).getValorTotal());

//        pedidoRepository.apagarPorId(2);
//
//        var pedido1 = pedidoRepository.buscarPorData("2022-03-05" , "2022-04-08");
//        System.out.println(pedido1.get(0).getValorTotal());
//
        var pedido2 = pedidoRepository.buscarPorId(1);
        System.out.println(pedido2.getValorTotal());

        var pedido4 = pedidoRepository.buscarTodos();
        System.out.println(pedido4.get(0).getValorTotal());

    }

}