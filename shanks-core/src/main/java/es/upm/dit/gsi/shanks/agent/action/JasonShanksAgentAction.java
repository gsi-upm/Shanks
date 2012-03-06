package es.upm.dit.gsi.shanks.agent.action;

import jason.asSyntax.Term;

import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;

/**
 * 
 * Class to represent the actions that are executed by JasonShanksAgents
 * 
 * @author a.carrera
 *
 */
public abstract class JasonShanksAgentAction {

    /**
     * @param simulation
     * @return true if the action was performed successfully, false if not 
     */
    abstract public boolean executeAction(ShanksSimulation simulation, ShanksAgent agent, List<Term> arguments);
    
}
