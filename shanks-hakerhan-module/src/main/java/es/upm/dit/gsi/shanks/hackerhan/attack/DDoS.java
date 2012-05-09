package es.upm.dit.gsi.shanks.hackerhan.attack;

import jason.asSemantics.Message;

import java.util.ArrayList;

import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;

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
	 * Constructor
	 * 
	 * @param hacker - The hacker launching the attack.
	 */
	public DDoS(Hacker hacker, String targetID){
		super();
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
		return false;
	}
	
	@Override
	public boolean isRunning() {
		// Once the messages have been sent, there is nothing to be done.
		return false;
	}

}
