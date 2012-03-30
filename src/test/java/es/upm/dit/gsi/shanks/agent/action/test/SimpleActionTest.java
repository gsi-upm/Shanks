package es.upm.dit.gsi.shanks.agent.action.test;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.agent.test.MySimpleShanksAgent;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.Action;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;

import sim.engine.SimState;
import sim.engine.Steppable;

public class SimpleActionTest {

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
    
    @Test
    public void createAction(){
        Steppable launcher = new Steppable() {
            
            @Override
            public void step(SimState arg0) {
                // TODO Auto-generated method stub
                
            }
        };
        Action act = new MyShanksAgentAction("Action", launcher);
        
        Assert.assertEquals("Action", act.getID());
        Assert.assertEquals(launcher, act.getLauncher());
    }
    
    @Test
    public void createActionAndNoExecuteAction() throws UnsupportedNetworkElementStatusException{
        Steppable launcher = new Steppable() {
            
            @Override
            public void step(SimState arg0) {
                // TODO Auto-generated method stub
                
            }
        };
        Action act = new MyShanksAgentAction("Action", launcher);
        
        Assert.assertEquals("Action", act.getID());
        Assert.assertEquals(launcher, act.getLauncher());
        
        Device d = new MyDevice("Dev", MyDevice.NOK_STATUS, false);
        Assert.assertEquals("Dev", d.getID());
        Assert.assertTrue(d.getStatus().get(MyDevice.NOK_STATUS));
        Assert.assertFalse(d.isGateway());
        
        
        
    }
    
    @Test
    public void createActionAndExecuteAction() throws UnsupportedNetworkElementStatusException, UnsupportedScenarioStatusException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, TooManyConnectionException, DuplicatedIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException, DuplicatedAgentIDException, DuplicatedActionIDException{
        Steppable launcher = new Steppable() {
            
            @Override
            public void step(SimState arg0) {
                // TODO Auto-generated method stub
                
            }
        };
        SimpleShanksAgentAction act = new MyShanksAgentAction("Action", launcher);
        
        Assert.assertEquals("Action", act.getID());
        Assert.assertEquals(launcher, act.getLauncher());
        
        Device d = new MyDevice("Dev", MyDevice.NOK_STATUS, false);

        Assert.assertEquals("Dev", d.getID());
        Assert.assertTrue(d.getStatus().get(MyDevice.NOK_STATUS));
        Assert.assertFalse(d.isGateway()); 
        List<NetworkElement> arguments = new ArrayList<NetworkElement>();
        arguments.add(d);
        MySimpleShanksAgent agent = new MySimpleShanksAgent("Agent", 50, 10);
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI,
                Scenario.NO_GUI);

        Properties configProperties = new Properties();
        configProperties.put(MyShanksSimulation.CONFIGURATION, "0");
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), MyScenario.class, "MyScenario",
                MyScenario.SUNNY, scenarioProperties, configProperties);
        
        act.executeAction(sim, agent, arguments);
        Assert.assertTrue(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(d.getStatus().get(MyDevice.NOK_STATUS));
        Assert.assertEquals(15.5, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        
    }
}
