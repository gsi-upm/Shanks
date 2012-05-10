package es.upm.dit.gsi.shanks.hackerhan.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.hackerhan.model.element.device.Computer;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.hackerhan.model.element.link.EthernetCable;
import es.upm.dit.gsi.shanks.hackerhan.model.element.link.WifiConnection;
import es.upm.dit.gsi.shanks.hackerhan.model.failure.NoIPFailure;
import es.upm.dit.gsi.shanks.hackerhan.model.scenario.portrayal.HANScenario2DPortrayal;
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
public class HANScenario extends Scenario {

	public static final String STATUS_SUNNY = "Sunny";
	public static final String STATUS_RAINY = "Rainy";
	public static final String STATUS_SNOWY = "Snowy";
	
	public HANScenario(String id, String initialState, Properties properties)
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
//		ModemADSL modem = new ModemADSL("Modem", true);
//		WifiAccessPoint wifiAP = new WifiAccessPoint("WifiAccessPoint");
		WirelessDevice iPhone = new WirelessDevice("iPhone");
		WirelessDevice android = new WirelessDevice("Android");
		
		EthernetCable ethernetCable = new EthernetCable("Ethernet: PC-Router", 2.5);
//		InternalBus ibRouterWifi = new InternalBus("InternalBus_MRW", 0.5);
		WifiConnection wifi = new WifiConnection("Wifi", WifiConnection.STATUS_OK, 64);
		
//		router.connectToDeviceWithLink(wifiAP, ibRouterWifi);
//		wifiAP.connectToDeviceWithLink(modem, ibRouterWifi);
//		modem.connectToDeviceWithLink(router, ibRouterWifi);
//		computer.connectToDeviceWithLink(router, ethernetCable);
//		iPhone.connectToDeviceWithLink(wifiAP, wifi);
//		android.connectToDeviceWithLink(wifiAP, wifi);
		
		this.addNetworkElement(computer);
		this.addNetworkElement(router);
//		this.addNetworkElement(modem);
//		this.addNetworkElement(wifiAP);
		this.addNetworkElement(iPhone);
		this.addNetworkElement(android);
		
//		this.addNetworkElement(ibRouterWifi);
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
        
        
        Set<NetworkElement> set = new HashSet<NetworkElement>();
        set.add(this.getNetworkElement("PC"));
        set.add(this.getNetworkElement("Ethernet PC"));
        
        List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
        possibleCombinations.add(set);

        NetworkElement router = this.getNetworkElement("Router");
        this.addPossibleFailure(NoIPFailure.class, router);
        
        NetworkElement pc = this.getNetworkElement("PC");
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
		return new HANScenario2DPortrayal(this, 400, 400);
//		return null;
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
//		return new HANScenario3DPortrayal(this, 100, 100, 100);
		return null;
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
		if (status.equals(HANScenario.STATUS_RAINY)) {
            return this.getRainyPenalties();
        } else if (status.equals(HANScenario.STATUS_SNOWY)) {
            return this.getSnowyPenalties();
        } else if (status.equals(HANScenario.STATUS_SUNNY)){
        	return this.getSunnyPenalties();
        } else {
            throw new UnsupportedScenarioStatusException();
        }
	}

	private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(NoIPFailure.class, 1.0);

        
        return penalties;
	}

	private HashMap<Class<? extends Failure>, Double> getSnowyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(NoIPFailure.class, 1.0);

        return penalties;
	}

	private HashMap<Class<? extends Failure>, Double> getRainyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(NoIPFailure.class, 1.0);

        return penalties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(HANScenario.STATUS_SUNNY);
		this.addPossibleStatus(HANScenario.STATUS_RAINY);
		this.addPossibleStatus(HANScenario.STATUS_SNOWY);
	}

	@Override
	public void addPossibleEvents() {
		// TODO Auto-generated method stub
		
	}

}
