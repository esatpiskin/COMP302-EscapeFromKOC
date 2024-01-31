package model.game;

import model.game.object.Player;

import java.util.Random;

public class Powerups {
private int powerUps;
private int power;


public Powerups(boolean havePowerUp){
    if(havePowerUp){
        this.setPowerUp(getRandomPowerUp());
    }
}
  public int getRandomPowerUp(){
    Random rand = new Random();
    return rand.nextInt(5)+1;
  }

    public String getAbilityText()
    {
        if (getPower() == 1) {
            return "\"Extre time Power-Up\"";
        }
        else if (getPower() == 2) {
            return "\"Hint\"";
        }
        else if (getPower() == 3) {
            return "\"Protection Vest\"";
        }
        else if (getPower() == 4) {
            return "\"Plastic Bottle\"";
        }
        else if (getPower() == 5) {
            // TODO: this absolutely does not belong here
            //player.reduceHealth();
            return "\"Extra Life\"";
        }
        return "";
    }

    public int getPower() {
        return power;
    }

    public void setPowerUp(int ability) {
        this.power = ability;
    }



}
