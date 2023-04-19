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

    public static void imprimirSistemaIniciado() {
        Cor.backgroundGrey();
        imprimirLinha();
        Cor.resetAll();
        Cor.fontBlue();
        System.out.printf("%104s %n%110s %n", "SISTEMA INICIADO",
                "BEM VINDO AO SERRA CONSTRUÇÕES" );
        Cor.resetAll();
        Cor.backgroundGrey();
        imprimirLinha();
        Cor.resetAll();
    }

    public static void imprimirLinha() {
        System.out.println("_ ".repeat(96));
    }
}
