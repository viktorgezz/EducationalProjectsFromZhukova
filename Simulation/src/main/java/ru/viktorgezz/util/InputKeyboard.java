package ru.viktorgezz.util;

import java.util.Scanner;

public class InputKeyboard {
    public static String getInputString(String message) {
        System.out.println(message);
        return new Scanner(System.in).nextLine();
    }

    public static Integer getInputInteger(String message) {
        System.out.println(message);
        return new Scanner(System.in).nextInt();
    }
}
