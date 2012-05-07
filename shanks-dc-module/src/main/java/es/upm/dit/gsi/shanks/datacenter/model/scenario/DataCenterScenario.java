package es.upm.dit.gsi.shanks.datacenter.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Computer;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Router;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Server;
import es.upm.dit.gsi.shanks.datacenter.model.element.link.EthernetCable;
import es.upm.dit.gsi.shanks.datacenter.model.scenario.portrayal.DataCenterScenario2DPortrayal;
import es.upm.dit.gsi.shanks.datacenter.model.scenario.portrayal.DataCenterScenario3DPortrayal;
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

public class DataCenterScenario extends Scenario {

	// TODO make that the status has influence.
	public static final String STATUS_NORMAL = "Normal";
	public static final String STATUS_UNDER_ATTACK = "UnderAttack";

	public DataCenterScenario(String id, String initialState,
			Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException {
		super(id, initialState, properties);
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new DataCenterScenario2DPortrayal(this, 100, 100);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new DataCenterScenario3DPortrayal(this, 100, 100, 100);
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(DataCenterScenario.STATUS_UNDER_ATTACK);
		this.addPossibleStatus(DataCenterScenario.STATUS_NORMAL);
	}

	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {

		Router intranetRouter = new Router(Values.INTRANET_ROUTER_ID + "@"
				+ this.getID());
		addNetworkElement(intranetRouter);

		// IT Crow terminals.
		ArrayList<EthernetCable> links = new ArrayList<EthernetCable>();
		for (int i = 0; i < Values.NUMBER_OF_ITCROW; i++) {
			Computer computer = (new Computer(Values.COMPUTER_ID + i + "@"+ this.getID()));
			links.add(new EthernetCable(Values.ETHERNET_ID + i + "@"
					+ this.getID(), Values.ETHERNET_LENGHT));
			computer.connectToDeviceWithLink(intranetRouter, links.get(i));
			addNetworkElement(computer);
		}

		
		// Servers
		Server ldap = new Server(Values.SERVER_ID + "LDAP@" + this.getID());
		links.add(new EthernetCable(Values.ETHERNET_ID + (links.size() - 1)
				+ "@" + this.getID(), Values.ETHERNET_LENGHT));
		ldap.connectToDeviceWithLink(intranetRouter, links.get(links.size()-1));
		addNetworkElement(ldap);
		
		Server external = new Server(Values.SERVER_ID+"External@"+this.getID());
		links.add(new EthernetCable(Values.ETHERNET_ID + (links.size() - 1)
				+ "@" + this.getID(), Values.ETHERNET_LENGHT));
		external.connectToDeviceWithLink(intranetRouter, links.get(links.size()-1));
		addNetworkElement(external);
		
		Server bbdd = new Server(Values.SERVER_ID+"BBDD@"+this.getID());
		links.add(new EthernetCable(Values.ETHERNET_ID + (links.size() - 1)
				+ "@" + this.getID(), Values.ETHERNET_LENGHT));
		bbdd.connectToDeviceWithLink(intranetRouter, links.get(links.size()-1));
		addNetworkElement(bbdd);
		
		Server bbddReplica = new Server(Values.SERVER_ID+"BBDD@"+this.getID());
		links.add(new EthernetCable(Values.ETHERNET_ID + (links.size() - 1)
				+ "@" + this.getID(), Values.ETHERNET_LENGHT));
		bbddReplica.connectToDeviceWithLink(bbdd, links.get(links.size()-1));
		addNetworkElement(bbddReplica);

		//Adding links
		for(EthernetCable link: links){
				this.addNetworkElement(link);
		}

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
			String status) throws UnsupportedScenarioStatusException {
		// TODO Auto-generated method stub
		return null;
	}

}
