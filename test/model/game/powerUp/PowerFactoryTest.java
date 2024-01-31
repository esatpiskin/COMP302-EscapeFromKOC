package model.game.powerUp;

import model.game.data.Position;
import model.game.data.Size;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PowerFactoryTest {

    private PowerFactory factory;
    private hint hintPower;
    private extraLife extraLifePower;

    private Random rand;

    public void setUp() {
        factory = PowerFactory.getInstance();
        rand = new Random();
    }


    /*This test case tests the performPower method with the powerType value "extraTime".
    It creates a new Power object with the conditionPowerUp power type and passes it, along with the "extraTime" value, to the performPower method.
    It then uses the assertEquals method to verify that the powerType of the returned Power object is "extraTime".
     *
     *
     */
    @Test
    public void testPerformPower_extraTime() {
        factory = PowerFactory.getInstance();
        Power powerObj = new Power(new Size(10, 10),new Position(5, 5),null,"extraTime");
        Power power = factory.performPower("extraTime", powerObj);
        System.out.println(power.getPowerType());  // Print out power.getPowerType()
        assertEquals("extraTime", power.getPowerType());
    }

    @Test
    public void testPerformPower_hint() {
        factory = PowerFactory.getInstance();
        Power powerObj = new Power(new Size(10, 10),new Position(5, 5),null,"hint");
        Power power = factory.performPower("hint", powerObj);
        System.out.println(power.getPowerType());  // Print out power.getPowerType()
        assertEquals("hint", power.getPowerType());
    }
    @Test
    public void testPerformPower_extraLife() {
        factory = PowerFactory.getInstance();
        Power powerObj = new Power(new Size(10, 10),new Position(5, 5),null,"extraLife");
        Power power = factory.performPower("extraLife", powerObj);
        System.out.println(power.getPowerType());  // Print out power.getPowerType()
        assertEquals("extraLife", power.getPowerType());
    }

    @Test
    public void testPerformPower_bottle() {
        factory = PowerFactory.getInstance();
        Power powerObj = new Power(new Size(10, 10),new Position(5, 5),null,"bottle");
        Power power = factory.performPower("bottle", powerObj);
        System.out.println(power.getPowerType());  // Print out power.getPowerType()
        assertEquals("bottle", power.getPowerType());
    }
    @Test
    public void testPerformPower_() {
        factory = PowerFactory.getInstance();
        Power powerObj = new Power(new Size(10, 10),new Position(5, 5),null,"extraLife");
        Power power = factory.performPower("extraLife", powerObj);
        System.out.println(power.getPowerType());  // Print out power.getPowerType()
        assertEquals("extraLife", power.getPowerType());
    }



    /*This test case tests the performPower method with a null powerObj parameter.
     *It passes the "hint" value for the powerType parameter and a null value for the powerObj parameter to the performPower method,
     *and then uses the assertNull method to verify that the method returns null.
     */

    @Test
    @Disabled
    public void testPerformPower_nullPowerObj() {
        Power power = factory.performPower("hint", null);
        assertNull(power);
    }



    @Test
    @Disabled
    public void testCreatePowerup() {
        Random rand = new Random();
        int type = rand.nextInt(4);
        Power power = factory.createPowerup(type);
        assertEquals(type, power.getType());
    }

    @Test
    @Disabled
    public void testCreatePowerup_noMorePowerups() {
        factory.numberOfPowerupsMap.put(0, 0);
        factory.numberOfPowerupsMap.put(1, 0);
        factory.numberOfPowerupsMap.put(2, 0);
        factory.numberOfPowerupsMap.put(3, 0);
        Power power = factory.createPowerup(0);
        assertNull(power);
    }

    @Test
    public void testGetInstance() {
        PowerFactory factory1 = PowerFactory.getInstance();
        PowerFactory factory2 = PowerFactory.getInstance();
        assertEquals(factory1, factory2);
    }


}