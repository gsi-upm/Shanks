package es.upm.dit.gsi.shanks.model.ftth.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.FTTHSimulation;
import es.upm.dit.gsi.shanks.model.ftth.FTTHSimulation2D;
import es.upm.dit.gsi.shanks.model.ftth.FTTHSimulation3D;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.OLTtoSplitter;
import es.upm.dit.gsi.shanks.model.ftth.element.link.SplitterToONT;
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTEmitedLaserFailure;
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTReceivedLaserFailure;
import es.upm.dit.gsi.shanks.model.ftth.failure.ONTEmitedLaserFailure;
import es.upm.dit.gsi.shanks.model.ftth.failure.ONTReceivedLaserFailure;
import es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal.HomeScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal.HomeScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class HomeScenario extends Scenario{
	
	
	
	public HomeScenario(String id, String initialState, Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException {
		super(id, initialState, properties);
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
			Device olt = new OLT("OLT", DeviceDefinitions.OK_STATUS, true);
			Device splitter = new Splitter("Splitter", DeviceDefinitions.OK_STATUS, false);
			Device ont1 = new ONT("ONT1", DeviceDefinitions.OK_STATUS, false);
			Device ont2 = new ONT("ONT2", DeviceDefinitions.OK_STATUS, false);
			Device ont3 = new ONT("ONT3", DeviceDefinitions.OK_STATUS, false);
//			Device ont4 = new ONT("ONT4", DeviceDefinitions.OK_STATUS, false);
//			Device ont5 = new ONT("ONT5", DeviceDefinitions.OK_STATUS, false);
//			Device ont6 = new ONT("ONT6", DeviceDefinitions.OK_STATUS, false);
//			Device ont7 = new ONT("ONT7", DeviceDefinitions.OK_STATUS, false);
//			Device ont8 = new ONT("ONT8", DeviceDefinitions.OK_STATUS, false);
//			Device ont9 = new ONT("ONT9", DeviceDefinitions.OK_STATUS, false);
			Link OLTtoSplitterLink = new OLTtoSplitter("Link0", LinkDefinitions.OK_STATUS, 2);
			Link userLink1 = new SplitterToONT("Link1", LinkDefinitions.OK_STATUS, 32);
			Link userLink2 = new SplitterToONT("Link2", LinkDefinitions.OK_STATUS, 32);
			Link userLink3 = new SplitterToONT("Link3", LinkDefinitions.OK_STATUS, 32);
			
			OLTtoSplitterLink.connectDevices(olt, splitter);
			userLink1.connectDevices(splitter, ont1);
			userLink2.connectDevices(splitter, ont2);
			userLink3.connectDevices(splitter, ont3);
			
			this.addNetworkElement(olt);
			this.addNetworkElement(splitter);
			this.addNetworkElement(ont1);
			this.addNetworkElement(ont2);
			this.addNetworkElement(ont3);
//			this.addNetworkElement(ont4);
//			this.addNetworkElement(ont5);
//			this.addNetworkElement(ont6);
//			this.addNetworkElement(ont7);
//			this.addNetworkElement(ont8);
//			this.addNetworkElement(ont9);
			
			this.addNetworkElement(OLTtoSplitterLink);
			this.addNetworkElement(userLink1);
			this.addNetworkElement(userLink2);
			this.addNetworkElement(userLink3);
	
	}

	
	/*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
     */
	@Override
	public void addPossibleFailures() {
		
        this.addPossibleFailure(OLTReceivedLaserFailure.class, 
        		this.getNetworkElement("OLT"));
        
        Set<NetworkElement> set = new HashSet<NetworkElement>();
        set.add(this.getNetworkElement("OLT"));
        this.addPossibleFailure(OLTEmitedLaserFailure.class, set);
        
        Set<NetworkElement> setOnt1 = new HashSet<NetworkElement>();
        setOnt1.add(this.getNetworkElement("ONT1"));
        Set<NetworkElement> setOnt2 = new HashSet<NetworkElement>();
        setOnt2.add(this.getNetworkElement("ONT2"));
        Set<NetworkElement> setOnt3 = new HashSet<NetworkElement>();
        setOnt3.add(this.getNetworkElement("ONT3"));
        List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
        possibleCombinations.add(setOnt1);
        possibleCombinations.add(setOnt2);
        possibleCombinations.add(setOnt3);
        this.addPossibleFailure(ONTReceivedLaserFailure.class, possibleCombinations);        
	
        Set<NetworkElement> setOntEmt1 = new HashSet<NetworkElement>();
        setOntEmt1.add(this.getNetworkElement("ONT1"));
        Set<NetworkElement> setOntEmt2 = new HashSet<NetworkElement>();
        setOntEmt2.add(this.getNetworkElement("ONT2"));
        Set<NetworkElement> setOntEmt3 = new HashSet<NetworkElement>();
        setOntEmt3.add(this.getNetworkElement("ONT3"));
        List<Set<NetworkElement>> possibleCombinationsEmt = new ArrayList<Set<NetworkElement>>();
        possibleCombinationsEmt.add(setOntEmt1);
        possibleCombinationsEmt.add(setOntEmt2);
        possibleCombinationsEmt.add(setOntEmt3);
        this.addPossibleFailure(ONTEmitedLaserFailure.class, possibleCombinationsEmt);    
	}
	
	
	 /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
     */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(ScenarioDefinitions.CLOUDY);
        this.addPossibleStatus(ScenarioDefinitions.SUNNY);
		
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
		if (status.equals(ScenarioDefinitions.CLOUDY)) {
            return this.getCloudyPenalties();
        } else if (status.equals(ScenarioDefinitions.SUNNY)) {
            return this.getSunnyPenalties();
        } else {
            throw new UnsupportedScenarioStatusException();
        }
	}
	
	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
     */
	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new HomeScenario2DPortrayal(this, 200, 200);
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
     */
	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new HomeScenario3DPortrayal(this, 100, 100, 100);
	}

	
	
	/**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(OLTReceivedLaserFailure.class, 1.0);
        penalties.put(OLTEmitedLaserFailure.class, 1.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getCloudyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
        String probs = (String) this.getProperty(ScenarioDefinitions.CLOUDY_PROB);
        double prob = new Double(probs);
        
        penalties.put(OLTReceivedLaserFailure.class, prob);
        penalties.put(OLTEmitedLaserFailure.class, 1.0);

        return penalties;
    }

	
	public static void main(String[] args) throws SecurityException,
    IllegalArgumentException, NoSuchMethodException,
    InstantiationException, IllegalAccessException,
    InvocationTargetException,
    UnsupportedNetworkElementStatusException,
    TooManyConnectionException, UnsupportedScenarioStatusException,
    DuplicatedIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException, DuplicatedAgentIDException, DuplicatedActionIDException {

		Properties scenarioProperties = new Properties();
		scenarioProperties.put(ScenarioDefinitions.CLOUDY_PROB, "5");
		scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
		//scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
		//scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
		Properties configProperties = new Properties();
		configProperties.put(FTTHSimulation.CONFIGURATION, "2");
		FTTHSimulation sim = new FTTHSimulation( System.currentTimeMillis(), HomeScenario.class,
				"Home Scenario", ScenarioDefinitions.SUNNY, scenarioProperties, configProperties);
		FTTHSimulation2D gui = new FTTHSimulation2D(sim);
		//FTTHSimulation3D gui = new FTTHSimulation3D(sim);
		gui.start();
		//sim.start();
		//do
		//    if (!sim.schedule.step(sim))
		//        break;
		//while (sim.schedule.getSteps() < 2001);
		//sim.finish();
	}
	
	

}
	


