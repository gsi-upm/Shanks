/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.perception.test;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

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
public class ShanksAgentPerceptionCapabilityTest {

    /**
     * 
     */
    @Test
    public void PerceptionCapabilityBasic2DTest() {
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
            Assert.assertTrue(agent.hasBeenNearToSomething());
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
    public void PerceptionCapabilityBasic3DTest() {
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
            
//            MySimpleShanksAgent agent = (MySimpleShanksAgent) sim.getAgent("simpleAgent1");
//            Assert.assertTrue(agent.hasBeenNearToSomething());  
            gui.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);
    }
}
