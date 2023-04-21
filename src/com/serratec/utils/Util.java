package com.serratec.utils;

import com.serratec.Main;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {
    public static String formatDate(Date date) {
        String inputDate = date.toString();

        LocalDate localDate = LocalDate.parse(inputDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy",
                new Locale("pt", "BR"));

        return localDate.format(formatter);
    }

    public static String formatDateddMMyyyy(Date date) {
        String inputDate = date.toString();

        LocalDate localDate = LocalDate.parse(inputDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return localDate.format(formatter);
    }
    public static void voltarAoMenu() {
        System.out.println("Pressione qualquer tecla para voltar ao menu");
        Main.input.nextLine();
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

    public static Date pedirData() {
        boolean continua;
        java.util.Date utilDate = null;

        do {
            continua = false;
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateStr = Main.input.nextLine();
                utilDate = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                Cor.fontRed();
                System.out.println("Formato inválido, certifique-se de usar o formato dd/MM/yyyy.");
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                continua = true;
            }
        } while (continua);

        return new Date(utilDate.getTime());
    }

    public static void imprimirCabecalhoProduto() {
        System.out.printf("%-13s %-35s %-20s %-30s %-70s %s\n",
                "CÓDIGO", "DESCRIÇÃO", "CATEGORIA", "ESTOQUE", "PREÇO DE CUSTO", "PREÇO DE VENDA");
    }

    public static void imprimirCabecalhoProdutoComQtdVendida() {
        System.out.printf("%-13s %-35s %-20s %-30s %-30s %-30s %s\n",
                "CÓDIGO", "DESCRIÇÃO", "CATEGORIA", "ESTOQUE",
                "PREÇO DE CUSTO", "PREÇO DE VENDA", "QUANTIDADE NO PEDIDO");
    }

    public static void imprimirCabecalhoProdutoComQtdVendidaEDesconto() {
        System.out.printf("%-13s %-35s %-20s %-30s %-30s %-30s %-40s %-30s\n",
                "CÓDIGO", "DESCRIÇÃO", "CATEGORIA", "ESTOQUE",
                "PREÇO DE CUSTO", "PREÇO DE VENDA", "QUANTIDADE NO PEDIDO", "DESCONTO");
    }

    public static void imprimirSistemaIniciado() {
        Cor.backgroundGrey();
        imprimirLinha();
        Cor.resetAll();
        Cor.fontBlue();
        System.out.printf("%103s %n%110s %n", "SISTEMA INICIADO",
                "BEM VINDO AO SERRA CONSTRUÇÕES" );
        Cor.resetAll();
        Cor.backgroundGrey();
        imprimirLinha();
        Cor.resetAll();
        System.out.printf("%107s %n%108s %n%108s %n%112s %n%107s %n%106s%n",
                "Nícolas Jandre De Faria",
                "Sophia Resende De Freitas",
                "Thallyson da Silva Antonio",
                "Rodrigo De Brites Sobrinho Lisbôa",
                "Arthur Barroso Barbosa",
                "Gabriel Rezende Maia");
        Cor.resetAll();
        Cor.backgroundGrey();
        imprimirLinha();
        Cor.resetAll();
    }

    public static void imprimirLinha() {
        Cor.fontBlue();
        System.out.println("_ ".repeat(126));
        Cor.resetAll();
    }

    public static boolean imprimirPedidoSemProdutos() {
        char opcao;

        System.out.print("""
                1) Imprimir sem os produtos
                2) Imprimir com os produtos""");

        do {
            String s = Main.input.nextLine().toUpperCase() + "R";
            opcao = s.charAt(0);

            switch (opcao) {
                case '1' -> { return true; }
                case '2' -> { return false; }
                default -> System.out.println("Opção inválida, digite novamente: ");
            }
        } while (true);
    }

    public static Double pedirDoubleMaiorQueZero(String mensagemDeErro, int valorMin, int valorMax) {
        Double dbReturn = 0.0;

        do {
            try {
                dbReturn = Main.input.nextDouble();

                if (valorMin != -1) {
                    if (dbReturn < valorMin) throw new Exception();
                }

                if (valorMax != -1) {
                    if (dbReturn > valorMax) throw new Exception();
                }
            } catch (Exception e) {
                Cor.fontRed();
                System.out.println(mensagemDeErro);
                Cor.resetAll();
                System.out.print("Digite novamente: ");
                Main.input.nextLine();
            }
        } while (dbReturn < valorMin);
        Main.input.nextLine();

        return dbReturn;
    }
}


