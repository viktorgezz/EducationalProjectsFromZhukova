package ru.viktorgezz;

import java.util.HashSet;
import java.util.List;

public class InputValidation {

    private String input;
    private Boolean isError;
    private List<Character> currSymbol;
    private HashSet<Character> inputLetters;


    public String validate(String input, List<Character> currSymbol) {
        this.isError = false;
        this.input = input;
        this.currSymbol = currSymbol;

        StringBuilder error = new StringBuilder();

        if (checkLettersRusLower()) {
            error.append("Ввод должен содеражть строчные RU буквы" + "\n");
            this.isError = true;
        }

        if (checkRepeatLetterWord()) {
            error.append("Такой символ уже есть" + "\n");
            this.isError = true;
        } else if (checkRepeatLetterInput()) {
            error.append("Такой символ уже был введён" + "\n");
            this.isError = true;
        }

        return error.toString();
    }

    private boolean checkLettersRusLower() {
        int index;
        for (int i = 0; i < input.length(); i++) {
            index = input.charAt(i);
            if (!(index >= 1072 && index <= 1103)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRepeatLetterWord() {
        return currSymbol.contains(input.charAt(0));
    }

    private boolean checkRepeatLetterInput () {
        if (inputLetters.contains(input.charAt(0)))
            return true;
        inputLetters.add(input.charAt(0));
        return false;
    }

    public void clearInputLetters() {
        this.inputLetters = new HashSet<>();
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }
}
