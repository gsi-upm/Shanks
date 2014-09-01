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
/**
 * 
 */
package es.upm.dit.gsi.shanks.model.scenario.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.event.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTest;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation3DGUI;

/**
 * @author a.carrera
 *
 */
public class MyHyperComplexScenario extends ComplexScenario {

    /**
     * 
     */
    public static final String STORM = "STORM";
    /**
     * 
     */
    public static final String EARTHQUAKE = "EARTHQUAKE";
    /**
     * 
     */
    public static final String SUNNY = "SUNNY";

    public MyHyperComplexScenario(String type, String initialState,
            Properties properties, Logger logger)
            throws ShanksException {
        super(type, initialState, properties, logger);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.ComplexScenario#addScenarios()
     */
    @Override
    public void addScenarios() throws ShanksException {
        this.addScenario(MyMegaComplexScenario.class, "MegaComplexScenario1", MyMegaComplexScenario.SUNNY, this.getProperties(), "MED1", "HEL1");
        this.addScenario(MyMegaComplexScenario.class, "MegaComplexScenario2", MyMegaComplexScenario.SUNNY, this.getProperties(), "MED1", "HEL1");
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
     */
    @Override
    public Scenario2DPortrayal createScenario2DPortrayal()
            throws ShanksException {
        return new MyHyperComplexScenario2DPortrayal(this, 400, 400);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
     */
    @Override
    public Scenario3DPortrayal createScenario3DPortrayal()
            throws ShanksException {
        return new MyHyperComplexScenario3DPortrayal(this, 100, 100, 100);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyHyperComplexScenario.STORM);
        this.addPossibleStatus(MyHyperComplexScenario.EARTHQUAKE);
        this.addPossibleStatus(MyHyperComplexScenario.SUNNY);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
     */
    @Override
    public void addNetworkElements()
            throws ShanksException {
        Link hel1 = new MyLink("HEL1", MyLink.OK_STATUS, 3, this.getLogger());
        Device hed1 = new MyDevice("HED1", MyDevice.OK_STATUS, true, this.getLogger());

        this.addNetworkElement(hed1);
        hed1.connectToLink(hel1);
        this.addNetworkElement(hel1);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
     */
    @Override
    public void addPossibleFailures() {
        this.addPossibleFailure(FailureTest.class, this.getNetworkElement("HEL1"));
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#getPenaltiesInStatus(java.lang.String)
     */
    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws ShanksException {

        if (status.equals(MyMegaComplexScenario.STORM)) {
            return this.getStormPenalties();
        } else if (status.equals(MyMegaComplexScenario.EARTHQUAKE)) {
            return this.getEarthquakePenalties();
        } else if (status.equals(MyMegaComplexScenario.SUNNY)) {
            return this.getSunnyPenalties();
        } else {
            throw new UnsupportedScenarioStatusException();
        }
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(FailureTest.class, 1.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getEarthquakePenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(FailureTest.class, 10.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getStormPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(FailureTest.class, 3.0);

        return penalties;
    }

    /**
     * @param args
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
     */
    public static void main(String[] args) throws ShanksException {

        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
//        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(MyShanksSimulation.CONFIGURATION, "3");
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), MyHyperComplexScenario.class,
                "MyHyperComplexScenario", MyHyperComplexScenario.SUNNY,
                scenarioProperties, configProperties);
//         MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
        MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
        gui.start();
    }

    @Override
    public void addPossibleEvents() {
        // TODO Auto-generated method stub
        
    }

}
