package es.upm.dit.gsi.shanks.tutorial.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTest;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Router;
import es.upm.dit.gsi.shanks.tutorial.model.element.links.EthernetLink;
import es.upm.dit.gsi.shanks.tutorial.model.events.ClientGoOutEvent;
import es.upm.dit.gsi.shanks.tutorial.model.events.NewClientEvent;
import es.upm.dit.gsi.shanks.tutorial.model.events.failures.ComputerDisconnectedFailure;
import es.upm.dit.gsi.shanks.tutorial.model.scenario.portrayal.LANScenario2DPortrayal;

public class LANScenario extends Scenario{
	
	public static final String CLOUDY = "CLOUDY";
    public static final String SUNNY = "SUNNY";

	public LANScenario(String id, String initialState, Properties properties)
			throws ShanksException {
		super(id, initialState, properties);
	}

	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws ShanksException {
		return new LANScenario2DPortrayal(this, 100, 100);
	}

	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws ShanksException {
		// TODO Auto-generated method stub
		return null;
	}

	
	//it is a simple scenario so the states aren't necessary
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(CLOUDY);
        this.addPossibleStatus(SUNNY);
		
	}

	@Override
	public void addNetworkElements() throws ShanksException {
		Device computer1 = new Computer("Computer 1", Computer.STATUS_OFF, false);
		Device computer2 = new Computer("Computer 2", Computer.STATUS_OFF, false);
		Device computer3 = new Computer("Computer 3", Computer.STATUS_OFF, false);
		Device computer4 = new Computer("Computer 4", Computer.STATUS_OFF, false);
		Device computer5 = new Computer("Computer 5", Computer.STATUS_OFF, false);
		
		Device router = new Router("Router", Router.STATUS_OK, true);
		
		Link l1 = new EthernetLink("L1", EthernetLink.STATUS_OK, 2);
		Link l2 = new EthernetLink("L2", EthernetLink.STATUS_OK, 2);
		Link l3 = new EthernetLink("L3", EthernetLink.STATUS_OK, 2);
		Link l4 = new EthernetLink("L4", EthernetLink.STATUS_OK, 2);
		Link l5 = new EthernetLink("L5", EthernetLink.STATUS_OK, 2);
		
		l1.connectDevices(router, computer1);
		l2.connectDevices(router, computer2);
		l3.connectDevices(router, computer3);
		l4.connectDevices(router, computer4);
		l5.connectDevices(router, computer5);
		
		this.addNetworkElement(computer1);
        this.addNetworkElement(computer2);
        this.addNetworkElement(computer3);
        this.addNetworkElement(computer4);
        this.addNetworkElement(computer5);
        this.addNetworkElement(router);
        this.addNetworkElement(l1);
        this.addNetworkElement(l2);
        this.addNetworkElement(l3);
        this.addNetworkElement(l4);
        this.addNetworkElement(l5);


	}

	@Override
	public void addPossibleFailures() {
		Set<NetworkElement> comp1 = new HashSet<NetworkElement>();
		comp1.add(this.getNetworkElement("Computer 1"));	
		Set<NetworkElement> comp2 = new HashSet<NetworkElement>();
		comp2.add(this.getNetworkElement("Computer 2"));	
		Set<NetworkElement> comp3 = new HashSet<NetworkElement>();
		comp3.add(this.getNetworkElement("Computer 3"));	
		Set<NetworkElement> comp4 = new HashSet<NetworkElement>();
		comp4.add(this.getNetworkElement("Computer 4"));
		Set<NetworkElement> comp5 = new HashSet<NetworkElement>();
		comp5.add(this.getNetworkElement("Computer 5"));		
		List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
		possibleCombinations.add(comp1);
		possibleCombinations.add(comp2);
		possibleCombinations.add(comp3);
		possibleCombinations.add(comp4);
		possibleCombinations.add(comp5);
		
//        this.addPossibleFailure(ComputerDisconnectedFailure.class, possibleCombinations);
		
	}

	@Override
	public void addPossibleEvents() {
		Set<NetworkElement> comp1 = new HashSet<NetworkElement>();
		comp1.add(this.getNetworkElement("Computer 1"));	
		Set<NetworkElement> comp2 = new HashSet<NetworkElement>();
		comp2.add(this.getNetworkElement("Computer 2"));	
		Set<NetworkElement> comp3 = new HashSet<NetworkElement>();
		comp3.add(this.getNetworkElement("Computer 3"));	
		Set<NetworkElement> comp4 = new HashSet<NetworkElement>();
		comp4.add(this.getNetworkElement("Computer 4"));
		Set<NetworkElement> comp5 = new HashSet<NetworkElement>();
		comp5.add(this.getNetworkElement("Computer 5"));		
		List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
		possibleCombinations.add(comp1);
		possibleCombinations.add(comp2);
		possibleCombinations.add(comp3);
		possibleCombinations.add(comp4);
		possibleCombinations.add(comp5);

//		this.addPossibleEventsOfNE(NewClientEvent.class, possibleCombinations);
//		this.addPossibleEventsOfNE(ClientGoOutEvent.class, possibleCombinations);
        this.addPossibleEventsOfNE(ComputerDisconnectedFailure.class, possibleCombinations);



	}

	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws ShanksException {
		// TODO Auto-generated method stub
		return null;
	}

}
