package com.serratec;

import com.serratec.domain.repository.MainRepository;

import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static final String PATH = "/home/nicolas/";
    public static final String SFILE = "DadosConexao.ini";
    public static final String BD = "trabalhofinalpoo";
    public static final String SCHEMA = "sistema";
    public static void main(String[] args) {
        MainRepository.CriarDatabase(PATH, SFILE, BD, SCHEMA);
    }
}