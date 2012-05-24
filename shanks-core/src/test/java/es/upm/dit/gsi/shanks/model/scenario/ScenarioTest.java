/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.scenario;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.exception.UnsupportedElementByEventException;
import es.upm.dit.gsi.shanks.model.event.failiure.exception.NoCombinationForFailureException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

/**
 * @author a.carrera
 *
 */

public class ScenarioTest {

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
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     */
    @Test
    public void createScenario()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties);
        Assert.assertEquals("MyScenario", s.getID());
        Assert.assertEquals(MyScenario.SUNNY, s.getCurrentStatus());
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     */
    @Test
    public void createScenarioAndChangeState()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties);
        s.setCurrentStatus(MyScenario.CLOUDY);
        Assert.assertEquals(MyScenario.CLOUDY, s.getCurrentStatus());
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     */
    @Test
    public void createScenarioAndChangeWrongState()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties);
        boolean catched = false;
        try {
            s.setCurrentStatus("WrongStatus");
        } catch (UnsupportedScenarioStatusException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     * @throws DuplicatedIDException
     */
    @Test
    public void createScenarioWithWrongState()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException, DuplicatedIDException {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
            new MyScenario("MyScenario", "WrongStatus", scenarioProperties);
        } catch (UnsupportedScenarioStatusException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     * @throws NoCombinationForFailureException
     * @throws UnsupportedElementByEventException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void createScenarioAndNoGenerateFailures()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NoCombinationForFailureException, InstantiationException, IllegalAccessException {

        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.CLOUDY, scenarioProperties);
        Assert.assertTrue(s.getCurrentFailures().size() == 0);
    }
 
}
