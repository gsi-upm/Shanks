package es.upm.dit.gsi.shanks.agent.capability.creation.test;

import java.util.Properties;

import junit.framework.Assert;

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

public class ShanksAgentCreationCapabilityTest {

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
            MyShanksSimulation sim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
            gui.start();
            sim.start();

            MySimpleShanksAgent creado = new MySimpleShanksAgent(
                    "agenteCreado", 1, 1);

            CreationShanksAgentCapability.addNewAgent(sim, creado);

            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 20);
            
            ShanksAgent devuelto = sim.getAgent("agenteCreado");
            
            Assert.assertEquals(creado, devuelto);
            sim.finish();
            gui.finish();
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
            MyShanksSimulation sim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
            gui.start();
            sim.start();

            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 20);
            
            String agentID = "AgenteCreado";
            CounterShanksAgent creado = new CounterShanksAgent(agentID);

            CreationShanksAgentCapability.addNewAgent(sim, creado);
            
            int initialcounter = sim.getCounter();
            
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 20);
            
            int finalCounter = sim.getCounter();
           
            Assert.assertTrue(initialcounter != finalCounter);
            
            sim.finish();
            gui.finish();
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
            MyShanksSimulation sim = new MyShanksSimulation(
                    System.currentTimeMillis(), MyScenario.class, "MyScenario",
                    MyScenario.SUNNY, scenarioProperties, configProperties);
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
            gui.start();
            sim.start();

            MySimpleShanksAgent creado = new MySimpleShanksAgent(
                    "agenteCreado", 1, 1);

            CreationShanksAgentCapability.addNewAgent(sim, creado);

            CreationShanksAgentCapability.removeAgent(sim, "agenteCreado");
            
            do
                if (!gui.getSimulation().schedule.step(sim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 20);

            ShanksAgent devuelto = sim.getAgent("agenteCreado"); // This should
                                                                 // throw an Exception

            Assert.assertNull(devuelto);
            sim.finish();
            gui.finish();
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

            String createdAgentID = "CreatedAgent";
            CounterShanksAgent createdAgent = new CounterShanksAgent(createdAgentID);
            
            testSim.registerShanksAgent(createdAgent);
            
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(testSim);
            gui.start();
            testSim.start();

            do
                if (!gui.getSimulation().schedule.step(testSim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 20);
            
            // String removeID = sim.getAgents().iterator().next().getID(); //
            // The ID of one of the agents, to be removed
            try {
                CreationShanksAgentCapability.removeAgent(testSim, createdAgentID);
            } catch (UnkownAgentException e) {
                // Cannot find the agent while removing it. Something is wrong.
                testSim.logger
                        .warning("Cannot find the agent while removing it. Something is wrong.");
                Assert.fail();
            }

            int afterRemoval = testSim.getCounter();
            testSim.logger.info("Counter after removal: " + afterRemoval);
            
            
            do
                if (!gui.getSimulation().schedule.step(testSim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 40);
           
            int current = testSim.getCounter(); 
            testSim.logger.info("Final counter: " + current);
            
            testSim.finish();
            gui.finish();
            
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
            CounterShanksAgent createdAgent = new CounterShanksAgent(createdAgentID);
            
            MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(testSim);
            gui.start();
            testSim.start();

            do
                if (!gui.getSimulation().schedule.step(testSim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 20);
            
            CreationShanksAgentCapability.addNewAgent(testSim, createdAgent);
            
            do
                if (!gui.getSimulation().schedule.step(testSim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 20);
            
            // String removeID = sim.getAgents().iterator().next().getID(); //
            // The ID of one of the agents, to be removed
            try {
                CreationShanksAgentCapability.removeAgent(testSim, createdAgentID);
            } catch (UnkownAgentException e) {
                // Cannot find the agent while removing it. Something is wrong.
                testSim.logger
                        .warning("Cannot find the agent while removing it. Something is wrong.");
                Assert.fail();
            }
            
            do
                if (!gui.getSimulation().schedule.step(testSim))
                    break;
            while (gui.getSimulation().schedule.getSteps() < 40);
            
            ShanksAgent devuelto = testSim.getAgent(createdAgentID); // This should
                                                               // throw an
                                                               // Exception
            
            Assert.assertNull(devuelto);
            testSim.finish();
            gui.finish();
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
