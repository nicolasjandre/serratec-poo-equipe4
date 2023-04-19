package com.serratec.utils;

public class Cor {
    public static void fontRed() {
        System.out.print("\u001B[31m");
    }

    public static void fontBlue() {
        System.out.print("\u001B[34m");
    }

    public static void fontGreen() {
        System.out.print("\u001B[32m");
    }

    public static void resetAll() {
        System.out.print("\u001B[0m");
    }

    public static void backgroundGrey() {
        System.out.print("\u001B[47m");
    }
}
