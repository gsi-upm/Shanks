package es.upm.dit.gsi.shanks.agent.action;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.workerroom.element.link.EthernetLink;

public class RepairWire extends SimpleShanksAgentAction{

	public RepairWire(String id, Steppable launcher) {
		super(id, launcher);
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(EthernetLink.class, EthernetLink.PROPERTY_PACKETLOSSRATIO, 0.001);
		
	}

	@Override
	public void addAffectedScenario(Scenario scen) {
	}

	@Override
	public void interactWithNE() {
	}

}
