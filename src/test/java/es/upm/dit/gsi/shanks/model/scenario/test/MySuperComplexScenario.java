package es.upm.dit.gsi.shanks.model.scenario.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.AlreadyConnectedScenarioException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.NonGatewayDeviceException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MySuperComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MySuperComplexScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation2DGUI;

/**
 * @author a.carrera
 *
 */
public class MySuperComplexScenario extends ComplexScenario {

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

    /**
     * @param type
     * @param initialState
     * @param properties
     * @throws UnsupportedNetworkElementStatusException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     * @throws NonGatewayDeviceException
     * @throws AlreadyConnectedScenarioException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public MySuperComplexScenario(String type, String initialState,
            Properties properties)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException,
            AlreadyConnectedScenarioException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(type, initialState, properties);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.ComplexScenario#addScenarios()
     */
    @Override
    public void addScenarios() throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException,
            AlreadyConnectedScenarioException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        this.addScenario(MyComplexScenario.class, "ComplexScenario1", MyComplexScenario.SUNNY, this.getProperties(), "ED1", "SEL1");
        this.addScenario(MyComplexScenario.class, "ComplexScenario2", MyComplexScenario.SUNNY, this.getProperties(), "ED1", "SEL1");
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MySuperComplexScenario.STORM);
        this.addPossibleStatus(MySuperComplexScenario.EARTHQUAKE);
        this.addPossibleStatus(MySuperComplexScenario.SUNNY);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
     */
    @Override
    public void addNetworkElements()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, DuplicatedIDException {
        Link sel1 = new MyLink("SEL1", MyLink.OK_STATUS, 3);
        Device sed1 = new MyDevice("SED1", MyDevice.OK_STATUS, true);

        this.addNetworkElement(sed1);
        sed1.connectToLink(sel1);
        this.addNetworkElement(sel1);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
     */
    @Override
    public void addPossibleFailures() {
        this.addPossibleFailure(MyFailure.class, this.getNetworkElement("SEL1"));

    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#getPenaltiesInStatus(java.lang.String)
     */
    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {

        if (status.equals(MySuperComplexScenario.STORM)) {
            return this.getStormPenalties();
        } else if (status.equals(MySuperComplexScenario.EARTHQUAKE)) {
            return this.getEarthquakePenalties();
        } else if (status.equals(MySuperComplexScenario.SUNNY)) {
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

        penalties.put(MyFailure.class, 1.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getEarthquakePenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(MyFailure.class, 10.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getStormPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(MyFailure.class, 3.0);

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
     * @throws UnsupportedNetworkElementStatusException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     * @throws DuplicatedActionIDException 
     * @throws DuplicatedAgentIDException 
     */
    public static void main(String[] args) throws SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException,
            UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException, DuplicatedAgentIDException, DuplicatedActionIDException {

        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
//        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(MyShanksSimulation.CONFIGURATION, "3");
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), MySuperComplexScenario.class,
                "MySuperComplexScenario", MySuperComplexScenario.SUNNY,
                scenarioProperties, configProperties);
         MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
//        MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
        gui.start();
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
     */
    @Override
    public Scenario2DPortrayal createScenario2DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return new MySuperComplexScenario2DPortrayal(this, 200, 200);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
     */
    @Override
    public Scenario3DPortrayal createScenario3DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return new MySuperComplexScenario3DPortrayal(this, 100, 100, 100);
    }

    @Override
    public void addPossibleEvents() {
        // TODO Auto-generated method stub
        
    }
}
