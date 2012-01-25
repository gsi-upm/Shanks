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
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyMegaComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyMegaComplexScenario3DPortrayal;

public class MyMegaComplexScenario extends ComplexScenario {

    public static final String STORM = "STORM";
    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String SUNNY = "SUNNY";

    public MyMegaComplexScenario(String type, String initialState,
            Properties properties)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException,
            AlreadyConnectedScenarioException, SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException {
        super(type, initialState, properties);
    }

    @Override
    public void addScenarios() throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException,
            AlreadyConnectedScenarioException, SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException {

        this.addScenario(MySuperComplexScenario.class, "SuperComplexScenario1", MySuperComplexScenario.SUNNY, this.getProperties(), "SED1", "MEL1");
        this.addScenario(MySuperComplexScenario.class, "SuperComplexScenario2", MySuperComplexScenario.SUNNY, this.getProperties(), "SED1", "MEL1");
    }

    @Override
    public Scenario2DPortrayal createScenario2DPortrayal()
            throws DuplicatedPortrayalID, ScenarioNotFoundException {
        return new MyMegaComplexScenario2DPortrayal(this, 200, 200);
    }

    @Override
    public Scenario3DPortrayal createScenario3DPortrayal()
            throws DuplicatedPortrayalID, ScenarioNotFoundException {
        return new MyMegaComplexScenario3DPortrayal(this, 100, 100, 100);
    }

    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyMegaComplexScenario.STORM);
        this.addPossibleStatus(MyMegaComplexScenario.EARTHQUAKE);
        this.addPossibleStatus(MyMegaComplexScenario.SUNNY);
    }

    @Override
    public void addNetworkElements()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, DuplicatedIDException {
        Link mel1 = new MyLink("MEL1", MyLink.OK_STATUS, 3);
        Device med1 = new MyDevice("MED1", MyDevice.OK_STATUS, true);

        this.addNetworkElement(med1);
        med1.connectToLink(mel1);
        this.addNetworkElement(mel1);
    }

    @Override
    public void addPossibleFailures() {
        this.addPossibleFailure(MyFailure.class, this.getNetworkElement("MEL1"));

    }

    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {

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
            DuplicatedIDException, DuplicatedPortrayalID, ScenarioNotFoundException {

        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
//        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), MyMegaComplexScenario.class,
                "MyMegaComplexScenario", MyMegaComplexScenario.SUNNY,
                scenarioProperties, configProperties);
         MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
//        MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
        gui.start();
    }

}
