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

import jason.JasonException;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import sim.engine.Schedule;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.notification.NotificationManager;

public class TestSimulation extends ShanksSimulation {


    private Properties configuration;
    public static final String CONFIGURATION = "Configuration";

    /**
     * @param seed
     * @param scenarioClass
     * @param scenarioID
     * @param initialState
     * @param properties
     * @param configPropertiesTestSimulation
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     * @throws DuplicatedActionIDException
     * @throws DuplicatedAgentIDException
     * @throws JasonException
     */
    public TestSimulation(long seed,
            Class<? extends Scenario> scenarioClass, String scenarioID,
            String initialState, Properties properties,
            Properties configPropertiesTestSimulation)
            throws ShanksException {
        super(seed, scenarioClass, scenarioID, initialState, properties);
        this.configuration = configPropertiesTestSimulation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.ShanksSimulation#addSteppables()
     */
    @Override
    public void addSteppables() {
        int conf = (new Integer(this.configuration.getProperty(TestSimulation.CONFIGURATION))).intValue();
//        int conf = 1;
        switch (conf) {
        case 0:
            logger.fine("Nothing to do here... No more steppables");
            break;
        case 1:
            schedule.scheduleRepeating(Schedule.EPOCH, 3, new NotificationManager(this), 10);
            break;
        case 2:
            schedule.scheduleRepeating(Schedule.EPOCH, 3, new NotificationManager(this), 10);
            break;
        default:
            logger.info("No configuration for TestSimulation. Configuration 0 loaded -> default");
        }
    }

    @Override
    public void registerShanksAgents() throws ShanksException {
        TestAgent agent = new TestAgent(TestDefinitions.AGENT_ID, 0, this.getScenarioManager());
        this.registerShanksAgent(agent);
    }
    private static final long serialVersionUID = -4791498585683932164L;
}