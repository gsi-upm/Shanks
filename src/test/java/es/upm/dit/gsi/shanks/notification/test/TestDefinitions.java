package es.upm.dit.gsi.shanks.notification.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.Event;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class TestDefinitions {

    public static final String NOTIFICATION_ID = "Notification#";
    public static final long NOTIFICATION_WHEN = 5;
    public static final Object NOTIFICATION_SOURCE = new Object();
    
    public static final String IN_INTERACTION = Event.class.getName();
    public static final List<Object> IN_TARGET = new ArrayList<Object>();
    
    public static final String EVN_ELEMENT_ID = "ElementID";
    public static final Object EVN_VALUE = new Object();
    
    public static final int NOTIFICATIONS_SIZE = 10;
    public static final int SOURCES_SIZE = 5;
    public static final int TARGETS_SIZE = 5;
    public static final int ELEMENT_ID_SIZE = 5;
    
    public static final String EVENT_ID = "testEvent#";
    public static final String AGENT_ID = "testAgent#";
    public static final String DEVICE_ID = "testDevice#";

    
    /**
     * Accessory method that creates a test simulation. 
     * @return
     *          a test simulation. 
     */
    public static ShanksSimulation getSimulation(int conf) throws SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException,
            UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, DuplicatedPortrayalIDException,
            ScenarioNotFoundException, DuplicatedAgentIDException,
            DuplicatedActionIDException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(TestSimulation.CONFIGURATION, conf);
        TestSimulation sim = new TestSimulation(System.currentTimeMillis(), 
                TestScenario.class, "TestScenario", TestScenario.TEST_STATE, 
                scenarioProperties, configProperties);
        return sim;
    }

}
