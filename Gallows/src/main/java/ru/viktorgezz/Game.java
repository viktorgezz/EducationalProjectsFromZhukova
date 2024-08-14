package ru.viktorgezz;

import java.util.Scanner;

public class Game {

    private int counterWord;

    public void playGame() {
        InputValidation inputValidation = new InputValidation();
        this.counterWord = 0;

        while (true) {
            printStartMenu();
            Gallows gallows = new Gallows();

            while (!gallows.isOver()) {
                WordObject wordObject = new WordObject();
                inputValidation.clearInputLetters();
                System.out.println("Появилось слово");

                while (true) {
                    wordObject.printCurrWord();
                    if (wordObject.isCurrSymbolEqualsWord()) {
                        printSuccessFullyGuessedWord();
                        break;
                    }

                    System.out.println("Введи символ или слово: ");
                    String input = new Scanner(System.in).nextLine();

                    String error = inputValidation.validate(input, wordObject.getCurrSymbol());
                    if (inputValidation.getError()) {
                        System.out.print(error);
                        continue;
                    }

                    String word = wordObject.getWord();

                    if (input.equals(word)) {
                        printSuccessFullyGuessedWord();
                        break;
                    } else if (word.contains(input) && input.length() == 1) {
                        wordObject.insertChar(input.charAt(0));
                    } else {
                        System.out.println("Ошибочка)");
                        gallows.nextStage();
                        gallows.printGallows();
                        if (gallows.getStage() == 5) {
                            System.out.println("Слово было = "+ word);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void printStartMenu() {
        System.out.println();
        System.out.println("Набери \"1\" если хочешь начать игру \n" +
                "Набери \"0\" если хочешь выйти");
        String answer = new Scanner(System.in).nextLine();
        switch (answer) {
            case "1":
                System.out.println("Игра начинается --_--");
                break;
            default:
                System.out.println("Игра закончилась(");
                System.exit(123);
        }
    }

    private void printSuccessFullyGuessedWord() {
        System.out.println("----------------------------------------------");
        System.out.println("Точно в цель!");
        System.out.println(++counterWord);
        System.out.println();
    }
}
