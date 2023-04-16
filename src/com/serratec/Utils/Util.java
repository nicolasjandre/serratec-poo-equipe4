package com.serratec.Utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {
    public static String formatDate(Date date) {
        String inputDate = date.toString();

        LocalDate localDate = LocalDate.parse(inputDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

        String formattedDate = localDate.format(formatter);

        return formattedDate;
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
}
