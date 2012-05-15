package es.upm.dit.gsi.shanks.hackerhan.attack;

import java.util.ArrayList;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server;

/**
 * Class to represent a SQL Injection Attack.
 * 
 */
public class SQLInjection implements Attack {

	private Hacker hacker;

	private ShanksSimulation sim;

	private static int numberOfAttacks = 0;
	private static int successfullAttacks = 0;
	
	private ArrayList<Integer> ports;
	private int steps;

	private boolean running;
	private boolean success;
	private String accessID;

	public SQLInjection(Hacker hacker, ShanksSimulation sim) {
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
		this.steps = sim.random.nextInt(Values.ATTACK_MAX_STEPS);
		this.accessID = hacker.getID();
	}

	@Override
	public void execute() {
		if (this.steps != 0) {
			this.running = true;
			try {
				Server sqlServer = (Server) ((ComplexScenario) ((ComplexScenario) this.sim
						.getScenario())
						.getScenario(Values.ENTERPRISE_SCENARIO_ID))
						.getScenario(Values.DATA_CENTER_SCENARIO_ID)
						.getNetworkElement(Values.SQL_SERVER_ID);
				int vulnerability = this.hacker.getAbility() * (int) ((Double) sqlServer
						.getProperty(Server.PROPERTY_VULNERABILITY) * 100);

				int rand = this.sim.random.nextInt(100);
				if (rand < vulnerability) {
					this.success = true;
					successfullAttacks++;
					// TODO: set property "hacked"??
				} else {
					sqlServer.addProperty(Server.PROPERTY_LOG, (Integer)sqlServer.getProperty(Server.PROPERTY_LOG) +1 );
					sqlServer.logAccess(accessID);
				// TODO: Increase log "weirdness"
				}

			} catch (ScenarioNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.steps -= 1;
		numberOfAttacks++;
	}

	@Override
	public boolean isSuccessful() {
		return this.success;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
	
	public static double ratioAttacks(){
		return successfullAttacks/numberOfAttacks;
	}

	@Override
	public void stop() {
		this.steps = 0;
		this.running = false;
	}

	@Override
	public int numberSteps() {
		return this.steps;
	}

	public void setAccessID(String newID){
		this.accessID = newID;
	}
}
