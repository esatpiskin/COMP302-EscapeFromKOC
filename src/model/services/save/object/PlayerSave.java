package model.services.save.object;

import java.io.Serializable;

public class PlayerSave implements Serializable {

    int currentBuilding;
    IntPair position;
    int health;

    public int getCurrentBuilding() {
        return currentBuilding;
    }

    public void setCurrentBuilding(int currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public IntPair getPosition() {
        return position;
    }

    public void setPosition(IntPair position) {
        this.position = position;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return "PlayerSave{" +
                "currentBuilding=" + currentBuilding +
                ", position=" + position +
                ", health=" + health +
                '}';
    }
}
