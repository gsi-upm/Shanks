package es.upm.dit.gsi.shanks.shanks_isp_module.agent.action;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Computer;

public class RepairComputer extends SimpleShanksAgentAction{

	public RepairComputer(String id, Steppable launcher) {
		super(id, launcher);
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_ANTIVIRUS, true);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_CPUFREQ, 3000);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_ETHERNET_CONNECTION, true);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_FANSPEED, 2000);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_FIREWALL, true);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_POWER, true);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_RAM, 0.25);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_TEMPERATURE, 35);

	}

	@Override
	public void addAffectedScenario(Scenario scen) {
		
	}

	@Override
	public void interactWithNE() {
		
	}

}
