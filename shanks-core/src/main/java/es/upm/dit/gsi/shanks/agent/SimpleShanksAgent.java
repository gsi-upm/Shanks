/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
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
 * Agent without any rule, behavior or code in the reasoning cycle
 * 
 * @author a.carrera
 * 
 */
public abstract class SimpleShanksAgent implements ShanksAgent {

    private Logger logger;

    private ShanksSimulation simulation;
    private String id;
    private List<Message> inbox;

    public SimpleShanksAgent(String id, Logger logger) {
        this.id = id;
        this.logger = logger;
        this.inbox = new ArrayList<Message>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.agent.ShanksAgent#putMessegaInInbox(jason.asSemantics
     * .Message)
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
     * The reasoning cycle of the agent. All behaviors of the agent are written
     * in this method.
     */
    abstract public void executeReasoningCycle(ShanksSimulation simulation);

    /**
     * Return the inbox of the agent
     */
    public List<Message> getInbox() {
        return this.inbox;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.agent.ShanksAgent#sendMsg(jason.asSemantics.Message
     * )
     */
    @Override
    public void sendMsg(Message message) {
        try {
            message.setSender(this.getID());
            ShanksAgent receiver = this.simulation.getAgent(message
                    .getReceiver());
            receiver.putMessegaInInbox(message);
            logger.finer("Sent message from Agent: " + this.getID()
                    + " to Agent: " + message.getReceiver());
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

    /*
     * (non-Javadoc)
     * 
     * @see sim.engine.Stoppable#stop()
     */
    public void stop() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#getLogger()
     */
    public Logger getLogger() {
        return this.logger;
    }
    
    private static final long serialVersionUID = -1895658991339856799L;
}
