package es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.datacenter.model.scenario.DataCenterScenario;
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
import es.upm.dit.gsi.shanks.networkattacks.util.failures.RouterFailure;
import es.upm.dit.gsi.shanks.networkattacks.util.failures.WireBroken;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.EthernetLink;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.Values;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.element.CompanyRouter;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.portrayal.EnterpriseScenario2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.scenario.WorkerRoomScenario;

public class EnterpriseScenario extends ComplexScenario{
	
	public static final String STORM = "STORM";
    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String SUNNY = "SUNNY";
	public static final String STATUS_NORMAL = "Normal";
	public static final String STATUS_UNDER_ATTACK = "UnderAttack";
    

	public EnterpriseScenario(String type, String initialState,
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
	    this.addScenario(DataCenterScenario.class, Values.DATA_CENTER_SCENARIO_ID, DataCenterScenario.STATUS_NORMAL, p, Values.DATA_CENTER_ROUTER_ID, Values.DATA_CENTER_LINK_ID);
		for (int i=0; i<Values.NUMBER_OF_WORKERROOMS; i++){
			this.addScenario(WorkerRoomScenario.class, Values.WORKER_ROOM_SCENARIO_ID+i, WorkerRoomScenario.STATUS_NORMAL, p, Values.WORKER_ROOM_ROUTER_ID, Values.WORKER_ROOM_LINK_ID+i);
		}
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new EnterpriseScenario2DPortrayal(this, 150, 150);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return null;
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(EARTHQUAKE);
		this.addPossibleStatus(STORM);
		this.addPossibleStatus(SUNNY);
		this.addPossibleStatus(STATUS_NORMAL);
		this.addPossibleStatus(STATUS_UNDER_ATTACK);
	}

	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {

		// Adding the Enterprise Router.
		Device companyRouter = new CompanyRouter(Values.ENTERPRISE_GATEWAY_ID);
		this.addNetworkElement(companyRouter);
		
		// Adding data center subnet external link. 
		Link dcLink = new EthernetLink(Values.DATA_CENTER_LINK_ID, 2.0);
		companyRouter.connectToLink(dcLink);
		this.addNetworkElement(dcLink);
	
		// Adding WorkerRoom external links.
		for(int i=0; i<Values.NUMBER_OF_WORKERROOMS; i++){
			Link workerRoomLink = new EthernetLink(Values.WORKER_ROOM_LINK_ID+i, 2.0);
			companyRouter.connectToLink(workerRoomLink);
			this.addNetworkElement(workerRoomLink);
		}
	}

	@Override
	public void addPossibleFailures(){
		List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
		for(int i=0; i<Values.NUMBER_OF_WORKERROOMS; i++){
			Set<NetworkElement> set = new HashSet<NetworkElement>();
			set.add(this.getNetworkElement(Values.WORKER_ROOM_LINK_ID+i));
			possibleCombinations.add(set);
		}
		this.addPossibleFailure(WireBroken.class, possibleCombinations);
		this.addPossibleFailure(RouterFailure.class, this.getNetworkElement(Values.ENTERPRISE_GATEWAY_ID));
	}
	
	@Override
	public void addPossibleEvents() {
//		this.addPossibleEventsOfNE(RouterCongestion.class, this.getNetworkElement("Intranet LANRouter"));
	}

	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws UnsupportedScenarioStatusException {

		HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
	    return penalties;
	}

}