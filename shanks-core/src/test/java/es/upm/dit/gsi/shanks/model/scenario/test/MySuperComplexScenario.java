package es.upm.dit.gsi.shanks.model.scenario.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.MyShanksSimulation2DGUI;
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

public class MySuperComplexScenario extends ComplexScenario {

    public static final String STORM = "STORM";
    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String SUNNY = "SUNNY";

    public MySuperComplexScenario(String type, String initialState,
            Properties properties)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException,
            AlreadyConnectedScenarioException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(type, initialState, properties);
    }

    @Override
    public void addScenarios() throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException,
            AlreadyConnectedScenarioException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        this.addScenario(MyComplexScenario.class, "ComplexScenario1", MyComplexScenario.SUNNY, this.getProperties(), "ED1", "SEL1");
        this.addScenario(MyComplexScenario.class, "ComplexScenario2", MyComplexScenario.SUNNY, this.getProperties(), "ED1", "SEL1");
    }

    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MySuperComplexScenario.STORM);
        this.addPossibleStatus(MySuperComplexScenario.EARTHQUAKE);
        this.addPossibleStatus(MySuperComplexScenario.SUNNY);
    }

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

    @Override
    public void addPossibleFailures() {
        this.addPossibleFailure(MyFailure.class, this.getNetworkElement("SEL1"));

    }

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

    private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(MyFailure.class, 1.0);

        return penalties;
    }

    private HashMap<Class<? extends Failure>, Double> getEarthquakePenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(MyFailure.class, 10.0);

        return penalties;
    }

    private HashMap<Class<? extends Failure>, Double> getStormPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(MyFailure.class, 3.0);

        return penalties;
    }

    public static void main(String[] args) throws SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException,
            UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException {

        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
//        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), MySuperComplexScenario.class,
                "MySuperComplexScenario", MySuperComplexScenario.SUNNY,
                scenarioProperties, configProperties);
         MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
//        MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
        gui.start();
    }

    @Override
    public Scenario2DPortrayal createScenario2DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return new MySuperComplexScenario2DPortrayal(this, 200, 200);
    }

    @Override
    public Scenario3DPortrayal createScenario3DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return new MySuperComplexScenario3DPortrayal(this, 100, 100, 100);
    }
}
