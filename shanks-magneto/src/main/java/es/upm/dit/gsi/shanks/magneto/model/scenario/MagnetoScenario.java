package es.upm.dit.gsi.shanks.magneto.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Computer;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ServerGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.link.MagnetoLink;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.HGProviderDownOrDisconnected;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.HGUserDisconnected;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.HGUserExternalConnectivityFailure;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.ISPCongestion;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.ISPRoutingProblem;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.InternalProviderConnectivityFailure;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.LocalUserConnectivityFailure;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.OVNManagementBadlyConfigured;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.ProviderHANCongestion;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.ServiceMalFunction;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.ServiceProvideDeviceDownOrDisconnected;
import es.upm.dit.gsi.shanks.magneto.model.event.failures.UserHANCongestion;
import es.upm.dit.gsi.shanks.magneto.model.scenario.portrayal.MagnetoScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
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
		Device oss = new Server("OSS", Server.STATUS_OK, false);
		Device ossGateway = new ServerGateway("OSS Gateway", ServerGateway.STATUS_OK, false);
		
		
		Link compToUserGway = new MagnetoLink("L1", MagnetoLink.STATUS_OK, 2);
		Link userGwayToISPGway = new MagnetoLink("L2", MagnetoLink.STATUS_OK, 2);
		Link ispGwayToServerGway = new MagnetoLink("L3", MagnetoLink.STATUS_OK, 2);
		Link serverGwayToServer = new MagnetoLink("L4", MagnetoLink.STATUS_OK, 2);
		Link ispToOssGateway = new MagnetoLink("L5", MagnetoLink.STATUS_OK, 2);
		Link ossGatewayToOss = new MagnetoLink("L6", MagnetoLink.STATUS_OK, 2);
	
		compToUserGway.connectDevices(userComputer, userGateway);
		userGwayToISPGway.connectDevices(userGateway, ispGateway);
		ispGwayToServerGway.connectDevices(ispGateway, serverGateway);
		serverGwayToServer.connectDevices(serverGateway, server);
		ispToOssGateway.connectDevices(ispGateway, ossGateway);
		ossGatewayToOss.connectDevices(ossGateway, oss);
		
		this.addNetworkElement(userComputer);
		this.addNetworkElement(userGateway);
		this.addNetworkElement(ispGateway);
		this.addNetworkElement(serverGateway);
		this.addNetworkElement(server);
		this.addNetworkElement(ossGateway);
		this.addNetworkElement(oss);
		
		this.addNetworkElement(compToUserGway);
		this.addNetworkElement(userGwayToISPGway);
		this.addNetworkElement(ispGwayToServerGway);
		this.addNetworkElement(serverGwayToServer);
		this.addNetworkElement(ossGatewayToOss);
		this.addNetworkElement(ispToOssGateway);
		
	
	}


	@Override
	public void addPossibleFailures() {
		
		this.addPossibleFailure(UserHANCongestion.class, this.getNetworkElement("User Gateway"));
		
		this.addPossibleFailure(LocalUserConnectivityFailure.class, this.getNetworkElement("User Gateway"));
		
		List<Set<NetworkElement>> list = new ArrayList<Set<NetworkElement>>();
		Set<NetworkElement> set = new HashSet<NetworkElement>();
		set.add(this.getNetworkElement("User Gateway"));
		set.add(this.getNetworkElement("ISP Gateway"));
		list.add(set);
		this.addPossibleFailure(HGUserDisconnected.class, list);
		
		this.addPossibleFailure(HGUserExternalConnectivityFailure.class, this.getNetworkElement("User Gateway"));
		
		this.addPossibleFailure(HGProviderDownOrDisconnected.class, list);
		
		this.addPossibleFailure(InternalProviderConnectivityFailure.class, this.getNetworkElement("Server Gateway"));
		
		this.addPossibleFailure(ISPCongestion.class, list);
		
		this.addPossibleFailure(ISPRoutingProblem.class, list);
		
		this.addPossibleFailure(OVNManagementBadlyConfigured.class, this.getNetworkElement("Server"));
	
		this.addPossibleFailure(ProviderHANCongestion.class, this.getNetworkElement("Server Gateway"));

		this.addPossibleFailure(ServiceMalFunction.class, this.getNetworkElement("Server"));

		this.addPossibleFailure(ServiceProvideDeviceDownOrDisconnected.class, this.getNetworkElement("Server Gateway"));

	
	
	}

	@Override
	public void addPossibleEvents() {
	}

	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws ShanksException {
		// TODO Auto-generated method stub
		return null;
	}

}
