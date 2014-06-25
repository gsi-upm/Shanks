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
package es.upm.dit.gsi.shanks.agent.capability.creation.test;

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

import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.creation.CreationShanksAgentCapability;
import es.upm.dit.gsi.shanks.agent.exception.UnkownAgentException;
import es.upm.dit.gsi.shanks.agent.test.CounterShanksAgent;
import es.upm.dit.gsi.shanks.agent.test.MySimpleShanksAgent;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation3DGUI;

public class ShanksAgentCreationCapabilityTest {

    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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

    @Test
    public void AgentCreationTest() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI,
                    Scenario.SIMULATION_2D);

            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            sim = new MyShanksSimulation(System.currentTimeMillis(),
                    MyScenario.class, "MyScenario", MyScenario.SUNNY,
                    scenarioProperties, configProperties);
            gui2D = new MyShanksSimulation2DGUI(sim);
            gui2D.start();
            sim.start();

            MySimpleShanksAgent creado = new MySimpleShanksAgent(
                    "agenteCreado", 1, 1, sim.getLogger());

            CreationShanksAgentCapability.addNewAgent(sim, creado);

            do
                if (!gui2D.getSimulation().schedule.step(sim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 20);

            ShanksAgent devuelto = sim.getAgent("agenteCreado");

            Assert.assertEquals(creado, devuelto);
            sim.finish();
            gui2D.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);
    }

    @Test
    public void AgentCreationTestCheckingSteps() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI,
                    Scenario.SIMULATION_2D);

            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            sim = new MyShanksSimulation(System.currentTimeMillis(),
                    MyScenario.class, "MyScenario", MyScenario.SUNNY,
                    scenarioProperties, configProperties);
            gui2D = new MyShanksSimulation2DGUI(sim);
            gui2D.start();
            sim.start();

            do
                if (!gui2D.getSimulation().schedule.step(sim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 20);

            String agentID = "AgenteCreado";
            CounterShanksAgent creado = new CounterShanksAgent(agentID, sim.getLogger());

            CreationShanksAgentCapability.addNewAgent(sim, creado);

            int initialcounter = sim.getCounter();

            do
                if (!gui2D.getSimulation().schedule.step(sim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 20);

            int finalCounter = sim.getCounter();

            Assert.assertTrue(initialcounter != finalCounter);

            sim.finish();
            gui2D.finish();
        } catch (Exception e) {
            catched = true;
            e.printStackTrace();
        }
        Assert.assertFalse(catched);
    }

    @Test
    public void AgentRemovalTest() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI,
                    Scenario.SIMULATION_2D);

            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            sim = new MyShanksSimulation(System.currentTimeMillis(),
                    MyScenario.class, "MyScenario", MyScenario.SUNNY,
                    scenarioProperties, configProperties);
            gui2D = new MyShanksSimulation2DGUI(sim);
            gui2D.start();
            sim.start();

            MySimpleShanksAgent creado = new MySimpleShanksAgent(
                    "agenteCreado", 1, 1, sim.getLogger());

            CreationShanksAgentCapability.addNewAgent(sim, creado);

            CreationShanksAgentCapability.removeAgent(sim, "agenteCreado");

            do
                if (!gui2D.getSimulation().schedule.step(sim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 20);

            ShanksAgent devuelto = sim.getAgent("agenteCreado"); // This should
                                                                 // throw an
                                                                 // Exception

            Assert.assertNull(devuelto);
            sim.finish();
            gui2D.finish();
        } catch (Exception e) {
            if (!(e instanceof UnkownAgentException)) {
                catched = true;
                e.printStackTrace();
            } else {
                // I do expect an UnkownAgentException, since I did remove the
                // agent.
            }
        }
        Assert.assertFalse(catched);
    }

    @Test
    public void AgentRemovalCheckingSteps() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI,
                    Scenario.SIMULATION_2D);

            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            MyShanksSimulation testSim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            // Creates an agent with a counter

            Logger logger = testSim.getLogger();
            String createdAgentID = "CreatedAgent";
            CounterShanksAgent createdAgent = new CounterShanksAgent(
                    createdAgentID, logger);

            testSim.registerShanksAgent(createdAgent);

            gui2D = new MyShanksSimulation2DGUI(testSim);
            gui2D.start();
            testSim.start();

            do
                if (!gui2D.getSimulation().schedule.step(testSim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 20);

            // String removeID = sim.getAgents().iterator().next().getID(); //
            // The ID of one of the agents, to be removed
            try {
                CreationShanksAgentCapability.removeAgent(testSim,
                        createdAgentID);
            } catch (UnkownAgentException e) {
                // Cannot find the agent while removing it. Something is wrong.
                testSim.logger
                        .warning("Cannot find the agent while removing it. Something is wrong.");
                Assert.fail();
            }

            int afterRemoval = testSim.getCounter();
            testSim.logger.info("Counter after removal: " + afterRemoval);

            do
                if (!gui2D.getSimulation().schedule.step(testSim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 40);

            int current = testSim.getCounter();
            testSim.logger.info("Final counter: " + current);

            testSim.finish();
            gui2D.finish();

            Assert.assertEquals(afterRemoval, current);

        } catch (Exception e) {
            if (!(e instanceof UnkownAgentException)) {
                catched = true;
                e.printStackTrace();
                Assert.fail();
            } else {
                // I do expect an UnkownAgentException, since I did remove the
                // agent.
            }
        }
        Assert.assertFalse(catched);
    }

    @Test
    public void AgentRemovalAfterStartedCheckingSteps() {
        boolean catched = false;
        try {
            Properties scenarioProperties = new Properties();
            scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
            scenarioProperties.put(Scenario.SIMULATION_GUI,
                    Scenario.SIMULATION_2D);

            Properties configProperties = new Properties();
            configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
            MyShanksSimulation testSim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            // Creates an agent with a counter

            String createdAgentID = "CreatedAgent";
            CounterShanksAgent createdAgent = new CounterShanksAgent(
                    createdAgentID, testSim.getLogger());

            gui2D = new MyShanksSimulation2DGUI(testSim);
            gui2D.start();
            testSim.start();

            do
                if (!gui2D.getSimulation().schedule.step(testSim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 20);

            CreationShanksAgentCapability.addNewAgent(testSim, createdAgent);

            do
                if (!gui2D.getSimulation().schedule.step(testSim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 20);

            // String removeID = sim.getAgents().iterator().next().getID(); //
            // The ID of one of the agents, to be removed
            try {
                CreationShanksAgentCapability.removeAgent(testSim,
                        createdAgentID);
            } catch (UnkownAgentException e) {
                // Cannot find the agent while removing it. Something is wrong.
                testSim.logger
                        .warning("Cannot find the agent while removing it. Something is wrong.");
                Assert.fail();
            }

            do
                if (!gui2D.getSimulation().schedule.step(testSim))
                    break;
            while (gui2D.getSimulation().schedule.getSteps() < 40);

            ShanksAgent devuelto = testSim.getAgent(createdAgentID); // This
                                                                     // should
            // throw an
            // Exception

            Assert.assertNull(devuelto);
            testSim.finish();
            gui2D.finish();
        } catch (Exception e) {
            if (!(e instanceof UnkownAgentException)) {
                catched = true;
                e.printStackTrace();
                Assert.fail();
            } else {
                // I do expect an UnkownAgentException, since I did remove the
                // agent.
            }
        }
        Assert.assertFalse(catched);
    }
}
