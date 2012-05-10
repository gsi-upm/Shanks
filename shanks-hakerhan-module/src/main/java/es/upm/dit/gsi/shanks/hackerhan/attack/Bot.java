package es.upm.dit.gsi.shanks.hackerhan.attack;

import jason.asSemantics.Message;

import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;

/**
 * Class to represent the bot to be deployed in a Hacked HAN
 * 
 * @author Alberto Mardomingo
 */
public class Bot extends SimpleShanksAgent{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean attacking;
	private String target;

	public Bot(String id) {
		super(id);
		attacking = false;
	}

	@Override
	public void checkMail() {
		List<Message> messages = this.getInbox();
		// Message syntax: "StartAttack:Target"
		for(Message message: messages){
			if(((String)message.getPropCont()).contains(Values.ATTACK_ORDER)){
				// Jaffa, kree!!
				this.target = ((String)message.getPropCont()).split(":")[1];
				this.attacking = true;
			}
		}
		// TODO: if "StartAttack" --> launch the attack
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		if(attacking){
			// Jaffa, Tal shak! 
//			Gateway gateway = (Gateway)simulation.getScenario().getNetworkElement(target);
			// TODO: increase load count.
		}
	}

}
