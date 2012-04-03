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
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;

public class TestDefinitions {

    public static final String IN_ID = "Notification#";
    public static final long IN_WHEN = 5;
    public static final Object IN_SOURCE = new Object();
    public static final String IN_INTERACTION = Event.class.getName();
    public static final List<Object> IN_TARGET = new ArrayList();
    
    public static final int NOTIFICATIONS_SIZE = 10;
    
    public static final String EVENT_ID = "testEvent";
    
    /**
     * Accessory method that creates a test simulation. 
     * @return
     *          a test simulation. 
     */
    public static ShanksSimulation getSimulation() throws SecurityException,
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
        configProperties.put(MyShanksSimulation.CONFIGURATION, "0");
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), MyScenario.class, "MyScenario",
                MyScenario.SUNNY, scenarioProperties, configProperties);
        return sim;
    }

}
