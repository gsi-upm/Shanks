package es.upm.dit.gsi.shanks.tutorial.agent.action;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;

public class ConnectComputerAction extends SimpleShanksAgentAction{

	public ConnectComputerAction(String id, Steppable launcher) {
		super(id, launcher);
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_ETHERNET_CONNECTION, true);

		
	}

	@Override
	public void addAffectedScenario(Scenario scen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactWithNE() {
		// TODO Auto-generated method stub
		
	}

}
