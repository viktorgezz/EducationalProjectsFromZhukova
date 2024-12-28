package ru.viktorgezz.util;

import ru.viktorgezz.action.creation.WorldPopulationController;
import ru.viktorgezz.action.creature.CreatureService;
import ru.viktorgezz.map.MapRender;
import ru.viktorgezz.map.Node;

import java.util.Scanner;
import java.util.concurrent.*;

public class Turn {

    private final ExecutorService executorService;
    private volatile boolean runningSimulate = true;


    private final MapRender mapRender = new MapRender();
    private final CreatureService creatureService = CreatureService.getInstance();
    private final WorldPopulationController worldPopulationController = new WorldPopulationController();

    public Turn() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public void performOneTurn(Node root) {
        creatureService.performAction();
        printCurrTurn(root);
        worldPopulationController.isGenerationCreatureRequired(root);
        worldPopulationController.isGenerationGrassRequired(root);
    }

    public void printCurrTurn(Node root) {
        mapRender.printMap(root);
    }

    public void performInfinityTurn(Node root) {
        try {
            Future<?> stopFuture = executorService.submit(() -> stopSimulation());

            executorService.submit(() -> runSimulation(root));

            stopFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Ошибка выполнения потоков: " + e.getMessage(), e);
        }

    }

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

    private void stopSimulation() {
        new Scanner(System.in).nextLine();
        runningSimulate = false;
    }
}
