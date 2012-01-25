/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.exception.AlreadyConnectedScenarioException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.NonGatewayDeviceException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

/**
 * @author a.carrera
 *
 */
public class ComplexScenarioTest {

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
    public void createComplexScenario()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException, AlreadyConnectedScenarioException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyComplexScenario("MyComplexScenario", MyComplexScenario.SUNNY, scenarioProperties);
        Assert.assertEquals("MyComplexScenario", s.getID());
        Assert.assertEquals(MyComplexScenario.SUNNY, s.getCurrentStatus());
    }
    


    @Test
    public void createComplexScenarioAndGetScenarios()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException, AlreadyConnectedScenarioException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ScenarioNotFoundException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        ComplexScenario s = new MyComplexScenario("MyComplexScenario", MyComplexScenario.SUNNY, scenarioProperties);
        Assert.assertEquals("MyComplexScenario", s.getID());
        Assert.assertEquals(MyComplexScenario.SUNNY, s.getCurrentStatus());
        Scenario s1 = s.getScenario("Scenario1");
        Scenario s2 = s.getScenario("Scenario2");
        Assert.assertEquals("Scenario1", s1.getID());
        Assert.assertEquals("Scenario2", s2.getID());
    }

}
