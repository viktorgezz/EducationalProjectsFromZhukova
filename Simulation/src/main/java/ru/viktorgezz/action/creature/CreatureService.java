package ru.viktorgezz.action.creature;

import ru.viktorgezz.action.Action;
import ru.viktorgezz.entity.Creature;
import ru.viktorgezz.entity.Grass;
import ru.viktorgezz.entity.Herbivore;
import ru.viktorgezz.entity.Predator;
import ru.viktorgezz.map.Node;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CreatureService extends Action {

    private final Movement movement = new Movement();

    private static CreatureService instance;

    private CreatureService() {
    }

    public static CreatureService getInstance() {
        if (instance == null) {
            instance = new CreatureService();
        }
        return instance;
    }

    public void performAction() {
        CopyOnWriteArrayList<Creature> creatures = creatureProvider.getCreatures();
        Iterator<Creature> iterator = creatures.iterator();

        while (iterator.hasNext()) {
            Creature creature = iterator.next();
            if (creature instanceof Herbivore) {
                movement.move(Grass.class, () -> eatGrass((Herbivore) creature), creature);
            } else if (creature instanceof Predator) {
                movement.move(Herbivore.class, () -> attack((Predator) creature), creature);
            }
            growOld(creature);
        }
    }

    private void eatGrass(Herbivore herbivore) {
        List<Node> linkedNodes = searchNode.getConnectNodesWithEntity(herbivore.getNode());

        for (Node node : linkedNodes) {
            if (node.getEntity() != null && node.getEntity().getClass() == Grass.class) {
                Grass grass = (Grass) node.getEntity();
                grass.minusSaturation();
                if (grass.getSaturation() <= 0)
                    creatureProvider.removeImmobileEntity(grass);

                developCharacteristic(herbivore);
                plusHp(herbivore);
                break;
            }
        }
    }

    private void attack(Predator predator) {
        List<Node> linkedNodes = searchNode.getConnectNodesWithEntity(predator.getNode());
        for (Node node : linkedNodes) {
            if (node != null && node.getEntity() != null && node.getEntity().getClass() == Herbivore.class) {
                Herbivore herbivore = (Herbivore) node.getEntity();
                herbivore.minusHp(predator.getDamage());

                if (herbivore.getHp() <= 0) {
                    creatureProvider.removeCreature(herbivore);
                    developCharacteristic(predator);
                }
                break;
            }
        }
    }

    private void developCharacteristic(Herbivore herbivore) {
        int randomNum = new Random().nextInt(2);
        switch (randomNum) {
            case 0:
                herbivore.setStep(herbivore.getStep() + 1);
        }
    }

    private void developCharacteristic(Predator predator) {
        predator.setCountMurders(predator.getCountMurders() + 1);
        if (predator.getCountMurders() > predator.getNumKillBeforeUpgrade()) {
            int temp = new Random().nextInt(2);

            switch (temp) {
                case 0:
                    predator.setDamage(predator.getDamage() + 1);
                    break;
                case 1:
                    predator.setStep(predator.getStep() + 1);
                    break;
            }
            predator.setNumKillBeforeUpgrade(predator.getNumKillBeforeUpgrade() * 2);
        }
    }

    private void plusHp(Herbivore herbivore) {
        herbivore.setHp(herbivore.getHp() + 2);
    }

    private void growOld(Creature creature) {
        creature.setAge(creature.getAge() + 1);
        if (creature.getAge() % 50 == 0 && creature.getStep() > 1) {
            creature.setStep(creature.getStep() - 1);
        }

        int numRandom = new Random().nextInt(5);
        if (creature.getAge() == 150 && numRandom == 0) {
            creatureProvider.removeCreature(creature);
        }
    }
}
