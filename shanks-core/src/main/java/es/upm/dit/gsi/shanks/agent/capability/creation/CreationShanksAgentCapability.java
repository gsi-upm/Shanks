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
package es.upm.dit.gsi.shanks.agent.capability.creation;

import sim.engine.Schedule;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * Class to provide the capability of create a new shanks agent on simulation
 * time, from any other shanks agent.
 * 
 * @author Alberto Mardomingo
 * 
 */
public class CreationShanksAgentCapability {

    /**
     * Adds an agent to the simulation
     * 
     * @param sim
     *            - The shanks simulation
     * @param Agent
     *            - The new agent
     * @throws DuplicatedAgentIDException
     */
    public static void addNewAgent(ShanksSimulation sim, ShanksAgent agent)
            throws ShanksException {
        sim.registerShanksAgent(agent);
        if (sim.schedule.getTime() < 0) {
            sim.schedule.scheduleRepeating(Schedule.EPOCH, 2, agent, 1);
        } else {
            sim.schedule.scheduleRepeating(sim.schedule.getTime(), 2, agent, 1);
        }
        sim.logger
                .info("Added a new agent to the simulation: " + agent.getID());
    }

    /**
     * "Removes" an agent with the given name from the simulation
     * 
     * Be careful: what this actually do is to stop the agent execution.
     * 
     * @param sim
     *            -The Shanks Simulation
     * @param agentID
     *            - The name of the agent to remove
     * @throws ShanksException
     *             An UnkownAgentException if the Agent ID is not found on the
     *             simulation.
     */
    public static void removeAgent(ShanksSimulation sim, String agentID)
            throws ShanksException {
        sim.logger.info("Stoppable not fount. Attempting direct stop...");
        sim.unregisterShanksAgent(agentID);
        sim.logger.info("Agent " + agentID + " stopped.");
    }
}
