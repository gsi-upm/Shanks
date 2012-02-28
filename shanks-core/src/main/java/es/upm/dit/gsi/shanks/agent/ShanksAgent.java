package es.upm.dit.gsi.shanks.agent;

import jason.asSemantics.Message;
import sim.engine.Steppable;
import sim.engine.Stoppable;

/**
 * Interface for all agent that executes in a ShanksSimulation
 * 
 * @author a.carrera
 *
 */
public interface ShanksAgent extends Steppable, Stoppable {

    /**
     * Put the message in the inbox of the message
     * 
     * @param message
     */
    public void putMessegaInInbox(Message message);
    
    /**
     * Check the inbox to process the incoming messages
     */
    public void checkMail();
    
    /**
     * Send a message.
     * 
     * The JASON message model is used to achieve connectivity between all agents independently of the Shanks agent architecture.
     * 
     * @param message
     */
    public void sendMsg(Message message);

    /**
     * @return The id of the agent
     */
    public String getID();
 
}
