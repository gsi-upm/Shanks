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
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTest;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.FinalComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.FinalComplexScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation3DGUI;

/**
 * @author a.carrera
 *
 */
public class FinalComplexScenario extends ComplexScenario {

    public FinalComplexScenario(String type, String initialState,
            Properties properties, Logger logger)
            throws ShanksException {
        super(type, initialState, properties, logger);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.ComplexScenario#addScenarios()
     */
    @Override
    public void addScenarios() throws ShanksException {
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario1", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL1");
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario2", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL2");
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario3", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL3");
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario4", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL4");
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario5", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL5");
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario6", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL6");
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario7", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL7");
        this.addScenario(MyHyperComplexScenario.class, "HyperComplexScenario8", MyHyperComplexScenario.SUNNY, this.getProperties(), "HED1", "FEL8");
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
     */
    @Override
    public Scenario2DPortrayal createScenario2DPortrayal()
            throws ShanksException {
        return new FinalComplexScenario2DPortrayal(this, 1100, 1500);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
     */
    @Override
    public Scenario3DPortrayal createScenario3DPortrayal()
            throws ShanksException {
        return new FinalComplexScenario3DPortrayal(this, 500, 500, 500);
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
        Link fel1 = new MyLink("FEL1", MyLink.OK_STATUS, 2, this.getLogger());
        Link fel2 = new MyLink("FEL2", MyLink.OK_STATUS, 2, this.getLogger());
        Link fel3 = new MyLink("FEL3", MyLink.OK_STATUS, 2, this.getLogger());
        Link fel4 = new MyLink("FEL4", MyLink.OK_STATUS, 2, this.getLogger());
        Link fel5 = new MyLink("FEL5", MyLink.OK_STATUS, 2, this.getLogger());
        Link fel6 = new MyLink("FEL6", MyLink.OK_STATUS, 2, this.getLogger());
        Link fel7 = new MyLink("FEL7", MyLink.OK_STATUS, 2, this.getLogger());
        Link fel8 = new MyLink("FEL8", MyLink.OK_STATUS, 2, this.getLogger());
        Device fed1 = new MyDevice("FED1", MyDevice.OK_STATUS, true, this.getLogger());

        this.addNetworkElement(fed1);
        
        fed1.connectToLink(fel1);
        this.addNetworkElement(fel1);
        
        fed1.connectToLink(fel2);
        this.addNetworkElement(fel2);
        
        fed1.connectToLink(fel3);
        this.addNetworkElement(fel3);
        
        fed1.connectToLink(fel4);
        this.addNetworkElement(fel4);
        
        fed1.connectToLink(fel5);
        this.addNetworkElement(fel5);
        
        fed1.connectToLink(fel6);
        this.addNetworkElement(fel6);
        
        fed1.connectToLink(fel7);
        this.addNetworkElement(fel7);
        
        fed1.connectToLink(fel8);
        this.addNetworkElement(fel8);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
     */
    @Override
    public void addPossibleFailures() {
        this.addPossibleFailure(FailureTest.class, this.getNetworkElement("FEL1"));
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
                System.currentTimeMillis(), FinalComplexScenario.class,
                "FinalComplexScenario", MyHyperComplexScenario.SUNNY,
                scenarioProperties, configProperties);
//         MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
        MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
        gui.start();
    }

    @Override
    public void addPossibleEvents() {
        
    }

}
