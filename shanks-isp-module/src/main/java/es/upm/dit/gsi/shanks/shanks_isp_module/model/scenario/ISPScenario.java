package es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.hackerhan.model.scenario.HackerHANScenario;
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
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.EnterpriseScenario;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.Values;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario.portrayal.ISPScenario2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.link.EthernetLink;
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
	    this.addScenario(EnterpriseScenario.class, Values.ENTERPRISE_SCENARIO_ID, EnterpriseScenario.STATUS_NORMAL, p, Values.ENTERPRISE_GATEWAY_ID, Values.ENTERPRISE_SUSCRIBER_LINK_ID);
	    for (int i=0; i<Values.NUMBER_OF_HANS; i++){
	    	this.addScenario(HackerHANScenario.class, Values.HAN_SCENARIO_ID+i, HackerHANScenario.STATUS_NORMAL, p, Values.HAN_ROUTER_ID, Values.HAN_SUSCRIBER_LINK+i);	    		
	    }
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new ISPScenario2DPortrayal(this, 300, 300);
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
		
		// Adding the ISP router
		Device ispRouter = new RouterDNS(Values.ISP_GATEWAY_ID);
		this.addNetworkElement(ispRouter);
		
		// Adding the enterprise subscription to the ISP
		Link enterpriseSuscription = new EthernetLink(Values.ENTERPRISE_SUSCRIBER_LINK_ID, EthernetLink.STATUS_OK, 2);
		ispRouter.connectToLink(enterpriseSuscription);
		this.addNetworkElement(enterpriseSuscription);
		
		//Adding the HAN's subscriptions to the ISP
		for (int i=0; i<Values.NUMBER_OF_HANS; i++){
			Link hanSubscription = new EthernetLink(Values.HAN_SUSCRIBER_LINK+i, EthernetLink.STATUS_OK, 2);
			ispRouter.connectToLink(hanSubscription);
			this.addNetworkElement(hanSubscription);
		}
	}

	@Override
	public void addPossibleFailures() {
		//TODO ¿really necessary?
//		Set<NetworkElement> set1 = new HashSet<NetworkElement>();
//		set1.add(this.getNetworkElement("LINK1"));
//		Set<NetworkElement> set2 = new HashSet<NetworkElement>();
//		set2.add(this.getNetworkElement("LINK2"));
//		Set<NetworkElement> set3 = new HashSet<NetworkElement>();
//		set3.add(this.getNetworkElement("LINK3"));
//		Set<NetworkElement> set4 = new HashSet<NetworkElement>();
//		set4.add(this.getNetworkElement("LINK4"));
//        List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
//		possibleCombinations.add(set1);
//		possibleCombinations.add(set2);
//		possibleCombinations.add(set3);
//		possibleCombinations.add(set4);
//		this.addPossibleFailure(WireBroken.class, possibleCombinations);
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