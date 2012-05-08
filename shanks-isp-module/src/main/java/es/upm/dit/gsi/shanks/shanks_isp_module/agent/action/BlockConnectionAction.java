package es.upm.dit.gsi.shanks.shanks_isp_module.agent.action;

import java.util.Set;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.element.link.CupperLink;

public class BlockConnectionAction extends SimpleShanksAgentAction{

	public BlockConnectionAction(String id, Steppable launcher) {
		super(id, launcher);
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedStatus(CupperLink.class, CupperLink.STATUS_BLOCKED, true);
		
	}

	@Override
	public void addAffectedScenario(Scenario scen) {

		
	}

	@Override
	public void interactWithNE() {

		
	}
	

}
