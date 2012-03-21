package es.upm.dit.gsi.shanks.model.event;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sim.engine.SimState;
import sim.engine.Steppable;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.test.MyEvent;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

public class EventTest {
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
    
    
    @Test
    public void createEvent(){
        Steppable generator = new Steppable() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
                
            }
        };
        Event event = new MyEvent("MyEvent", generator);
        
        Assert.assertEquals("MyEvent", event.getName());
    }
    
    @Test
    public void createEventAndNoLaunch() throws UnsupportedNetworkElementStatusException{
        Steppable generator = new Steppable() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
                
            }
        };
        
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Event event = new MyEvent("MyEvent", generator);
        event.addChanges();
        event.changePropertiesOfNetworkElement(d);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(false, d.isGateway());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.HIGH_TEMP_STATUS));
        Assert.assertTrue(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(d.getStatus().get(MyDevice.NOK_STATUS));
    }
    
    @Test
    public void createEventAndLaunch() throws UnsupportedNetworkElementStatusException {
        Steppable generator = new Steppable() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
                
            }
        };
        
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Event event = new MyEvent("MyEvent", generator);
        event.addChanges();
        event.changePropertiesOfNetworkElement(d);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(false, d.isGateway());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.HIGH_TEMP_STATUS));
        Assert.assertTrue(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(d.getStatus().get(MyDevice.NOK_STATUS));
        
        
        event.launchEvent();
        event.changePropertiesOfNetworkElement(d);
        d.checkStatus();
        Assert.assertEquals(100, d.getProperties().get(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertFalse(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().get(MyDevice.NOK_STATUS));
    }
    
    
    @Test
    public void createEventAndNoChangeScenarioProperties() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException{
        Steppable generator = new Steppable() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
                
            }
        };
        
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties);
        Assert.assertEquals("MyScenario", s.getID());
        Assert.assertEquals(MyScenario.SUNNY, s.getCurrentStatus());
        
        Event event = new MyEvent("MyEvent", generator);
        
        event.addChanges();
        event.changePropertiesOfScenario(s);
        Assert.assertEquals("50", s.getProperties().get(MyScenario.CLOUDY_PROB));
        
        
    }
    
    @Test
    public void createEventAndChangeScenarioProperties() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException{
        Steppable generator = new Steppable() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public void step(SimState arg0) {
                
            }
        };
        
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyScenario("MyScenario", MyScenario.SUNNY, scenarioProperties);
        Assert.assertEquals("MyScenario", s.getID());
        Assert.assertEquals(MyScenario.SUNNY, s.getCurrentStatus());
        
        Event event = new MyEvent("MyEvent", generator);
        
        event.addChanges();
        event.changePropertiesOfScenario(s);    
        Assert.assertEquals("50", s.getProperties().get(MyScenario.CLOUDY_PROB));
        event.launchEvent();
        event.changePropertiesOfScenario(s); 
        Assert.assertEquals("80", s.getProperties().get(MyScenario.CLOUDY_PROB));
        
        
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

