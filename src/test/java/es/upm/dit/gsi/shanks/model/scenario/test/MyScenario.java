package es.upm.dit.gsi.shanks.model.scenario.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyScenario3DPortrayal;

public class MyScenario extends Scenario {

    private Logger logger = Logger.getLogger(MyScenario.class.getName());
    
    public static final String CLOUDY = "CLOUDY";
    public static final String SUNNY = "SUNNY";

    private int dimensions;

    private double cloudyPenalty;

    public MyScenario(String id, String initialState, double cloudyPenalty)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException {
        super(id, initialState);
        this.cloudyPenalty = cloudyPenalty;
        this.dimensions = 2;
    }

    public MyScenario(String id, String initialState)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException {
        super(id, initialState);
        this.cloudyPenalty = 3.0;
        this.dimensions = 2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
     */
    @Override
    public void addNetworkElements()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, DuplicatedIDException {
        Device d1 = new MyDevice("D1", MyDevice.OK, false);
        Device d2 = new MyDevice("D2", MyDevice.OK, false);
        Device d3 = new MyDevice("D3", MyDevice.OK, false);
        Device d4 = new MyDevice("D4", MyDevice.OK, false);
        Device d5 = new MyDevice("D5", MyDevice.OK, true);
        Link l1 = new MyLink("L1", MyLink.OK, 3);
        Link l2 = new MyLink("L2", MyLink.OK, 2);
        Link l3 = new MyLink("L3", MyLink.OK, 2);

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
    @Override
    public void addPossibleFailures() {
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
        this.addPossibleFailure(MyFailure.class, possibleCombinations);
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

        penalties.put(MyFailure.class, 1.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getCloudyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(MyFailure.class, this.cloudyPenalty);

        return penalties;
    }

    public ScenarioPortrayal createScenarioPortrayal() {
        logger.fine("Creating Scenario Portrayal...");
        switch (this.dimensions) {
        case 2:
            logger.fine("Creating Scenario2DPortrayal");
            return new MyScenario2DPortrayal(this, 100, 100);
        case 3:
            logger.fine("Creating Scenario3DPortrayal");
            return new MyScenario3DPortrayal(this, 100, 100, 100);
        }
        return null;

    }

}
