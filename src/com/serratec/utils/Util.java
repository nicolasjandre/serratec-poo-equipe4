package com.serratec.utils;

import com.serratec.Main;

import java.sql.Date;
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

    public static void imprimirSistemaIniciado() {
        Color.backgroundGrey();
        imprimirLinha();
        Color.resetAll();
        Color.fontBlue();
        System.out.printf("%104s %n%110s %n", "SISTEMA INICIADO",
                "BEM VINDO AO SERRA CONSTRUÇÕES" );
        Color.resetAll();
        Color.backgroundGrey();
        imprimirLinha();
        Color.resetAll();
    }

    public static void imprimirLinha() {
        System.out.println("_ ".repeat(96));
    }
}
