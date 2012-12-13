package es.upm.dit.gsi.shanks.magneto.agent.action;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ServerGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class UniversalFixAction extends SimpleShanksAgentAction{

	public UniversalFixAction(String id, Steppable launcher) {
		super(id, launcher);
		
	}

		public static final String FIX = "Fix";
	
	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedStatus(UserGateway.class, UserGateway.STATUS_OK, true);
		this.addPossibleAffectedStatus(ISPGateway.class, ISPGateway.STATUS_OK, true);
		this.addPossibleAffectedStatus(ServerGateway.class, ServerGateway.STATUS_OK, true);
		this.addPossibleAffectedStatus(Server.class, Server.STATUS_OK, true);
	}

	@Override
	public void addAffectedScenario(Scenario scen) {
		
	}

	@Override
	public void interactWithNE() {
		
	}

}
