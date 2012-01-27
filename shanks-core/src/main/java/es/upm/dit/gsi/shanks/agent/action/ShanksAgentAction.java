package es.upm.dit.gsi.shanks.agent.action;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public abstract class ShanksAgentAction {

    /**
     * @param simulation
     * @return true if the action was performed successfully, false if not 
     */
    abstract public boolean executeAction(ShanksSimulation simulation);
    
}
