package es.upm.dit.gsi.shanks.model.ftth.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
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
import es.upm.dit.gsi.shanks.model.ftth.element.device.GatewayRouter;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.OLTtoSplitter;
import es.upm.dit.gsi.shanks.model.ftth.element.link.SplitterToONT;
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTFailure;
import es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal.OfficeScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal.OfficeScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class OfficeScenario extends Scenario{

	public OfficeScenario(String id, String initialState, Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException {
		super(id, initialState, properties);
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new OfficeScenario2DPortrayal(this, 200, 200);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new OfficeScenario3DPortrayal(this, 200, 200, 200);
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(ScenarioDefinitions.SUNNY);
		this.addPossibleStatus(ScenarioDefinitions.CLOUDY);	
	}

	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		Device olt = new OLT("OLT", DeviceDefinitions.OK_STATUS, true);
		Device splitter1 = new Splitter("Splitter1", DeviceDefinitions.OK_STATUS, false);
		Device splitter2 = new Splitter("Splitter2", DeviceDefinitions.OK_STATUS, false);
		Device splitter3 = new Splitter("Splitter3", DeviceDefinitions.OK_STATUS, false);
		Device ont1 = new ONT("ONT1", DeviceDefinitions.OK_STATUS, false);
		Device ont2 = new ONT("ONT2", DeviceDefinitions.OK_STATUS, false);
		Device ont3 = new ONT("ONT3", DeviceDefinitions.OK_STATUS, false);
		Device router1 = new GatewayRouter("Router1", DeviceDefinitions.OK_STATUS, false);
		Device router2 = new GatewayRouter("Router2", DeviceDefinitions.OK_STATUS, false);
		Device router3 = new GatewayRouter("Router3", DeviceDefinitions.OK_STATUS, false);
		Device router4 = new GatewayRouter("Router4", DeviceDefinitions.OK_STATUS, false);
		Device router5 = new GatewayRouter("Router5", DeviceDefinitions.OK_STATUS, false);
		Device router6 = new GatewayRouter("Router6", DeviceDefinitions.OK_STATUS, false);
		Link link0 = new OLTtoSplitter("Link0", LinkDefinitions.OK_STATUS, 4);
		Link link1 = new SplitterToONT("Link1", LinkDefinitions.OK_STATUS, 2);
		Link link2 = new SplitterToONT("Link2", LinkDefinitions.OK_STATUS, 2);
		Link link3 = new SplitterToONT("Link3", LinkDefinitions.OK_STATUS, 2);
		Link link4 = new SplitterToONT("Link4", LinkDefinitions.OK_STATUS, 24);
		Link link5 = new SplitterToONT("Link5", LinkDefinitions.OK_STATUS, 24);
		Link link6 = new SplitterToONT("Link6", LinkDefinitions.OK_STATUS, 24);
		
		link0.connectDevices(olt,splitter1);
		link0.connectDevices(olt, splitter2);
		link0.connectDevices(olt, splitter3);
		link1.connectDevices(splitter1, ont1);
		link2.connectDevices(splitter2, ont2);
		link3.connectDevices(splitter3, ont3);
		link4.connectDevices(ont1, router1);
		link4.connectDevices(ont1, router2);
		link5.connectDevices(ont2, router3);
		link5.connectDevices(ont2, router4);
		link6.connectDevices(ont3, router5);
		link6.connectDevices(ont3, router6);
		
		this.addNetworkElement(olt);
		this.addNetworkElement(splitter1);
		this.addNetworkElement(splitter2);
		this.addNetworkElement(splitter3);
		this.addNetworkElement(ont1);
		this.addNetworkElement(ont2);
		this.addNetworkElement(ont3);
		this.addNetworkElement(router1);
		this.addNetworkElement(router2);
		this.addNetworkElement(router3);
		this.addNetworkElement(router4);
		this.addNetworkElement(router5);
		this.addNetworkElement(router6);
		
		this.addNetworkElement(link0);
		this.addNetworkElement(link1);
		this.addNetworkElement(link2);
		this.addNetworkElement(link3);
		this.addNetworkElement(link4);
		this.addNetworkElement(link5);
		this.addNetworkElement(link6);
	}

	@Override
	public void addPossibleFailures() {
		Set<NetworkElement> seta = new HashSet<NetworkElement>();
        seta.add(this.getNetworkElement("OLT"));
        seta.add(this.getNetworkElement("Splitter1"));
        seta.add(this.getNetworkElement("Splitter2"));
        seta.add(this.getNetworkElement("Splitter3"));
        this.addPossibleFailure(OLTFailure.class, seta);
		
	}

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
	
	/**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(OLTFailure.class, 1.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getCloudyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
        String probs = (String) this.getProperty(ScenarioDefinitions.CLOUDY_PROB);
        double prob = new Double(probs);
        penalties.put(OLTFailure.class, prob);

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
		FTTHSimulation sim = new FTTHSimulation( System.currentTimeMillis(), OfficeScenario.class,
				"Office Scenario", ScenarioDefinitions.SUNNY, scenarioProperties, configProperties);
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
