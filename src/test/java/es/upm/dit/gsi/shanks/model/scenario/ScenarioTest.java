/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.scenario;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

/**
 * @author a.carrera
 *
 */
public class ScenarioTest {

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
    public void createScenario() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException {
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY);
        Assert.assertEquals("MyScenario", s.getID());
        Assert.assertEquals(MyScenario.SUNNY, s.getCurrentStatus());
    }

    @Test
    public void createScenarioAndChangeState() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException {
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY);
        s.setCurrentStatus(MyScenario.CLOUDY);
        Assert.assertEquals(MyScenario.CLOUDY, s.getCurrentStatus());
    }

    @Test
    public void createScenarioAndChangeWrongState() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException {
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY);
        boolean catched = false;
        try {
            s.setCurrentStatus("WrongStatus");   
        } catch (UnsupportedScenarioStatusException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void createScenarioWithWrongState() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, DuplicatedIDException {
        boolean catched = false;
        try {
            new MyScenario("MyScenario", "WrongStatus");   
        } catch (UnsupportedScenarioStatusException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void createScenarioAndGenerateFailures() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException {
        Scenario s = new MyScenario("MyScenario", MyScenario.CLOUDY);
        s.generateFailures();
        //TODO aquí falta comprobar
    }
    

}
