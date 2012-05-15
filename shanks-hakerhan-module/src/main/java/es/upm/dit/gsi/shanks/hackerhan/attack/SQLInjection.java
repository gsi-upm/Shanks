package es.upm.dit.gsi.shanks.hackerhan.attack;

import java.util.ArrayList;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Server;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;

/**
 * Class to represent a SQL Injection Attack.
 * 
 */
public class SQLInjection implements Attack {

	private Hacker hacker;
	
	private ShanksSimulation sim;
	
	private ArrayList<Integer> ports;
	
	private boolean running;
	private boolean success;
	
	public SQLInjection(Hacker hacker, ShanksSimulation sim){
		super();
		this.hacker = hacker;
		this.sim = sim;
		this.ports = new ArrayList<Integer>();
		this.ports.add(80); // HTTP
		this.ports.add(443); // Default SQL
		this.ports.add(8080); // Tomcat
		this.ports.add(3306); // Default SQL
		this.running = false;
		this.success = false;
	}
	
	@Override
	public void execute() {
		try {
			this.running = true;
			Server sqlServer = (Server) ((ComplexScenario)((ComplexScenario)this.sim.getScenario()).getScenario(Values.ENTERPRISE_SCENARIO_ID))
					.getScenario(Values.DATA_CENTER_SCENARIO_ID).getNetworkElement(Values.SQL_SERVER_ID);
			int vulnerability = (int) ((Double)sqlServer.getProperty(Server.PROPERTY_VULNERABILITY) * 100);
			
			int rand = this.sim.random.nextInt(100);
			if (rand < vulnerability){
				this.success = true;
				// TODO: set property "hacked"??
			}
			// TODO: Increase log "weirdness" 
			
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}

	@Override
	public boolean isSuccessful() {
		return this.success;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

}
