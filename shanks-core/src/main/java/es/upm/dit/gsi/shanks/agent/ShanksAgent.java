package es.upm.dit.gsi.shanks.agent;

import jason.JasonException;
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Agent;
import jason.asSemantics.Circumstance;
import jason.asSemantics.Message;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.runtime.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.action.ShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.action.exception.UnknownShanksAgentActionException;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
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
    private HashMap<String, Class<? extends ShanksAgentAction>> actions;

    /**
     * Constructor of the agent
     * 
     * @param id
     * @throws DuplicatedActionIDException
     */
    public ShanksAgent(String id, String aslFilePath)
            throws DuplicatedActionIDException {
        this.id = id;
        this.aslFilePath = aslFilePath;
        this.inbox = new ArrayList<Message>();
        this.actions = new HashMap<String, Class<? extends ShanksAgentAction>>();
        this.configActions();
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

    /**
     * Add all possible actions to the agent
     * 
     * @throws DuplicatedActionIDException
     */
    abstract public void configActions() throws DuplicatedActionIDException;

    public void addAction(String actionID,
            Class<? extends ShanksAgentAction> action)
            throws DuplicatedActionIDException {
        if (!this.actions.containsKey(actionID)) {
            this.actions.put(actionID, action);
        } else {
            throw new DuplicatedActionIDException(actionID, this.getID());
        }
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
        Circumstance cir;
        if (this.getTS() == null || this.getTS().getC() == null) {
            cir = new Circumstance();
        } else {
            cir = this.getTS().getC();
        }
        new TransitionSystem(agent, cir, new Settings(), this);
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
    public List<Literal> perceive() {
        if (!isRunning())
            return null;
        if (this.getSimulation() == null)
            return null;

        return this.updateBeliefs(this.getSimulation());
    }

    /**
     * Update beliefs of the agent
     * 
     * @param simulation
     * @return new beliefs of the agent
     */
    abstract public List<Literal> updateBeliefs(ShanksSimulation simulation);

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#act(jason.asSemantics.ActionExec,
     * java.util.List)
     */
    public void act(ActionExec action, List<ActionExec> feedback) {
        if (!isRunning())
            return;
        if (this.getSimulation() == null)
            return;

        boolean result = false;

        Structure actionStructure = action.getActionTerm();
        String actionID = actionStructure.getFunctor();

        try {
            if (this.actions.containsKey(actionID)) {
                ShanksAgentAction shanksAction = this.actions.get(actionID)
                        .newInstance();
                result = shanksAction.executeAction(this.getSimulation());
            } else {
                throw new UnknownShanksAgentActionException(actionID,
                        this.getID());
            }

        } catch (UnknownShanksAgentActionException e) {
            logger.severe("Action was not executed -> " + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.severe("InstantiationException" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.severe("IllegalAccessException" + e.getMessage());
            e.printStackTrace();
        }

        action.setResult(result);

    }

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

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#canSleep()
     */
    public boolean canSleep() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#isRunning()
     */
    public boolean isRunning() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#sleep()
     */
    public void sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
    }

    /*
     * (non-Javadoc)
     * 
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
