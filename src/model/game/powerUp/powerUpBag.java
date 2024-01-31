package model.game.powerUp;

import model.game.Powerups;

import java.util.List;

public class powerUpBag {
    private List<Power> powerUps;

    public void addPowerUp(Power powerUp) {
        powerUps.add(powerUp);
    }

    public void usePowerUp(int index){
        Power power = powerUps.get(index);

        if (power instanceof Ipickupable) {
            ((Ipickupable) power).onPickup();
        } else if (power instanceof IActivatable) {
            ((IActivatable) power).activate();
        }

    }
    }
