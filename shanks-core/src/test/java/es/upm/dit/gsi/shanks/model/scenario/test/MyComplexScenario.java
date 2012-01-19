package es.upm.dit.gsi.shanks.model.scenario.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.MyShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.model.element.device.Device;
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
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyComplexScenario3DPortrayal;

public class MyComplexScenario extends ComplexScenario {

    public static final String STORM = "STORM";
    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String SUNNY = "SUNNY";

//    private Logger logger = Logger.getLogger(MyComplexScenario.class.getName());

    public MyComplexScenario(String type, String initialState, Properties properties)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException, AlreadyConnectedScenarioException {
        super(type, initialState, properties);
    }

    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyComplexScenario.STORM);
        this.addPossibleStatus(MyComplexScenario.EARTHQUAKE);
        this.addPossibleStatus(MyComplexScenario.SUNNY);
    }

    @Override
    public void addNetworkElements()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, DuplicatedIDException {
        Link el1 = new MyLink("EL1", MyLink.OK_STATUS, 2);
        
        this.addNetworkElement(el1);

    }

    @Override
    public void addPossibleFailures() {
        this.addPossibleFailure(MyFailure.class, this.getNetworkElement("EL1"));

    }

    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {

        if (status.equals(MyComplexScenario.STORM)) {
            return this.getStormPenalties();
        } else if (status.equals(MyComplexScenario.EARTHQUAKE)) {
            return this.getEarthquakePenalties();
        } else if (status.equals(MyComplexScenario.SUNNY)) {
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

    @Override
    public void addScenarios() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException, NonGatewayDeviceException, AlreadyConnectedScenarioException {
        Properties scenarioProperties1 = new Properties();
        scenarioProperties1.put(MyScenario.CLOUDY_PROB, this.getProperty(MyScenario.CLOUDY_PROB));
        scenarioProperties1.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_2D);
        Scenario s1 = new MyScenario("Scenario1", MyScenario.SUNNY, scenarioProperties1);

        Properties scenarioProperties2 = new Properties();
        scenarioProperties2.put(MyScenario.CLOUDY_PROB, this.getProperty(MyScenario.CLOUDY_PROB));
        scenarioProperties2.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_2D);
        Scenario s2 = new MyScenario("Scenario2", MyScenario.CLOUDY, scenarioProperties2);
        
        this.addScenario(s1, (Device)s1.getNetworkElement("D5"), (Link)this.getNetworkElement("EL1"));
        this.addScenario(s2, (Device)s2.getNetworkElement("D5"), (Link)this.getNetworkElement("EL1"));
    }
    
    public static void main (String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException {

        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
//        scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_2D);
        scenarioProperties.put(Scenario.PORTRAYAL_DIMENSIONS, Scenario.SIMULATION_3D);
        Properties configProperties = new Properties();
        configProperties.put(MyShanksSimulation.CONFIGURATION, "1");
        MyShanksSimulation sim = new MyShanksSimulation(System.currentTimeMillis(), MyComplexScenario.class, "MyComplexScenario", MyComplexScenario.SUNNY, scenarioProperties, configProperties);
//        MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
        MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
        gui.start();
    }

    @Override
    public Scenario2DPortrayal createScenario2DPortrayal() {
        return new MyComplexScenario2DPortrayal(this, 100, 100);
    }

    @Override
    public Scenario3DPortrayal createScenario3DPortrayal() {
        return new MyComplexScenario3DPortrayal(this, 100, 100, 100);
    }

}
