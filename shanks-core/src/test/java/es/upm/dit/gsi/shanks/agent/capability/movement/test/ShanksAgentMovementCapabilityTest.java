/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.movement.test;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

import sim.util.Double2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.test.MySimpleShanksAgent;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation3DGUI;

/**
 * @author a.carrera
 * 
 */
public class ShanksAgentMovementCapabilityTest {

    /**
     * 
     */
    @Test
    public void MovementCapabilityBasic2DTest() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI,
                    Scenario.SIMULATION_2D);

            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            MyShanksSimulation sim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
            gui.start();
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 2001);
            
            MySimpleShanksAgent agent = (MySimpleShanksAgent) sim.getAgent("simpleAgent1");
            Location location = agent.getCurrentLocation();
            Double2D loc2D = location.getLocation2D();
            Assert.assertNotSame(50, loc2D.x);
            Assert.assertNotSame(50, loc2D.y);            
            gui.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);
    }

    /**
     * 
     */
    @Test
    public void MovementCapabilityBasic3DTest() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI,
                    Scenario.SIMULATION_3D);

            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            MyShanksSimulation sim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
            gui.start();
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 2001);
            
            MySimpleShanksAgent agent = (MySimpleShanksAgent) sim.getAgent("simpleAgent1");
            Location location = agent.getCurrentLocation();
            Double3D loc3D = location.getLocation3D();
            Assert.assertNotSame(50, loc3D.x);
            Assert.assertNotSame(50, loc3D.y);
            Assert.assertNotSame(50, loc3D.z);            
            gui.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);
    }
}
