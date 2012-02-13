package es.upm.dit.gsi.shanks.model.ftth.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;
import es.upm.dit.gsi.shanks.model.ftth.FTTHSimulation;
import es.upm.dit.gsi.shanks.model.ftth.FTTHSimulation2D;
import es.upm.dit.gsi.shanks.model.ftth.FTTHSimulation3D;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.OLTtoSplitter;
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTFailure;
import es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal.FTTHComplexScenario2DGUI;
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

public class FTTHComplexScenario extends ComplexScenario {
	
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

	public FTTHComplexScenario(String type, String initialState,
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

	
	/*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.ComplexScenario#addScenarios()
     */
	@Override
	public void addScenarios() throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, NonGatewayDeviceException,
			AlreadyConnectedScenarioException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		
		Properties p = this.getProperties();
        p.put(ScenarioDefinitions.CLOUDY_PROB, "10.0");
        this.addScenario(HomeScenario.class, "HomeScenario", ScenarioDefinitions.SUNNY, p,
                "OLT", "HomeLink");
        p.put(ScenarioDefinitions.CLOUDY_PROB, "50.0");
        this.addScenario(OfficeScenario.class, "OfficeScenario", ScenarioDefinitions.SUNNY, p,
                "OLT", "OfficeLink");
		
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new FTTHComplexScenario2DGUI(this, 400, 400);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}



	/*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
     */
	public void setPossibleStates() {
		this.addPossibleStatus(ScenarioDefinitions.STORM);
        this.addPossibleStatus(ScenarioDefinitions.EARTHQUAKE);
        this.addPossibleStatus(ScenarioDefinitions.SUNNY);
		
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
		Link el1 = new OLTtoSplitter("HomeLink", LinkDefinitions.OK_STATUS, 2);
        Link el2 = new OLTtoSplitter("OfficeLink", LinkDefinitions.OK_STATUS, 2);
        Device ed1 = new OLT("Cloud", DeviceDefinitions.OK_STATUS, true);

        this.addNetworkElement(ed1);
        ed1.connectToLink(el1);
        ed1.connectToLink(el2);
        this.addNetworkElement(el1);
        this.addNetworkElement(el2);
		
	}

	
	/*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
     */
	@Override
	public void addPossibleFailures() {
		this.addPossibleFailure(OLTFailure.class, this.getNetworkElement("EL1"));
		
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
		if (status.equals(ScenarioDefinitions.STORM)) {
            return this.getStormPenalties();
        } else if (status.equals(ScenarioDefinitions.EARTHQUAKE)) {
            return this.getEarthquakePenalties();
        } else if (status.equals(ScenarioDefinitions.SUNNY)) {
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
            DuplicatedIDException, DuplicatedPortrayalIDException,
            ScenarioNotFoundException, DuplicatedAgentIDException, DuplicatedActionIDException {

        Properties scenarioProperties = new Properties();
        scenarioProperties.put(ScenarioDefinitions.CLOUDY_PROB, "5");
        //scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
        // scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Properties configProperties = new Properties();
        configProperties.put(FTTHSimulation.CONFIGURATION, "1");
        FTTHSimulation sim = new FTTHSimulation(
                System.currentTimeMillis(), FTTHComplexScenario.class,
                "FTTHComplexScenario", ScenarioDefinitions.SUNNY,
                scenarioProperties, configProperties);
         //FTTHSimulation2D gui = new FTTHSimulation2D(sim);
         FTTHSimulation3D gui = new FTTHSimulation3D(sim);
        gui.start();
    }

}
