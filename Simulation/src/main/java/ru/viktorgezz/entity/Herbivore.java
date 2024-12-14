package ru.viktorgezz.entity;

import ru.viktorgezz.map.Node;

import java.util.List;
import java.util.Random;

public class Herbivore extends Creature {

    public Herbivore() {
        setHp(3);
        setStep(2);
        setAge(1);
    }

    @Override
    public void playAction() {
        move(Grass.class, () -> eatGrass(getNode()));
        growOld();
    }

    private void eatGrass(Node nodeNearGrass) {
        List<Node> linkedNodes = searchNode.getConnectNodesWithEntity(nodeNearGrass);

        for (Node node : linkedNodes) {
            if (node.getEntity() != null && node.getEntity().getClass() == Grass.class) {
                Grass grass = (Grass) node.getEntity();
                grass.minusSaturation();
                if (grass.getSaturation() <= 0)
                    mapService.removeImmobileEntity(grass);

                developCharacteristic();
                plusHp();
                break;
            }
        }
    }

    private void developCharacteristic() {
        int randomNum = new Random().nextInt(2);
        switch (randomNum) {
            case 0:
                this.setStep(getStep() + 1);
        }
    }

    private void plusHp() {
        this.setHp(getHp() + 2);
    }

    public void minusHp(int damage) {
        this.setHp(getHp() - damage);
    }

    private void growOld() {
        setAge(getAge() + 1);
        if (getAge() % 40 == 0 && getStep() > 1)
            setStep(getStep() - 1);
    }
}
