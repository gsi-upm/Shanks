/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.scenario;

import java.util.Properties;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.exception.ShanksException;
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

    static Logger logger = Logger.getLogger(ScenarioTest.class.getName());
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
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties, logger);
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
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties, logger);
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
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties, logger);
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
            throws ShanksException {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
            new MyScenario("MyScenario", "WrongStatus", scenarioProperties, logger);
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
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.CLOUDY, scenarioProperties, logger);
        Assert.assertTrue(s.getCurrentFailures().size() == 0);
    }
 
}
