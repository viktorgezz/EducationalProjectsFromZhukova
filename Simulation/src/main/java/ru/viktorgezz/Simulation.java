package ru.viktorgezz;

import ru.viktorgezz.actions.*;
import ru.viktorgezz.map.MapRender;
import ru.viktorgezz.map.MapWorld;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Simulation {

    private final List<Action> initActions = new LinkedList<>();
    private final LinkedList<Action> turnActions = new LinkedList<>();

    private final MapWorld map = new MapWorld();
    private final MapRender mapRender;
    private final WorldPopulationController checkNumHerbivore;

    private Integer countTern = 1;

    private volatile boolean runningSimulate = true;
    private final ExecutorService executorService;
    private Future<?> simulationFuture;

    public Simulation(int horizontalSize, int verticalSize) {
        map.createWorld(horizontalSize, verticalSize);
        mapRender = new MapRender(map.getRoot());
        checkNumHerbivore = new WorldPopulationController(map);

        this.executorService = Executors.newFixedThreadPool(2);
    }


    public void run() {
        performInitialActions();
        simulationFuture = executorService.submit(this::controlSimulation);
    }

    private void performInitialActions() {
        fillInitActions();
        for (Action action : initActions)
            action.perform();
        printMap();
    }

    private void controlSimulation() {
        while (runningSimulate) {
            printStartMenu();

            String answer = new Scanner(System.in).nextLine();
            switch (answer) {
                case "1":
                    nextTurn();
                    break;

                case "2":
                    printCountTern();
                    printMap();
                    break;

                case "3":
                    executorService.submit(this::stopSimulation);
                    runInfiniteSimulation();
                    break;

                default:
                    continue;
            }
        }
    }


    private void runInfiniteSimulation() {
        while (runningSimulate) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            nextTurn();
        }
    }

    private void nextTurn() {
        fillMainActions();
        turnActions.pop().perform();
        printCountTern();
        printMap();
        countTern++;
    }

    private void fillInitActions() {
        initActions.add(new CreationEntities(map));
    }

    private void fillMainActions() {
        turnActions.add(new MoveCreatures(map));
        generateEntitiesIfRequired();
    }

    private void generateEntitiesIfRequired() {
        if (checkNumHerbivore.isGrassGenerationRequired(countTern)) {
            turnActions.addFirst(new CreationGrass(map));
        }
        if (checkNumHerbivore.isHerbivoreGenerationRequired()) {
            turnActions.addFirst(new CreationHerbivore(map));
        }

        if (checkNumHerbivore.isPredatorGenerationRequired()) {
            turnActions.addFirst(new CreationPredator(map));
        }
    }

    private void stopSimulation() {
        new Scanner(System.in).nextLine();
        runningSimulate = false;

        if (simulationFuture != null) {
            try {
                simulationFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        runningSimulate = true;
        simulationFuture = executorService.submit(this::controlSimulation);
    }


    private void printStartMenu() {
        System.out.println("Выберите число для действия  \n" +
                "1. один ход  \n" +
                "2. текущий ход \n" +
                "3. бесконечный ходы"
        );
    }

    private void printCountTern() {
        System.out.println(countTern);
    }

    private void printMap() {
        mapRender.printMap();
    }
}