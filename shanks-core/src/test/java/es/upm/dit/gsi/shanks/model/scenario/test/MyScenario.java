package es.upm.dit.gsi.shanks.model.scenario.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.model.event.test.MyProbNetElementEvent;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTest;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation2DGUI;

/**
 * @author a.carrera
 *
 */
public class MyScenario extends Scenario {

    /**
     * @param id
     * @param initialState
     * @param properties
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     */
    public MyScenario(String id, String initialState, Properties properties, Logger logger)
            throws ShanksException,
            DuplicatedIDException {
        super(id, initialState, properties, logger);
    }

    public static final String CLOUDY = "CLOUDY";
    public static final String SUNNY = "SUNNY";

    public static final String CLOUDY_PROB = "CLOUDY_PROB";

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
     */
    @Override
    public void addNetworkElements()
            throws ShanksException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, false, this.getLogger());
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, false, this.getLogger());
        Device d3 = new MyDevice("D3", MyDevice.OK_STATUS, false, this.getLogger());
        Device d4 = new MyDevice("D4", MyDevice.OK_STATUS, false, this.getLogger());
        Device d5 = new MyDevice("D5", MyDevice.OK_STATUS, true, this.getLogger());
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 3, this.getLogger());
        Link l2 = new MyLink("L2", MyLink.OK_STATUS, 2, this.getLogger());
        Link l3 = new MyLink("L3", MyLink.OK_STATUS, 2, this.getLogger());

        d1.connectToLink(l1);
        d2.connectToLink(l1);
        d3.connectToLink(l1);

        l2.connectDevices(d1, d4);

        l3.connectDevices(d3, d5);

        this.addNetworkElement(d1);
        this.addNetworkElement(d2);
        this.addNetworkElement(d3);
        this.addNetworkElement(d4);
        this.addNetworkElement(d5);
        this.addNetworkElement(l1);
        this.addNetworkElement(l2);
        this.addNetworkElement(l3);

    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
     */
//    @Override
    public void addPossibleFailures() {
        Set<NetworkElement> seta = new HashSet<NetworkElement>();
        seta.add(this.getNetworkElement("D1"));
        seta.add(this.getNetworkElement("L1"));
        this.addPossibleFailure(FailureTest.class, seta);
        Set<NetworkElement> set = new HashSet<NetworkElement>();
        set.add(this.getNetworkElement("D1"));
        set.add(this.getNetworkElement("L1"));
        Set<NetworkElement> set2 = new HashSet<NetworkElement>();
        set2.add(this.getNetworkElement("D5"));
        set2.add(this.getNetworkElement("L3"));
        Set<NetworkElement> set3 = new HashSet<NetworkElement>();
        set3.add(this.getNetworkElement("D4"));
        set3.add(this.getNetworkElement("L2"));
        List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
        possibleCombinations.add(set);
        possibleCombinations.add(set2);
        possibleCombinations.add(set3);
        this.addPossibleFailure(FailureTest.class, possibleCombinations);
    }
    
    @Override
    public void addPossibleEvents() {
          Set<NetworkElement> seta = new HashSet<NetworkElement>();
          seta.add(this.getNetworkElement("D1"));
          seta.add(this.getNetworkElement("L1"));
          this.addPossibleEventsOfNE(MyProbNetElementEvent.class, seta);
          Set<NetworkElement> set = new HashSet<NetworkElement>();
          set.add(this.getNetworkElement("D1"));
          set.add(this.getNetworkElement("L1"));
          Set<NetworkElement> set2 = new HashSet<NetworkElement>();
          set2.add(this.getNetworkElement("D5"));
          set2.add(this.getNetworkElement("L3"));
          Set<NetworkElement> set3 = new HashSet<NetworkElement>();
          set3.add(this.getNetworkElement("D4"));
          set3.add(this.getNetworkElement("L2"));
          List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
          possibleCombinations.add(set);
          possibleCombinations.add(set2);
          possibleCombinations.add(set3);
          this.addPossibleEventsOfNE(MyProbNetElementEvent.class, possibleCombinations);
          this.addPossibleEventsOfNE(MyProbNetElementEvent.class, this.getNetworkElement("D1"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyScenario.CLOUDY);
        this.addPossibleStatus(MyScenario.SUNNY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.model.scenario.Scenario#getPenaltiesInStatus(java
     * .lang.String)
     */
    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {

        if (status.equals(MyScenario.CLOUDY)) {
            return this.getCloudyPenalties();
        } else if (status.equals(MyScenario.SUNNY)) {
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
    private HashMap<Class<? extends Failure>, Double> getCloudyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
        String probs = (String) this.getProperty(MyScenario.CLOUDY_PROB);
        double prob = new Double(probs);
        penalties.put(FailureTest.class, prob);

        return penalties;
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
     */
    @Override
    public Scenario2DPortrayal createScenario2DPortrayal() throws ShanksException {
        return new MyScenario2DPortrayal(this, 100, 100);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
     */
    @Override
    public Scenario3DPortrayal createScenario3DPortrayal() throws ShanksException {
        return new MyScenario3DPortrayal(this, 100, 100, 100);
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
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
//        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
        //scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(MyShanksSimulation.CONFIGURATION, "3");
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), MyScenario.class,
                "MyScenario", MyScenario.SUNNY,
                scenarioProperties, configProperties);
        MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
//        MyShanksSimulation3DGUI gui = new MyShanksSimulation3DGUI(sim);
        gui.start();
//        sim.start();
//        do
//            if (!sim.schedule.step(sim))
//                break;
//        while (sim.schedule.getSteps() < 2001);
//        sim.finish();
    }






}
