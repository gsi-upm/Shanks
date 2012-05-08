package es.upm.dit.gsi.shanks.shanks_isp_module.model.element.device;

import java.util.HashMap;
import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class ISPGateway extends Device{
	
	public static final String PROPERTY_CONNECTIONS = "Hans Connected";
	public static final String PROPERTY_POWER = "Power Supply";
	
	public static final String STATUS_OK = "Ok";
	public static final String STATUS_OFF = "Off";
	
	private HashMap<Scenario, Boolean> connections;

	public ISPGateway(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
		this.connections = new HashMap<Scenario, Boolean>();
	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_CONNECTIONS, connections);
		this.addProperty(PROPERTY_POWER, true);
	}

	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
			HashMap<String, Boolean> status = this.getStatus();
			if(status.get(STATUS_OFF)){
				this.updatePropertyTo(PROPERTY_POWER, false);
			}else if(status.get(STATUS_OK)){
				this.updatePropertyTo(PROPERTY_POWER, true);
			}
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
			Boolean power = (Boolean) this.getProperty(PROPERTY_POWER);
			if(power){
				this.updateStatusTo(STATUS_OK, true);
			}else if(!power){
				this.updateStatusTo(STATUS_OFF, false);
			}
	}

	@Override
	public void setPossibleStates() {
		 this.addPossibleStatus(STATUS_OFF);
		 this.addPossibleStatus(STATUS_OK);
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
