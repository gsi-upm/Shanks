/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
            String[] args = new String[1];
            args[0] = new String("0");
            MyShanksSimulation.main(args);
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
            String[] args = new String[1];
            args[0] = new String("1");
            MyShanksSimulation.main(args);
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
            String[] args = new String[1];
            args[0] = new String("0");
            MyShanksSimulation sim = new MyShanksSimulation(
                    System.currentTimeMillis(), args);
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
            String[] args = new String[1];
            args[0] = new String("1");
            MyShanksSimulation sim = new MyShanksSimulation(
                    System.currentTimeMillis(), args);
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

}
