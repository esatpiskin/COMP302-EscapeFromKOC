package model.game.powerUp;

import model.game.data.Position;
import model.game.data.Size;

import java.util.HashMap;
import java.util.Random;

public class PowerFactory {


    private static PowerFactory factory;

    public static HashMap<Integer, Integer> numberOfPowerupsMap = new HashMap<>();
    private PowerFactory(){

        
    }

    public static PowerFactory getInstance(){
        if(factory == null){
            factory = new PowerFactory();
        }
        return factory;
    }


    public Power performPower(String powerType , Power powerObj){

        if(powerObj != null && powerObj.getPowerType().equals("conditionPowerUp")){
            switch(powerType){
                case "extraTime":
                    return new extraTime(new Size(10,10), new Position((int) (powerObj.getPosition().x() + (powerObj.getSize().x() - 5)/2), (int) (powerObj.getPosition().y() +(powerObj.getSize().y()-5)/2)) , "null","extraTime") ;
                case "hint":
                    return new hint(new Size(10,10), new Position((int) (powerObj.getPosition().x() + (powerObj.getSize().x() - 5)/2), (int) (powerObj.getPosition().y() +(powerObj.getSize().y()-5)/2)) , "null","hint") ;
                case "vest":
                    return new vest(new Size(10,10), new Position((int) (powerObj.getPosition().x() + (powerObj.getSize().x() - 5)/2), (int) (powerObj.getPosition().y() +(powerObj.getSize().y()-5)/2)) , "null","hint") ;
                case "bottle":
                    return new bottle(new Size(10,10), new Position((int) (powerObj.getPosition().x() + (powerObj.getSize().x() - 5)/2), (int) (powerObj.getPosition().y() +(powerObj.getSize().y()-5)/2)) , "null","hint") ;
                case "extraLife":
                    return new extraLife(new Size(10,10), new Position((int) (powerObj.getPosition().x() + (powerObj.getSize().x() - 5)/2), (int) (powerObj.getPosition().y() +(powerObj.getSize().y()-5)/2)) , "null","hint") ;
            }
        }
        return powerObj;
    }


    /* m is a dummy parameter to ehance the
     * condition.
     */

    public Power createPowerup(int m) {
        Random rand = new Random();
        int type = rand.nextInt(4);

        if (!noMorePowerups()) {
            while (numberOfPowerupsMap.get(type)==0) {
                type = rand.nextInt(4);
            }
            return createPowerup(type);
        }else {
            return null;
        }
    }


    private boolean noMorePowerups() {
        for(Integer numberOfPowerups : numberOfPowerupsMap.values()) {
            if(numberOfPowerups!=0) return false;
        }
        return true;
    }

}
