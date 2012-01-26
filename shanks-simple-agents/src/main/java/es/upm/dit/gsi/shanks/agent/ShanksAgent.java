package es.upm.dit.gsi.shanks.agent;

import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSyntax.Literal;

import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

public abstract class ShanksAgent extends AgArch implements Steppable, Stoppable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4744430503147830611L;

	abstract public void step(SimState paramSimState);
	abstract public void stop();

	abstract public List<Literal> percieve();

	public void act(ActionExec action, List<ActionExec> feedback) {
		//TOIMP this method
	}
	
	//TOIMP sendMessage method
	//TOIMP checkMail method

}
