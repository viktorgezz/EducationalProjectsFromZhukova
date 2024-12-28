package ru.viktorgezz.menu;

import ru.viktorgezz.map.Node;
import ru.viktorgezz.util.InputKeyboard;

import java.util.InputMismatchException;
import java.util.List;

public class MenuService {

    public void displayAndSelectAction(Menu menu, Node root) {
        for (; ; ) {
            try {
                showMenu(menu);
                int answerUser = InputKeyboard.getInputInteger("Введите номер пункта меню: ");
                if (answerUser == menu.getItems().size()) {
                    System.exit(0);
                    break;
                }

                menu.getItems().get(answerUser).getExec().exec(root);
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода числа");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Ошибка ввода не известного числа");
            }
        }
    }

    private void showMenu(Menu menu) {
        List<MenuItem> actions = menu.getItems();
        int size = actions.size();
        for (int i = 0; i < size; i++) {
            System.out.println(i + ". " + actions.get(i).getDescription());
        }
        System.out.println(actions.size() + ". Выход");
        System.out.println();
    }
}
