package es.upm.dit.gsi.shanks.hackerhan.attack;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;

public class Bot extends SimpleShanksAgent{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean attacking;

	public Bot(String id) {
		super(id);
		attacking = false;
	}

	@Override
	public void checkMail() {
		// TODO: if "StartAttack" --> launch the attack
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		if(attacking){
			//TODO: CHAAAAAAAAAARGEE!!!
		}
	}

}
