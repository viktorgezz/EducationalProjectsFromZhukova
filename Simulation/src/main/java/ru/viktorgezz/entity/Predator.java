package ru.viktorgezz.entity;

import ru.viktorgezz.map.Node;

import java.util.List;
import java.util.Random;

public class Predator extends Creature {

    private Integer countMurders = 0;
    private Integer damage = 1;
    private Integer numKillBeforeUpgrade = 2;

    public Predator() {
        setHp(2);
        setStep(1);
        setAge(0);
    }

    @Override
    public void playAction() {
        move(Herbivore.class, () -> attack(getNode()));
        growOld();
    }

    private void attack(Node nodeNearHerbivore) {
        List<Node> linkedNodes = searchNode.getConnectNodesWithEntity(nodeNearHerbivore);
        for (Node node : linkedNodes) {
            if (node != null && node.getEntity() != null && node.getEntity().getClass() == Herbivore.class) {
                Herbivore herbivore = (Herbivore) node.getEntity();
                herbivore.minusHp(damage);

                if (herbivore.getHp() <= 0) {
                    mapService.removeCreature(herbivore);
                    developCharacteristic();
                }
                break;
            }
        }
    }

    private void developCharacteristic() {
        countMurders++;
        if (countMurders > numKillBeforeUpgrade) {
            int temp = new Random().nextInt(2);

            switch (temp) {
                case 0:
                    this.damage += 1;
                    break;
                case 1:
                    this.setStep(getStep() + 1);
                    break;
            }
            numKillBeforeUpgrade *= 2;
        }
    }

    private void growOld() {
        setAge(getAge() + 1);
        if (getAge() % 50 == 0 && getStep() > 1)
            setStep(getStep() - 1);

        int numRandom = new Random().nextInt(5);
        if (getAge() == 150 && numRandom == 0)
            mapService.removeCreature(this);
    }

    public Integer getCountMurders() {
        return countMurders;
    }



}
