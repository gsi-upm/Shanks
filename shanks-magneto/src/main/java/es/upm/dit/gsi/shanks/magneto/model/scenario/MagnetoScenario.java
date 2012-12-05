package es.upm.dit.gsi.shanks.magneto.model.scenario;

import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Computer;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ServerGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.link.MagnetoLink;
import es.upm.dit.gsi.shanks.magneto.model.scenario.portrayal.MagnetoScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;

public class MagnetoScenario extends Scenario{

	public static final String SUNNY = "Sunny";
	
	public MagnetoScenario(String id, String initialState, Properties properties)
			throws ShanksException {
		super(id, initialState, properties);
		
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws ShanksException {
		return new MagnetoScenarioPortrayal(this, 175, 175);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws ShanksException {
		
		return null;
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(SUNNY);
		
	}

	@Override
	public void addNetworkElements() throws ShanksException {
		Device userComputer = new Computer("User Computer", Computer.STATUS_ON, false);
		Device userGateway = new UserGateway("User Gateway", UserGateway.STATUS_OK, false);
		Device ispGateway = new ISPGateway("ISP Gateway", ISPGateway.STATUS_OK, true);
		Device serverGateway = new ServerGateway("Server Gateway", ServerGateway.STATUS_OK, false);
		Device server = new Server("Server", Server.STATUS_OK, false);
		
		Link compToUserGway = new MagnetoLink("L1", MagnetoLink.STATUS_OK, 2);
		Link userGwayToISPGway = new MagnetoLink("L2", MagnetoLink.STATUS_OK, 2);
		Link ispGwayToServerGway = new MagnetoLink("L3", MagnetoLink.STATUS_OK, 2);
		Link serverGwayToServer = new MagnetoLink("L4", MagnetoLink.STATUS_OK, 2);
	
		compToUserGway.connectDevices(userComputer, userGateway);
		userGwayToISPGway.connectDevices(userGateway, ispGateway);
		ispGwayToServerGway.connectDevices(ispGateway, serverGateway);
		serverGwayToServer.connectDevices(serverGateway, server);
		
		this.addNetworkElement(userComputer);
		this.addNetworkElement(userGateway);
		this.addNetworkElement(ispGateway);
		this.addNetworkElement(serverGateway);
		this.addNetworkElement(server);
		
		this.addNetworkElement(compToUserGway);
		this.addNetworkElement(userGwayToISPGway);
		this.addNetworkElement(ispGwayToServerGway);
		this.addNetworkElement(serverGwayToServer);
		
	
	}

	@Override
	public void addPossibleFailures() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPossibleEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws ShanksException {
		// TODO Auto-generated method stub
		return null;
	}

}
