/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.movement.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    private MyShanksSimulation sim = null;
    private MyShanksSimulation2DGUI gui2D = null;
    private MyShanksSimulation3DGUI gui3D = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));

    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        try {

            if (gui2D != null) {
                gui2D.quit();
                gui2D.finish();
                gui2D = null;
            }
            if (gui3D != null) {
                gui3D.quit();
                gui3D.finish();
                gui3D = null;
            }
            if (sim != null) {
                sim.finish();
                sim = null;
            }
        } catch (Exception e) {
            logger.warning("Exception trying clean the test. Message: "
                    + e.getMessage());
        }
    }
    
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
            sim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            gui2D = new MyShanksSimulation2DGUI(sim);
            gui2D.start();
            do
                if (!gui2D.getSimulation().schedule.step(sim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 2001);
            
            MySimpleShanksAgent agent = (MySimpleShanksAgent) sim.getAgent("simpleAgent1");
            Location location = agent.getCurrentLocation();
            Double2D loc2D = location.getLocation2D();
            Assert.assertNotSame(50, loc2D.x);
            Assert.assertNotSame(50, loc2D.y);            
            gui2D.finish();
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
            sim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            gui3D = new MyShanksSimulation3DGUI(sim);
            gui3D.start();
            do
                if (!gui3D.getSimulation().schedule.step(sim))
                    break;
            while (gui3D.getSimulation().schedule.getSteps() < 2001);
            
            MySimpleShanksAgent agent = (MySimpleShanksAgent) sim.getAgent("simpleAgent1");
            Location location = agent.getCurrentLocation();
            Double3D loc3D = location.getLocation3D();
            Assert.assertNotSame(50, loc3D.x);
            Assert.assertNotSame(50, loc3D.y);
            Assert.assertNotSame(50, loc3D.z);            
            gui3D.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);
    }
}
