package es.upm.dit.gsi.shanks.hackerhan.attack;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.creation.CreationShanksAgentCapability;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Server;

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
	
	boolean running;
	boolean success;
	
	public RootShell(Hacker hacker, ShanksSimulation sim, Scenario han){
		super();
		this.hacker = hacker;
		this.sim = sim;
		this.han = han;
		this.success = false;
		this.running = false;
	}
	@Override
	public void execute() {
		try {
			this.running = true;
			Server webServer = (Server) ((ComplexScenario)((ComplexScenario)this.sim.getScenario()).getScenario(Values.ENTERPRISE_SCENARIO_ID))
					.getScenario(Values.DATA_CENTER_SCENARIO_ID).getNetworkElement(Values.WEB_SERVER_ID);
			int vulnerability = (int) ((Double)webServer.getProperty(Server.PROPERTY_VULNERABILITY) * 100);
			
			int rand = this.sim.random.nextInt(100);
			if (rand < vulnerability){
				this.success = true;
				// TODO: set property "hacked"??
			}
			// TODO: Increase log "weirdness" 
			
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
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
			Bot bot = new Bot("Bot" + hacker.getID() + hacker.getBotCount());
			hacker.addBot(bot.getID());
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

}
