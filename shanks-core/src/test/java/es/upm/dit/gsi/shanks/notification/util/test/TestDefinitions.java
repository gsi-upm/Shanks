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
package es.upm.dit.gsi.shanks.notification.util.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.event.Event;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class TestDefinitions {

    public static final String NOTIFICATION_ID = "Notification#";
    public static final long NOTIFICATION_WHEN = 5;
    public static final Object NOTIFICATION_SOURCE = new Object();
    public static final int NOTIFICATIONS_SIZE = 10;
    
    public static final String IN_INTERACTION = Event.class.getName();
    public static final List<Object> IN_TARGET = new ArrayList<Object>();
    public static final long IN_ITERATIONS = 50;
    
    public static final String VN_ELEMENT_ID = "ElementID";
    public static final Object VN_VALUE = new Object();
    public static final long VN_ITERATIONS = 200;

    public static final int SOURCES_SIZE = 5;
    public static final int TARGETS_SIZE = 5;
    public static final int ELEMENT_ID_SIZE = 5;
    
    public static final String EVENT_ID = "testEvent#";
    public static final String AGENT_ID = "testAgent#";
    public static final String DEVICE_ID = "testDevice#";

    public static final String SPEED_ID = "speed";
    public static final String AGENT_STATE_ID = "agentState";
    public static final String BOOLEAN_ID = "boolean";
    public static final double EVENT_PROBABILITY = 0.5;
    public static final int EVENT_PERIOD = 10;
    
    /**
     * Accessory method that creates a test simulation. 
     * @return
     *          a test simulation. 
     */
    public static ShanksSimulation getSimulation(int conf) throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(TestSimulation.CONFIGURATION, ""+conf);
        TestSimulation sim = new TestSimulation(System.currentTimeMillis(), 
                TestScenario.class, "TestScenario", TestScenario.TEST_STATE, 
                scenarioProperties, configProperties);
        return sim;
    }

}
