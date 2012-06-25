/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import jason.asSemantics.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import sim.engine.SimState;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.UnkownAgentException;
import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * 
 * Agent without any rule, behaviour or code in the reasoning cycle
 * 
 * @author a.carrera
 * 
 */
public abstract class SimpleShanksAgent implements ShanksAgent {

    private Logger logger = Logger.getLogger(SimpleShanksAgent.class.getName());
    
    private ShanksSimulation simulation;
    private String id;
    private List<Message> inbox;

    public SimpleShanksAgent(String id) {
        this.id = id;
        this.inbox = new ArrayList<Message>();
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#putMessegaInInbox(jason.asSemantics.Message)
     */
    @Override
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
        this.checkMail();
        this.executeReasoningCycle((ShanksSimulation) simulation);
    }

    /**
     * The reasoning cycle of the agent. All behaviors of the agent are written in this method.
     */
    abstract public void executeReasoningCycle(ShanksSimulation simulation);
    
    /**
     * Return the inbox of the agent
     */
    public List<Message> getInbox() {
        return this.inbox;
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#sendMsg(jason.asSemantics.Message)
     */
    @Override
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
        } catch (ShanksException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The id of the agent
     */
    public String getID() {
        return this.id;
    }

    /* (non-Javadoc)
     * @see sim.engine.Stoppable#stop()
     */
    public void stop() {
    }
    
    private static final long serialVersionUID = -1895658991339856799L;
}
