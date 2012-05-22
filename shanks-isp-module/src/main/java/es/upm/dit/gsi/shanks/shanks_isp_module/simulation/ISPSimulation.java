package es.upm.dit.gsi.shanks.shanks_isp_module.simulation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import sim.engine.Schedule;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.datacenter.agent.SysAdmin;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.hackerhan.model.scenario.HackerHANScenario;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.Values;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.chart.chartPainter;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario.ISPScenario;
import es.upm.dit.gsi.shanks.workerroom.agent.RepairWireAgent;

public class ISPSimulation extends ShanksSimulation {

	private static final long serialVersionUID = 1778288778609950190L;

	public static final String CONFIGURATION = "Configuration";

	public ISPSimulation(long seed, Class<? extends Scenario> scenarioClass,
			String scenarioID, String initialState, Properties properties)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			UnsupportedNetworkElementFieldException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException, DuplicatedAgentIDException,
			DuplicatedActionIDException {
		super(seed, scenarioClass, scenarioID, initialState, properties);
	}

	@Override
	public void registerShanksAgents() throws DuplicatedAgentIDException,
			DuplicatedActionIDException {
		RepairWireAgent worker = new RepairWireAgent("Repair Wire Worker");
		this.registerShanksAgent(worker);
		
		// Add hackers
		ArrayList<Scenario> hackerHans = new ArrayList<Scenario>();
		Iterator<Scenario> scenarios = ((ComplexScenario)this.getScenario()).getScenarios().iterator();
		for (int i = 0; i< Values.NUMBER_OF_HANS; i++){
			Scenario hackerHan = null;
			while(scenarios.hasNext()){
				Scenario next = scenarios.next();
				if (next instanceof HackerHANScenario && !hackerHans.contains(next)){
					hackerHan = next;
					hackerHans.add(next);
					break; // Shame on me. But I do not have time to do this properly.
				}
			}
			Hacker hacker = new Hacker("Hacker" + i, 
					"../shanks-network-attacks-simulation/src/main/java/es/upm/dit/gsi/shanks/networkattacks/util/Hacker.net", (HackerHANScenario)hackerHan);
			this.registerShanksAgent(hacker);
		}
		
		// Add sysadmin
		SysAdmin bofh = new SysAdmin("elAutoestopista"); // Tek ma'tek, Tek ma'te!
		this.registerShanksAgent(bofh);
	}

	public void addSteppables() {
		schedule.scheduleRepeating(Schedule.EPOCH, 3, new chartPainter(), 50);
	}

	public static void main(String[] args) {
		try {
			Properties scenarioProperties = new Properties();
			scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			ISPSimulation tut = new ISPSimulation(System.currentTimeMillis(),
					ISPScenario.class, Values.ISP_SCENARIO_ID,
					ISPScenario.STATUS_NORMAL, scenarioProperties);
			tut.start();
			do
				if (!tut.schedule.step(tut))
					break;
			while (tut.schedule.getSteps() < 8001);
			tut.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
