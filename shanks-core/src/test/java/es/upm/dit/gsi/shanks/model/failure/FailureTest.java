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
package es.upm.dit.gsi.shanks.model.failure;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.exception.UnsupportedElementByEventException;
import es.upm.dit.gsi.shanks.model.event.failure.Failure;
import es.upm.dit.gsi.shanks.model.event.test.MyProbNetElementEvent;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTestDefinitions;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTestDevice;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTestScenario;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTestSimulation;
import es.upm.dit.gsi.shanks.model.failure.util.test.TestDeviceFailure;
import es.upm.dit.gsi.shanks.model.failure.util.test.TestScenarioFailure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

/**
 * @author a.carrera
 * 
 */
public class FailureTest {

    static Logger logger = Logger.getLogger(MyProbNetElementEvent.class.getName());
    /**
     * @throws Exception
     */
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
    }

    /**
     * 
     */
    @Test
    public void createFailure() {
        try {
            Failure f = new Failure(FailureTestDefinitions.FAILURE_ID, null,
                    FailureTestDefinitions.FAILURE_PROB, logger) {
                @Override
                public void interactWithNE() {
                }

                @Override
                public void addPossibleAffected() {
                }

                @Override
                public void changeOtherFields()
                        throws UnsupportedNetworkElementFieldException {
                }

                @Override
                public boolean isResolved() {
                    // TODO Auto-generated method stub
                    return false;
                }
            };
            Assert.assertTrue(f.getID().contains(
                    FailureTestDefinitions.FAILURE_ID));
            Assert.assertEquals(FailureTestDefinitions.FAILURE_PROB,
                    f.getProb());
            Assert.assertNull(f.getLauncher());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void addingPossibleAffectedElementsToFailure() {

        // Adding affected elements one by one.
        try {

            Failure f = new Failure(FailureTestDefinitions.FAILURE_ID, null,
                    FailureTestDefinitions.FAILURE_PROB, logger) {
                @Override
                public void interactWithNE() {
                }

                @Override
                public void addPossibleAffected() {
                }

                @Override
                public void changeOtherFields()
                        throws UnsupportedNetworkElementFieldException {
                }

                @Override
                public boolean isResolved() {
                    // TODO Auto-generated method stub
                    return false;
                }
            };

            // Adding affected elements
            f.addPossibleAffectedElementProperty(FailureTestDevice.class,
                    FailureTestDevice.FUNDATIONS_PROPERTY,
                    FailureTestDefinitions.NOK);
            f.addPossibleAffectedElementState(FailureTestDevice.class,
                    FailureTestDevice.NOK_STATUS, true);
            f.addPossibleAffectedElementField(FailureTestDevice.class,
                    FailureTestDevice.USER_FIELD, "anything");
            f.addPossibleAffectedElementProperty(MyDevice.class,
                    MyDevice.TEMPERATURE_PROPERTY, 0.0);
            // Trying to add a duplicated possible affected element.
            f.addPossibleAffectedElementField(FailureTestDevice.class,
                    FailureTestDevice.FUNDATIONS_PROPERTY,
                    FailureTestDefinitions.OK);

            HashMap<Class<? extends NetworkElement>, HashMap<String, Object>> possibleAffectedElements = f
                    .getPossibleAffectedElements();
            Assert.assertTrue(possibleAffectedElements.size() == 2);
            Assert.assertTrue(possibleAffectedElements.get(
                    FailureTestDevice.class).size() == 3);
            Assert.assertTrue(possibleAffectedElements.get(MyDevice.class)
                    .size() == 1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        // Adding affected elements by groups.
        try {

            Failure f = new Failure(FailureTestDefinitions.FAILURE_ID, null,
                    FailureTestDefinitions.FAILURE_PROB, logger) {
                @Override
                public void interactWithNE() {
                }

                @Override
                public void addPossibleAffected() {
                }

                @Override
                public void changeOtherFields()
                        throws UnsupportedNetworkElementFieldException {
                }

                @Override
                public boolean isResolved() {
                    // TODO Auto-generated method stub
                    return false;
                }
            };

            // Adding affected elements
            HashMap<String, Object> toAdd = new HashMap<String, Object>();
            toAdd.put(FailureTestDevice.FUNDATIONS_PROPERTY,
                    FailureTestDefinitions.NOK);
            toAdd.put(FailureTestDevice.NOK_STATUS, true);
            toAdd.put(FailureTestDevice.USER_FIELD, "anything");
            f.addPossibleAffectedElement(FailureTestDevice.class, toAdd);

            // Trying to add a duplicated possible affected element.
            f.addPossibleAffectedElement(FailureTestDevice.class, toAdd);

            toAdd = new HashMap<String, Object>();
            toAdd.put(MyDevice.TEMPERATURE_PROPERTY, 0.0);
            f.addPossibleAffectedElement(MyDevice.class, toAdd);

            HashMap<Class<? extends NetworkElement>, HashMap<String, Object>> possibleAffectedElements = f
                    .getPossibleAffectedElements();
            Assert.assertTrue(possibleAffectedElements.size() == 2);
            Assert.assertTrue(possibleAffectedElements.get(
                    FailureTestDevice.class).size() == 3);
            Assert.assertTrue(possibleAffectedElements.get(MyDevice.class)
                    .size() == 1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void addingPossibleAffectedScenariosToFailure() {

        // Adding affected scenarios one by one.
        try {

            Failure f = new Failure(FailureTestDefinitions.FAILURE_ID, null,
                    FailureTestDefinitions.FAILURE_PROB, logger) {
                @Override
                public void interactWithNE() {
                }

                @Override
                public void addPossibleAffected() {
                }

                @Override
                public void changeOtherFields()
                        throws UnsupportedNetworkElementFieldException {
                }

                @Override
                public boolean isResolved() {
                    // TODO Auto-generated method stub
                    return false;
                }
            };

            // Adding affected scenarios
            f.addPossibleAffectedScenarioState(FailureTestScenario.class,
                    FailureTestScenario.NOK_STATE, FailureTestDefinitions.NOK);
            f.addPossibleAffectedScenarioField(FailureTestScenario.class,
                    FailureTestScenario.USER_FIELD, "anything");
            f.addPossibleAffectedScenarioState(MyScenario.class,
                    MyScenario.CLOUDY, true);

            // Trying to add a duplicated possible affected scenario.
            f.addPossibleAffectedScenarioState(FailureTestScenario.class,
                    FailureTestScenario.NOK_STATE, FailureTestDefinitions.OK);

            HashMap<Class<? extends Scenario>, HashMap<String, Object>> possibleAffectedScenarios = f
                    .getPossibleAffectedScenarios();
            Assert.assertTrue(possibleAffectedScenarios.size() == 2);
            Assert.assertTrue(possibleAffectedScenarios.get(
                    FailureTestScenario.class).size() == 2);
            Assert.assertTrue(possibleAffectedScenarios.get(MyScenario.class)
                    .size() == 1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        // Adding affected scenarios by groups.
        try {

            Failure f = new Failure(FailureTestDefinitions.FAILURE_ID, null,
                    FailureTestDefinitions.FAILURE_PROB, logger) {
                @Override
                public void interactWithNE() {
                }

                @Override
                public void addPossibleAffected() {
                }

                @Override
                public void changeOtherFields()
                        throws UnsupportedNetworkElementFieldException {
                }

                @Override
                public boolean isResolved() {
                    // TODO Auto-generated method stub
                    return false;
                }
            };

            // Adding affected elements
            HashMap<String, Object> toAdd = new HashMap<String, Object>();
            toAdd.put(FailureTestScenario.NOK_STATE, true);
            toAdd.put(FailureTestScenario.USER_FIELD, "anything");
            f.addPossibleAffectedScenario(FailureTestScenario.class, toAdd);

            // Trying to add a duplicated possible affected element.
            f.addPossibleAffectedScenario(FailureTestScenario.class, toAdd);

            toAdd = new HashMap<String, Object>();
            toAdd.put(MyScenario.CLOUDY, true);
            f.addPossibleAffectedScenario(MyScenario.class, toAdd);

            HashMap<Class<? extends Scenario>, HashMap<String, Object>> possibleAffectedScenarios = f
                    .getPossibleAffectedScenarios();
            Assert.assertTrue(possibleAffectedScenarios.size() == 2);
            Assert.assertTrue(possibleAffectedScenarios.get(
                    FailureTestScenario.class).size() == 2);
            Assert.assertTrue(possibleAffectedScenarios.get(MyScenario.class)
                    .size() == 1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     * @throws UnsupportedElementByEventException
     */
    @Test
    public void launchFailuresManually() {
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
            Properties configProperties = new Properties();
            configProperties.put(FailureTestSimulation.CONFIGURATION, "" + 1);
            FailureTestSimulation sim = new FailureTestSimulation(
                    System.currentTimeMillis(), FailureTestScenario.class,
                    FailureTestDefinitions.SCENARIO_ID,
                    FailureTestScenario.TEST_STATE, scenarioProperties,
                    configProperties);
            
            Device d = new FailureTestDevice(FailureTestDefinitions.DEVICE_ID, FailureTestDevice.OK_STATUS, false, logger);
            Failure deviceFailure = new TestDeviceFailure(
                    FailureTestDefinitions.FAILURE_ID,
                    sim.getNotificationManager(),
                    FailureTestDefinitions.FAILURE_HIGH_PROB, logger);
            deviceFailure.addAffectedElement(d);
            
            Scenario sc = new FailureTestScenario(FailureTestDefinitions.SCENARIO_ID, FailureTestScenario.OK_STATE, scenarioProperties, logger);
            Failure scenarioFailure = new TestScenarioFailure(
                    FailureTestDefinitions.FAILURE_ID,
                    sim.getNotificationManager(),
                    FailureTestDefinitions.FAILURE_HIGH_PROB, logger);
            scenarioFailure.addAffectedScenario(sc);
            
            deviceFailure.launchEvent();
            scenarioFailure.launchEvent();
            
            Assert.assertFalse(d.getStatus().get(FailureTestDevice.OFF_STATUS));
            Assert.assertFalse(d.getStatus().get(FailureTestDevice.OK_STATUS));
            Assert.assertFalse(d.getStatus().get(FailureTestDevice.NOK_STATUS));
            Assert.assertTrue(d.getStatus().get(FailureTestDevice.UNKOWN_STATUS));
            Assert.assertEquals(FailureTestScenario.NOK_STATE, sc.getCurrentStatus());
            
            
            // sim.start();
            // do
            // if (!sim.schedule.step(sim))
            // break;
            // while (sim.schedule.getSteps() <
            // FailureTestDefinitions.IN_ITERATIONS);
            // sim.finish();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
