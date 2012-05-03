package es.upm.dit.gsi.shanks.hackerhan.model.han.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.link.EthernetCable;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.link.WifiConnection;
import es.upm.dit.gsi.shanks.hackerhan.model.han.failure.ComputerFailure;
import es.upm.dit.gsi.shanks.hackerhan.model.han.failure.NoIPFailure;
import es.upm.dit.gsi.shanks.hackerhan.model.han.failure.NoISPConnection;
import es.upm.dit.gsi.shanks.hackerhan.model.han.failure.RouterFailure;
import es.upm.dit.gsi.shanks.hackerhan.model.han.failure.WirelessDeviceFailure;
import es.upm.dit.gsi.shanks.hackerhan.model.han.scenario.portrayal.HackerHanScenario2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.han.scenario.portrayal.HackerHanScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 * 
 */
public class HackerHANScenario extends Scenario {
	
	//TODO make that the status has influence. 
	public static final String STATUS_NORMAL = "Normal";
	public static final String STATUS_MONITORIZED = "Monitorized";
	public static final String STATUS_BLOCKED = "Blocked";
	public static final String STATUS_ATTACKING = "Attacking";
	
	public HackerHANScenario(String id, String initialState, Properties properties)
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
		
		Computer computer = new Computer("PC");
		WifiRouterADSL router = new WifiRouterADSL("Router");
		WirelessDevice android = new WirelessDevice("Android");
		WirelessDevice tablet = new WirelessDevice("Tablet");
		
		EthernetCable ethernetCable = new EthernetCable("Ethernet", 2.5);
		WifiConnection wifi = new WifiConnection("Wifi", WifiConnection.STATUS_OK, 64);
		computer.connectToDeviceWithLink(router, ethernetCable);
		android.connectToDeviceWithLink(router, wifi);
		tablet.connectToDeviceWithLink(router, wifi);
		
		this.addNetworkElement(computer);
		this.addNetworkElement(router);
		this.addNetworkElement(android);
		this.addNetworkElement(tablet);
		this.addNetworkElement(ethernetCable);
		this.addNetworkElement(wifi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
	 */
	@Override
	public void addPossibleFailures() {
		NetworkElement computer = this.getNetworkElement("PC");
		NetworkElement router = this.getNetworkElement("Router");
		NetworkElement android = this.getNetworkElement("Android");
		NetworkElement tablet = this.getNetworkElement("Tablet");
//		NetworkElement ethernet = this.getNetworkElement("Ethernet");
//		NetworkElement wifi= this.getNetworkElement("Wifi");
		
//		ComputerFailure.java
		this.addPossibleFailure(ComputerFailure.class, computer);
		
//		NoIPFailure.java
		Set<NetworkElement> set = new HashSet<NetworkElement>();
		set.add(router);
		set.add(computer);
		set.add(android);
		set.add(tablet);
		this.addPossibleFailure(NoIPFailure.class, set);
		
//		NoISPConnection.java
		this.addPossibleFailure(NoISPConnection.class, set);
		
//		RouterFailure.java
		this.addPossibleFailure(RouterFailure.class, router);
		
//		WirelessDeviceFailure.java
		List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
		set = new HashSet<NetworkElement>();
		set.add(android);
		possibleCombinations.add(set);
		set = new HashSet<NetworkElement>();
		set.add(tablet);
		possibleCombinations.add(set);
		set = new HashSet<NetworkElement>();
		set.add(android);
		set.add(tablet);
		possibleCombinations.add(set);
		this.addPossibleFailure(WirelessDeviceFailure.class, possibleCombinations);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
	 */
	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new HackerHanScenario2DPortrayal(this, 400, 400);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
	 */
	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new HackerHanScenario3DPortrayal(this, 100, 100, 100);
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

		HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
		penalties.put(ComputerFailure.class, 1.0);
		penalties.put(NoIPFailure.class, 1.0);
		penalties.put(NoISPConnection.class, 1.0);
		penalties.put(RouterFailure.class, 1.0);
		penalties.put(WirelessDeviceFailure.class, 1.0);
		
//		penalties.put(ComputerFailure.class, Values.COMPUTER_FAILURE_PROB);
//		penalties.put(NoIPFailure.class, Values.NO_IP_FAILURE_PROB);
//		penalties.put(NoISPConnection.class, Values.NO_ISP_FAILURE_PROB);
//		penalties.put(RouterFailure.class, Values.ROUTER_FAILURE_PROB);
//		penalties.put(WirelessDeviceFailure.class, Values.WIRELESSD_FAILURE_PROB);
		return penalties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(HackerHANScenario.STATUS_MONITORIZED);
		this.addPossibleStatus(HackerHANScenario.STATUS_BLOCKED);
		this.addPossibleStatus(HackerHANScenario.STATUS_ATTACKING);
	}

	@Override
	public void addPossibleEvents() {
		// Nothing here.  
	}

}
