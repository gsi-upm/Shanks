package es.upm.dit.gsi.shanks.agent;

import jason.JasonException;
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Agent;
import jason.asSemantics.Circumstance;
import jason.asSemantics.Message;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.Literal;
import jason.runtime.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.UnkownAgentException;

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

    private Logger logger = Logger.getLogger(ShanksAgent.class.getName());

    private ShanksSimulation simulation;
    private String id;
    public List<Message> inbox;
    private ShanksJasonAgent agent;
    private String aslFilePath;

    /**
     * Constructor of the agent
     * 
     * @param id
     */
    public ShanksAgent(String id, String aslFilePath) {
        this.id = id;
        this.aslFilePath = aslFilePath;
        this.inbox = new ArrayList<Message>();
    }

    /**
     * @return The id of the agent
     */
    public String getID() {
        return this.id;
    }

    /**
     * Used by other agents to
     * 
     * @param message
     */
    public void putMessegaInInbox(Message message) {
        this.inbox.add(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.engine.Steppable#step(sim.engine.SimState)
     */
    @Override
    public void step(SimState simulation) {
        this.simulation = (ShanksSimulation) simulation;

        agent = new ShanksJasonAgent();
        new TransitionSystem(agent, new Circumstance(), new Settings(), this);
        try {
            agent.initAg(aslFilePath);
            TransitionSystem ts = this.getTS();
            ts.reasoningCycle();
        } catch (JasonException e) {
            logger.severe("JasonException was thrown when agent " + id
                    + " was starting...");
            e.printStackTrace();
        }
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
    public void checkMail() {
        for (Message m : this.inbox) {
            logger.fine("Received message -> ID: " + m.getMsgId() + " Sender: "
                    + m.getSender());
            getTS().getC().getMailBox().add(m);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#sendMsg(jason.asSemantics.Message)
     */
    public void sendMsg(Message message) {
        try {
            message.setSender(this.getID());
            ShanksAgent receiver = this.simulation.getAgent(message
                    .getReceiver());
            receiver.putMessegaInInbox(message);
        } catch (UnkownAgentException e) {
            logger.severe("UnkownAgentException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see jason.architecture.AgArch#canSleep()
     */
    public boolean canSleep() {
        return true;
    }

    /* (non-Javadoc)
     * @see jason.architecture.AgArch#isRunning()
     */
    public boolean isRunning() {
        return true;
    }

    /* (non-Javadoc)
     * @see jason.architecture.AgArch#sleep()
     */
    public void sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
    }
    
    /* (non-Javadoc)
     * @see jason.architecture.AgArch#getAgName()
     */
    public String getAgName() {
        return this.id;
    }

    /**
     * @author a.carrera
     * 
     */
    class ShanksJasonAgent extends Agent {
        public boolean socAcc(Message m) {
            return true;
        }
    }

}
