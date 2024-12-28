package ru.viktorgezz.action.creation;

import ru.viktorgezz.action.Action;

/**
 * Абстрактный класс, от которого наследуются все классы, связанные с созданием и управлением сущностями.
 * Содержит ссылку на объект EntityCounter для работы с лимитами количества сущностей.
 */
public abstract class ACreation extends Action {

    protected EntityCounter entityCounter = new EntityCounter();

}
