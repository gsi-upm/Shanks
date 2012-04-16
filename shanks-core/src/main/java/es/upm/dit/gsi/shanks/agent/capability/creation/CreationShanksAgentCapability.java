package es.upm.dit.gsi.shanks.agent.capability.creation;

import sim.engine.Schedule;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.exception.UnkownAgentException;

/**
 * Class to provide the capability of create a new shanks agent
 * on the go, from any other shanks agent.
 * 
 * Experimental. May not work.
 * 
 * @author Alberto Mardomingo
 *
 */
public class CreationShanksAgentCapability {
    
    /**
     * Adds an agent to the simulation
     * 
     * @param sim - The shanks simulation
     * @param Agent - The new agent
     * @throws DuplicatedAgentIDException 
     */
    public static void addNewAgent(ShanksSimulation sim, ShanksAgent agent) throws DuplicatedAgentIDException{
        sim.registerShanksAgent(agent);
        if (sim.schedule.getTime() < 0) {
            sim.schedule.scheduleRepeating(Schedule.EPOCH, 2, agent, 1);
        } else {
            sim.schedule.scheduleRepeating(sim.schedule.getTime(), 2, agent, 1);
        }
        sim.logger.info("Added a new agent to the simulation: " + agent.getID());
    }
    
    /**
     * "Removes" an agent with the given name from the simulation
     * 
     * Be careful: what this actually do is to stop the agent execution.
     * 
     * @param sim -The Shanks Simulation
     * @param agentID - The name of the agent to remove
     * @throws UnkownAgentException 
     */
    public static void removeAgent(ShanksSimulation sim, String agentID) throws UnkownAgentException {
        sim.logger.info("Stoppable not fount. Attempting direct stop...");
        sim.unregisterShanksAgent(agentID);        
        sim.logger.info("Agent " + agentID + " stopped.");// by " + remover.getID());
        
        //After implementing this, I realized that unregisterShanksAgent does the same thing.
        // I'm a genius.
        
//        try {
//            sim.getAgents().remove(sim.getAgent(agentID)); // Would this work?
//            if ( this.stoppables.containsKey(agentID)) {
//                this.stoppables.get(agentID).stop();
//                sim.logger.info("Agent " + agentID + " stopped.");// by " + remover.getID());
//            } else {
//                sim.logger.info("Stoppable not fount. Attempting direct stop...");
//                //  Attempts to directly stop it
//                //ShanksAgent agent = sim.getAgent(agentID);
//                Collection<ShanksAgent> agents = sim.getAgents();
//                Iterator<ShanksAgent> iter = agents.iterator();
//                while(iter.hasNext()){
//                    ShanksAgent agent = iter.next();
//                    if (agent.getID().equals(agentID)){
//                        sim.logger.fine("Agent " + agentID + " found");
//                        agent.stop();
//                    }
//                }
//                sim.logger.info("Agent " + agentID + " stopped.");// by " + remover.getID());
//            }
//        } catch (UnkownAgentException e) {
//            sim.logger.warning("Could not delete agent: " + agentID);
//            throw e;
//        }
    }
}
