package es.upm.dit.gsi.shanks.hackerhan.attack;

import jason.asSemantics.Message;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

/**
 * Class to represent a DDoS attack
 * 
 */
public class DDoS implements Attack {
	
	/**
	 * The hacker
	 */
	private Hacker hacker;

	/**
	 * The target of the attack
	 */
	private String targetID;
	
	/**
	 * Running
	 */
	private boolean running;
	
	/**
	 * Simulation
	 */
	private ShanksSimulation sim;
	
	/**
	 * Constructor
	 * 
	 * @param hacker - The hacker launching the attack.
	 */
	public DDoS(Hacker hacker, String targetID, ShanksSimulation sim){
		super();
		this.sim = sim;
		this.hacker = hacker;
		this.targetID = targetID;
	}
	/**
	 * Launchs the attack.
	 * Send a message to every bot so they can start flooding the
	 * company gateway.
	 */
	@Override
	public void execute() {
		this.running = true;
		// Order every bot to launch an attack
		for(String bot: hacker.getBots()){
			// TODO: send a message to the bot.
			Message msg = new Message();
			msg.setReceiver(bot);
			msg.setMsgId("Start");
			msg.setPropCont(Values.ATTACK_ORDER + ":" + this.targetID);
			hacker.sendMsg(msg);
		}
	}

	/**
	 * If the attack has been successful.
	 * 
	 * @return true - If the gateway is down.
	 */
	@Override
	public boolean isSuccessful() {
		// TODO Check if the company gateway is still up.
		RouterDNS router = (RouterDNS)this.sim.getScenario().getNetworkElement(targetID);
		HashMap<String, Boolean> status = router.getStatus();
		
		for (String statusID : status.keySet()){
			if (status.get(statusID) && !statusID.equalsIgnoreCase(RouterDNS.STATUS_OK))
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isRunning() {
		if (isSuccessful())
			this.running = false;
		// Once the messages have been sent, there is nothing to be done.
		return running;
	}

}
