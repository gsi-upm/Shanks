/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

/**
 * @author a.carrera
 * 
 */
public class ShanksSimulationTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));

    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void ShanksSimulationWithoutGUI() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.NO_GUI);
            
            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "0");
            MyShanksSimulation sim = new MyShanksSimulation(System.currentTimeMillis(), MyScenario.class, "MyScenario", MyScenario.SUNNY, scenarioProperties, configProperties);
            sim.start();
            do
                if (!sim.schedule.step(sim))
                    break;
            while (sim.schedule.getSteps() < 2001);
            sim.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);

    }

    @Test
    public void ShanksSimulationResolvingProblemsWithoutGUI() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.NO_GUI);
            
            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            MyShanksSimulation sim = new MyShanksSimulation(System.currentTimeMillis(), MyScenario.class, "MyScenario", MyScenario.SUNNY, scenarioProperties, configProperties);
            sim.start();
            do
                if (!sim.schedule.step(sim))
                    break;
            while (sim.schedule.getSteps() < 2001);
            sim.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);

        // TOTEST implement test that count the number of generated tests or the
        // number of resolved tests
    }

    @Test
    public void ShanksSimulationWith2DGUI() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_2D);
            
            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "0");
            MyShanksSimulation sim = new MyShanksSimulation(System.currentTimeMillis(), MyScenario.class, "MyScenario", MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
            gui.start();
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 2001);
            gui.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);

    }

    @Test
    public void ShanksSimulationResolvingProblemsWith2DGUI() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_2D);
            
            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            MyShanksSimulation sim = new MyShanksSimulation(System.currentTimeMillis(), MyScenario.class, "MyScenario", MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
            gui.start();
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 2001);
            gui.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);

    }

    @Test
    public void ShanksSimulationWith3DGUI() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_3D);
            
            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "0");
            MyShanksSimulation sim = new MyShanksSimulation(System.currentTimeMillis(), MyScenario.class, "MyScenario", MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
            gui.start();
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 2001);
            gui.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);

    }

    @Test
    public void ShanksSimulationResolvingProblemsWith3DGUI() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_3D);
            
            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            MyShanksSimulation sim = new MyShanksSimulation(System.currentTimeMillis(), MyScenario.class, "MyScenario", MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
            gui.start();
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 2001);
            gui.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);

    }

}
