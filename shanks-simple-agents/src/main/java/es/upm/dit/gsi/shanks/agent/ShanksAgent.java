package es.upm.dit.gsi.shanks.agent;

import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Message;
import jason.asSyntax.Literal;

import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

/**
 * @author a.carrera
 *
 */
public abstract class ShanksAgent extends AgArch implements Steppable,
		Stoppable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4744430503147830611L;

	private ShanksSimulation simulation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState simulation) {
		this.simulation = (ShanksSimulation) simulation;
		this.getTS().reasoningCycle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jason.architecture.AgArch#stop()
	 */
	public void stop() {

	}

	/**
	 * @return The simulation to get data
	 */
	public ShanksSimulation getSimulation() {
		return simulation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jason.architecture.AgArch#perceive()
	 */
	abstract public List<Literal> perceive();

	/*
	 * (non-Javadoc)
	 * 
	 * @see jason.architecture.AgArch#act(jason.asSemantics.ActionExec,
	 * java.util.List)
	 */
	abstract public void act(ActionExec action, List<ActionExec> feedback);

	/*
	 * (non-Javadoc)
	 * 
	 * @see jason.architecture.AgArch#checkMail()
	 */
	abstract public void checkMail();

	/*
	 * (non-Javadoc)
	 * 
	 * @see jason.architecture.AgArch#sendMsg(jason.asSemantics.Message)
	 */
	abstract public void sendMsg(Message message);

}