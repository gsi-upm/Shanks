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

import java.util.logging.Logger;

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
    
    /**
     * @return Logger of the simulation
     */
    public Logger getLogger();
 
}
