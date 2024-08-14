package ru.viktorgezz;

import java.util.*;
import java.util.stream.Collectors;

public class WordObject {

    public WordObject() {
        this.word = getRandomWord();
        this.currSymbol = new ArrayList<>(Collections.nCopies(word.length(), '_'));
    }

    private List<String> words = Arrays.asList(
            "яблоко", "банан", "вишня", "финик", "черника", "инжир", "виноград", "дыня",
            "киви", "лимон", "манго", "нектарин", "апельсин", "папайя", "айва", "малина",
            "клубника", "мандарин", "угли", "ваниль", "арбуз", "ксигуа", "батат", "кабачок",
            "авокадо", "черника", "дыня", "питайя", "баклажан", "фейхоа", "гуава",
            "голубика", "джекфрут", "кумкват", "лайм", "шелковица", "мускат", "олива", "персик",
            "слива", "гранат", "киноа", "ревень", "шпинат", "томат", "угли", "фиалка",
            "грецкий орех", "ксантан", "йогурт", "зебра", "миндаль", "брокколи", "морковь", "дайкон",
            "эндивий", "фенхель", "чеснок", "хрен", "айсберг", "халапеньо", "капуста", "салат",
            "гриб", "нектар", "окра", "петрушка", "перепел", "редис", "шалот", "репа",
            "удон", "уксус", "васаби", "анчоус", "базилик", "капуста", "укроп", "эскарол", "фета",
            "имбирь", "хикама", "кольраби", "мята", "мускат", "орегано", "петрушка", "корица", "шалфей",
            "тимьян", "укроп", "фенхель", "чеснок", "хрен", "шпинат", "щавель", "эстрагон",
            "артишок", "баклажан", "баранина", "брусника", "васаби", "винегрет", "гвоздика", "горчица",
            "грейпфрут", "груша", "дыня", "ежевика", "жасмин", "зеленый чай", "изюм", "какао",
            "каперсы", "картофель", "кедровый орех", "клубника", "клюква", "кокос", "кукуруза", "курица",
            "лавровый лист", "лосось", "малина", "мед", "молоко", "морковь", "мята", "огурец",
            "орех", "паприка", "перец", "петрушка", "помидор", "рыба", "сахар", "сельдерей",
            "сливки", "сметана", "соль", "спаржа", "тыква", "укроп", "фасоль", "хлеб",
            "чеснок", "шоколад", "яблоко", "яйцо", "ягода"
    );

    private String word;

    private List<Character> currSymbol;

    private String getRandomWord() {
        Random random = new Random();
        int indexRandom = random.nextInt(words.size());
        return words.get(indexRandom);
    }

    public boolean isCurrSymbolEqualsWord() {
        String currSymbol = this.currSymbol
                .stream().map(String::valueOf)
                .collect(Collectors
                        .joining());
        return this.word.equals(currSymbol);
    }

    public void insertChar(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                this.currSymbol.set(i, letter);
            }
        }
    }

    public void printCurrWord() {
        for (char el : this.currSymbol) {
            System.out.print(el + " ");
        }
        System.out.println();
    }
    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public List<Character> getCurrSymbol() {
        return currSymbol;
    }
}
