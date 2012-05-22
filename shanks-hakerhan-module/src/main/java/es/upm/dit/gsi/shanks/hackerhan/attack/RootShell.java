package es.upm.dit.gsi.shanks.hackerhan.attack;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.creation.CreationShanksAgentCapability;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server;

/**
 * Class to represent a "RootShell" attack,
 * where you try to gain control (or access to a root shell)
 * of the victim machine
 * 
 */
public class RootShell implements Attack {
	
	private Hacker hacker;
	private ShanksSimulation sim;
	
	private Scenario han;
	
	private int steps;
	
	private boolean running;
	private boolean success;
	
	private static int numberOfAttacks = 0;
	private static int successfullAttacks = 0;
	
	public RootShell(Hacker hacker, ShanksSimulation sim){
		super();
		this.hacker = hacker;
		this.sim = sim;
		this.success = false;
		this.running = false;
		this.steps = sim.random.nextInt(Values.ATTACK_MAX_STEPS);
	}
	@Override
	public void execute() {
		try {
			this.running = true;
			Server webServer = (Server) ((ComplexScenario)((ComplexScenario)this.sim.getScenario()).getScenario(Values.ENTERPRISE_SCENARIO_ID))
					.getScenario(Values.DATA_CENTER_SCENARIO_ID).getNetworkElement(Values.WEB_SERVER_ID);
			int vulnerability = this.hacker.getAbility() * (int) ((Double)webServer.getProperty(Server.PROPERTY_VULNERABILITY) * 100);
			
			int rand = this.sim.random.nextInt(100);
			if (rand < vulnerability){
				this.success = true;
				successfullAttacks++;
				// TODO: set property "hacked"??
			} else {
				try {
					webServer.updatePropertyTo(Server.PROPERTY_LOG, (Integer)webServer.getProperty(Server.PROPERTY_LOG) +1 );
				} catch (UnsupportedNetworkElementFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// TODO: Increase log "weirdness"
			}
			numberOfAttacks++;
			
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.steps -= 1;
	}

	@Override
	public boolean isSuccessful() {
		return success;
	}
	
	public void installBot(ShanksSimulation sim){
		try {
			//ISPDNS dns = sim.getScenario().getNetworkElement("DNS");
			
			// TODO: find a han to attack
			
			//Bot bot = new Bot(dns.getRouterID("") + hacker.getID() + hacker.getBotCount());
			Bot bot = new Bot("Bot" + hacker.getID() + hacker.getBotCount(), han);
			hacker.addBot(bot);
			CreationShanksAgentCapability.addNewAgent(sim,bot);
		} catch (Exception e) {
			sim.logger.warning("Could not create bot:");
			sim.logger.warning(e.getMessage());
		}
	}
	
	
	@Override
	public boolean isRunning() {
		return this.running;
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
	
	public static double ratioAttacks(){
		return successfullAttacks/numberOfAttacks;
	}

	public void setHAN(Scenario han){
		this.han = han;
	}
	
	public Scenario getHAN(Scenario han){
		return this.han = han;
	}
}
