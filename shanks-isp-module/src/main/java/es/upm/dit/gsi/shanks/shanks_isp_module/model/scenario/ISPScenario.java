package es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.hackerhan.model.scenario.HackerHANScenario;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.AlreadyConnectedScenarioException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.NonGatewayDeviceException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.EnterpriseScenario;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.Values;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario.portrayal.ISPScenario2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.link.EthernetLink;
import es.upm.dit.gsi.shanks.workerroom.model.failure.WireBroken;
import es.upm.dit.gsi.shanks.workerroom.model.scenario.WorkerRoomScenario;

public class ISPScenario extends ComplexScenario{

	
	
	public static final String STATUS_NORMAL = "Normal";
	public static final String STATUS_UNDER_ATTACK = "UnderAttack";
	
	public ISPScenario(String type, String initialState, Properties properties)
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
		Properties p = this.getProperties();
	    p.put(WorkerRoomScenario.CLOUDY_PROB, "10.0");
	    this.addScenario(EnterpriseScenario.class, "Enterprise", EnterpriseScenario.STATUS_NORMAL, p, "Intranet LANRouter", "LINK1");
	    this.addScenario(HackerHANScenario.class, "Hacker HAN 1", HackerHANScenario.STATUS_NORMAL, p, Values.WIFI_ROUTER_ID, "LINK2");
	    this.addScenario(HackerHANScenario.class, "Hacker HAN 2", HackerHANScenario.STATUS_NORMAL, p, Values.WIFI_ROUTER_ID, "LINK3");
	    this.addScenario(HackerHANScenario.class, "Hacker HAN 3", HackerHANScenario.STATUS_NORMAL, p, Values.WIFI_ROUTER_ID, "LINK4");
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new ISPScenario2DPortrayal(this, 150, 150);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return null;
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(STATUS_NORMAL);
		this.addPossibleStatus(STATUS_UNDER_ATTACK);
	}

	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		Device ispRouter = new ISPGateway("ISP Gateway", this);
		Link cupperLink1 = new EthernetLink("LINK1", EthernetLink.STATUS_OK, 2);
		Link cupperLink2 = new EthernetLink("LINK2", EthernetLink.STATUS_OK, 2);
		Link cupperLink3 = new EthernetLink("LINK3", EthernetLink.STATUS_OK, 2);
		Link cupperLink4 = new EthernetLink("LINK4", EthernetLink.STATUS_OK, 2);
		
		ispRouter.connectToLink(cupperLink4);
		ispRouter.connectToLink(cupperLink3);
		ispRouter.connectToLink(cupperLink2);
		ispRouter.connectToLink(cupperLink1);
		
		this.addNetworkElement(cupperLink4);
		this.addNetworkElement(cupperLink3);
		this.addNetworkElement(cupperLink2);
		this.addNetworkElement(cupperLink1);
		this.addNetworkElement(ispRouter);

	}

	@Override
	public void addPossibleFailures() {
		Set<NetworkElement> set1 = new HashSet<NetworkElement>();
		set1.add(this.getNetworkElement("LINK1"));
		Set<NetworkElement> set2 = new HashSet<NetworkElement>();
		set2.add(this.getNetworkElement("LINK2"));
		Set<NetworkElement> set3 = new HashSet<NetworkElement>();
		set3.add(this.getNetworkElement("LINK3"));
		Set<NetworkElement> set4 = new HashSet<NetworkElement>();
		set4.add(this.getNetworkElement("LINK4"));
        List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
		possibleCombinations.add(set1);
		possibleCombinations.add(set2);
		possibleCombinations.add(set3);
		possibleCombinations.add(set4);
		this.addPossibleFailure(WireBroken.class, possibleCombinations);
		
		
		
	}

	@Override
	public void addPossibleEvents() {
		
	}


	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws UnsupportedScenarioStatusException {
		 
		HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
	    return penalties;
	}

}
