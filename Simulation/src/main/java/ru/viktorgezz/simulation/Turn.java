package ru.viktorgezz.simulation;

import ru.viktorgezz.action.creation.WorldPopulationController;
import ru.viktorgezz.action.creature.CreatureService;
import ru.viktorgezz.map.MapRender;
import ru.viktorgezz.map.Node;

import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Класс Turn отвечает за выполнение игровых ходов в симуляции.
 * Вывод текущего состояния карты.
 * Выполнение действий симуляции.
 * Выполнение бесконечных ходов с возможностью остановки.
 */
public class Turn {

    private final ExecutorService executorService; // Пул потоков для выполнения параллельных задач.
    private volatile boolean runningSimulate = true; // Флаг для управления состоянием симуляции.

    private final MapRender mapRender = new MapRender();  // Отвечает за визуализацию карты.
    private final CreatureService creatureService = CreatureService.getInstance(); // Управляет действиями существ.
    private final WorldPopulationController worldPopulationController = new WorldPopulationController();  // Управляет популяцией объектов.

    /**
     * Конструктор инициализирует пул потоков для выполнения задач.
     */
    public Turn() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    /**
     * Выполняет один игровой ход.
     * @param root Корневой узел карты.
     */
    public void performOneTurn(Node root) {
        creatureService.performAction();
        printCurrTurn(root);
        worldPopulationController.isGenerationCreatureRequired(root);
        worldPopulationController.isGenerationGrassRequired(root);
    }

    /**
     * Выводит текущее состояние карты.
     * @param root Корневой узел карты.
     */
    public void printCurrTurn(Node root) {
        mapRender.printMap(root);
    }

    /**
     * Запускает бесконечное выполнение ходов до остановки.
     * @param root Корневой узел карты.
     */
    public void performInfinityTurn(Node root) {
        try {
            Future<?> stopFuture = executorService.submit(() -> stopSimulation());

            executorService.submit(() -> runSimulation(root));

            stopFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Ошибка выполнения потоков: " + e.getMessage(), e);
        }

    }

    /**
     * Запускает бесконечный цикл выполнения ходов симуляции.
     * @param root Корневой узел карты.
     */
    private void runSimulation(Node root) {
        runningSimulate = true;
        while (runningSimulate) {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            performOneTurn(root);
        }
    }

    /**
     * Останавливает симуляцию при получении ввода от пользователя.
     */
    private void stopSimulation() {
        new Scanner(System.in).nextLine();
        runningSimulate = false;
    }
}
