package es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.datacenter.model.scenario.DataCenterScenario;
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
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.model.workerroom.element.link.EthernetLink;
import es.upm.dit.gsi.shanks.model.workerroom.scenario.WorkerRoomScenario;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.element.IntranetRouter;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.portrayal.EnterpriseScenario2DPortrayal;

public class EnterpriseScenario extends ComplexScenario{
	
	public static final String STORM = "STORM";
    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String SUNNY = "SUNNY";

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
	    p.put(MyScenario.CLOUDY_PROB, "10.0");
		this.addScenario(WorkerRoomScenario.class, "Worker Room 1", SUNNY, p, "Router", "EL1");
		this.addScenario(WorkerRoomScenario.class, "Worker Room 2", SUNNY, p, "Router", "EL2");
		this.addScenario(WorkerRoomScenario.class, "Worker Room 3", SUNNY, p, "Router", "EL3");
		this.addScenario(DataCenterScenario.class, "Data Center", DataCenterScenario.STATUS_NORMAL, p, Values.DATA_CENTER_ROUTER_ID, "EL4");

		
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
	}

	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		Device router = new IntranetRouter("Intranet Router", IntranetRouter.STATUS_OK, true);
		Link ethernetLink1 = new EthernetLink("EL1", EthernetLink.STATUS_OK, 2);
		Link ethernetLink2 = new EthernetLink("EL2", EthernetLink.STATUS_OK, 2);
		Link ethernetLink3 = new EthernetLink("EL3", EthernetLink.STATUS_OK, 2);
		Link ethernetLink4 = new EthernetLink("EL4", EthernetLink.STATUS_OK, 2);
		
		this.addNetworkElement(ethernetLink4);
		this.addNetworkElement(ethernetLink3);
		this.addNetworkElement(ethernetLink2);
		this.addNetworkElement(ethernetLink1);
		this.addNetworkElement(router);
		
		router.connectToLink(ethernetLink4);
		router.connectToLink(ethernetLink3);
		router.connectToLink(ethernetLink2);
		router.connectToLink(ethernetLink1);
	}

	@Override
	public void addPossibleFailures() {
		
	}

	@Override
	public void addPossibleEvents() {
			
	}

	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws UnsupportedScenarioStatusException {
		return null;
	}

}
