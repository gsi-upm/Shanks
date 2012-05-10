package es.upm.dit.gsi.shanks.workerroom.agent.action;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.workerroom.model.Values;
import es.upm.dit.gsi.shanks.workerroom.model.element.link.EthernetLink;

public class RepairWire extends SimpleShanksAgentAction{

	public RepairWire(String id, Steppable launcher) {
		super(id, launcher);
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(EthernetLink.class, EthernetLink.PROPERTY_PACKETLOSSRATIO, Values.REPAIR_WIRE_PROB);
		
	}

	@Override
	public void addAffectedScenario(Scenario scen) {
	}

	@Override
	public void interactWithNE() {
	}

}
