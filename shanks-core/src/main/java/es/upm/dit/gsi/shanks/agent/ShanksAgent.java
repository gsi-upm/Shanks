package es.upm.dit.gsi.shanks.agent;

import jason.JasonException;
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Agent;
import jason.asSemantics.Circumstance;
import jason.asSemantics.Message;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import jason.runtime.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
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

    public static final String MYSELF = "myself";

    private Logger logger = Logger.getLogger(ShanksAgent.class.getName());

    private ShanksSimulation simulation;
    private String id;
    private List<Message> inbox;
    private ShanksJasonAgent agent;
    private HashMap<String, Class<? extends ShanksAgentAction>> actions;

    private boolean reasoning;

    /**
     * Constructor of the agent
     * 
     * @param id
     * @throws DuplicatedActionIDException
     */
    public ShanksAgent(String id, String aslFilePath)
            throws DuplicatedActionIDException {
        this.id = id;
        this.reasoning = false;
        this.inbox = new ArrayList<Message>();
        this.actions = new HashMap<String, Class<? extends ShanksAgentAction>>();
        this.configActions();
        try {
            Circumstance cir;
            Settings settings;
            cir = new Circumstance();
            settings = new Settings();
            agent = new ShanksJasonAgent();
            new TransitionSystem(agent, cir, settings, this);
            agent.initAg();
            agent.addInitialBel(ASSyntax.createLiteral(ShanksAgent.MYSELF,
                    new Term[] { Literal.parseLiteral(this.getID()) }));
            agent.load(aslFilePath);
        } catch (JasonException e) {
            logger.severe("JasonException was thrown when agent " + id
                    + " was starting...");
            e.printStackTrace();
        }
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
        this.getTS().reasoningCycle();
        while (this.isRunning()) {
            this.getTS().reasoningCycle();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#reasoningCycleStarting()
     */
    public void reasoningCycleStarting() {
        this.reasoning = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#isRunning()
     */
    public boolean isRunning() {
        return this.reasoning;
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#stop()
     */
    public void stop() {
        super.stop();
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
        List<Literal> percepts = this.updateBeliefs(this.getSimulation());
        return percepts;
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
                result = shanksAction.executeAction(this.getSimulation(),
                        this.getID(), actionStructure.getTerms());
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
        this.getTS().getC().addFeedbackAction(action);
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#checkMail()
     */
    public void checkMail() {
        Queue<Message> mailBox = getTS().getC().getMailBox();
        for (Message m : this.inbox) {
            mailBox.offer(m);
            logger.fine("Received message -> ID: " + m.getMsgId() + " Sender: "
                    + m.getSender());
        }
        this.inbox.clear();
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
            logger.fine("Sent message from Agent: " + this.getID()
                    + "to Agent: " + message.getReceiver());
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
        return this.getTS().getC().getMailBox().isEmpty() && isRunning();
    }

    /*
     * (non-Javadoc)
     * 
     * @see jason.architecture.AgArch#sleep()
     */
    public void sleep() {
        this.reasoning = false;
    }
    
    /* (non-Javadoc)
     * @see jason.architecture.AgArch#wake()
     */
    public void wake() {
        this.reasoning = true;
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
    }

}
