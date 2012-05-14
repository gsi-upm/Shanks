package es.upm.dit.gsi.shanks.shanks_isp_module.model.element.device;

import java.util.HashMap;
import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

public class ISPGateway extends RouterDNS{
	
	
	private HashMap<Scenario, Boolean> connections;

	public ISPGateway(String id, Scenario parent)
			throws UnsupportedNetworkElementStatusException {
		super(id, parent);
		this.connections = new HashMap<Scenario, Boolean>();
	}

	public void changeConnections(Scenario scen){
		this.connections.put(scen, true);
		System.out.println("ESCENARIO AÑADIDO " + scen.getID());
	}
	
	public void addConnections(ComplexScenario comScen){
		Set<Scenario> scens = comScen.getScenarios();
		for(Scenario s : scens){
			this.changeConnections(s);
		}
	}
	

}
