package com.serratec.utils;

public class Menu {
    public static void inicial() {
        System.out.print("""
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                          MENU INICIAL
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                1) Cliente
                2) Produto
                3) Pedido
                0) Sair do programa
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                Digite sua opção:""");
    }
    public static void clienteInicial() {
        System.out.print("""
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                          MENU CLIENTE
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                1) Novo Cliente
                2) Alterar Cliente
                3) Apagar Cliente
                4) Imprimir clientes pelo nome
                5) Imprimir todos os clientes
                0) Voltar 
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                Digite sua opção:""");
    }

    public static void produtoInicial() {
        System.out.print("""
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                           MENU PRODUTO
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                1) Novo Produto
                2) Alterar Produto
                3) Apagar Produto
                5) Imprimir todos os produtos
                0) Voltar 
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                Digite sua opção:""");
    }

    public static void pedidoInicial() {
        System.out.print("""
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                           MENU PEDIDO
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                1) Novo Pedido
                2) Alterar Pedido
                3) Apagar Pedido
                4) Imprimir pedidos por cliente
                5) Imprimir pedidos por período
                6) Imprimir todos os pedidos
                0) Voltar 
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                Digite sua opção:""");
    }
}
