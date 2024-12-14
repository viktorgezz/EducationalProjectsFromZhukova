package ru.viktorgezz.menu;

import ru.viktorgezz.util.InputKeyboard;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

public class MenuService {
    private static boolean continueMenu;

    public void displayAndSelectAction(Menu menu) {
        continueMenu = true;
        while (continueMenu) {
            showMenu(menu);
            selectAction(menu);
        }
    }

    private void finishedMenu() {
        continueMenu = false;
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

    private void selectAction(Menu menu) {
        if (getExec(menu).isPresent()) {
            getExec(menu).get().exec();
        }
    }

    private Optional<Exec> getExec(Menu menu) {
        Exec exec = null;
        for (; ; ) {
            try {
                int answerUser = InputKeyboard.getInputInteger("Введите номер пункта меню: ");
                if (answerUser == menu.getItems().size()) {
                    finishedMenu();
                    break;
                }
                exec = menu.getItems().get(answerUser).getExec();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода числа");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Ошибка ввода не известного числа");
            }
        }
        return Optional.ofNullable(exec);
    }
}
