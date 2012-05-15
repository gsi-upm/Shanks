package es.upm.dit.gsi.shanks.hackerhan.attack;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.creation.CreationShanksAgentCapability;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;

/**
 * Class to represent a "RootShell" attack,
 * where you try to gain control (or access to a root shell)
 * of the victim machine
 * 
 */
public class RootShell implements Attack {
	
	private Hacker hacker;
	private ShanksSimulation sim;
	
	Thread attackThread;
	int ports;
	
	boolean success;
	
	public RootShell(Hacker hacker, ShanksSimulation sim){
		super();
		this.hacker = hacker;
		this.sim = sim;
		this.ports = 1000;
		this.success = false;
	}
	@Override
	public void execute() {
		/*
		 * If the target is another han, automatic success.
		 * 
		 * If not, scan for open ports. If one is found, checks
		 * his knowledge base (o uses a random variable) to see if 
		 * a vulnerability can be exploit.
		 * 
		 */
		
		//ISPDNS dns = sim.getScenario().getNetworkElement("DNS");
		//Gateway gateway = dns.getRouter("Gateway");
		
		//Gateway gateway = (Gateway) sim.getScenario().getNetworkElement("Gateway");
//		int port = sim.random.nextInt(ports);
//		if(gateway.isPortOpen(hacker.getID(), port)){
//			// Try to attack it.
//		}
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
		return this.attackThread.isAlive();
	}

}
