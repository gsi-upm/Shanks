package es.upm.dit.gsi.shanks.hackerhan.agent;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
/**
 * Hacker agent is a malicious agent that investigate security failures and try 
 * to exploit them. In this simulation the attacks will be centralized in a
 * single company network (not the World Wide Web).
 * 
 * A hacker can start with the necessary knowledge to perform a certain attack,
 * or no knowledge at all. In the course of the simulation a hacker can learn
 * new knowledge about attacks in general, that can lead to improve his own 
 * skills making attacks or acquire new ones.  
 * 
 * There is a small chance that the Hacker network suffer a failure, in that 
 * case the hacker stops all attacks and malicious actions until the failure 
 * is resolved. Also there is a chance that a hacker is traced back from his 
 * victims, in that case the hacker is put down.  
 * 
 * @author Danny
 *
 */
public class Hacker extends SimpleShanksAgent{

	public Hacker(String id) {
		super(id);
		//TODO make that the agent start with or without knowledge 
	}

	@Override
	public void checkMail() {
		// TODO design the interaction between hacker agents throw mail.
		// ¿Attacks throw mail too?
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		// TODO program how the agent decide to make an attack attempt... and the attack. 
		// TODO his second action is share and acquire knowledge. 
	}
	private static final long serialVersionUID = -8386091575218484770L;
}
