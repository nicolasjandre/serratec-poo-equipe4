package com.serratec.utils;

import com.serratec.domain.models.Pedido;

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
                4) Imprimir todos os produtos
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

    public static void alterarPedido(Pedido pedido) {
        if (pedido.getCliente() != null) {
            System.out.printf("""
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                         ALTERAR PEDIDO
                           CÓDIGO %d
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                1) Alterar cliente - Cliente atual: %s
                2) Alterar produtos %s
                3) Alterar observação
                4) Alterar data de emissão - %s
                5) Alterar data de entrega - %s
                6) Imprimir pedido
                7) Salvar pedido
                0) Voltar 
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                Digite sua opção:""", pedido.getIdPedido(),
                    pedido.getCliente().getNome() != null ? pedido.getCliente().getNome() : "\u001B[31mNenhum cliente inserido\u001B[0m",
                    pedido.getProdutos().size() == 0 ? "- \u001B[31mNenhum produto inserido\u001B[0m" : "",
                    Util.formatDateddMMyyyy(pedido.getDtEmissao()),
                    Util.formatDateddMMyyyy(pedido.getDtEntrega()));
        } else {
            System.out.print("""
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                         ALTERAR PEDIDO
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                1) Selecionar pedido
                0) Voltar 
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                Digite sua opção:""");
        }
    }
    public static void alterarProdutosPedido() {
        System.out.print("""
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                         ALTERAR PRODUTOS
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                1) Adicionar um produto
                2) Trocar um produto por outro
                3) Remover um produto
                4) Remover todos os produtos
                5) Alterar preço de um produto
                0) Voltar 
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                Digite sua opção:""");
    }
}
