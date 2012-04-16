package es.upm.dit.gsi.shanks.agent.test;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;

/**
 * 
 * Simple agent, simply counts steps in the simulation.
 * 
 * @author Alberto Mardomingo
 */
public class CounterShanksAgent extends SimpleShanksAgent{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7824028426007073312L;

    public CounterShanksAgent(String id) {
        super(id);
    }

    @Override
    public void checkMail() {
        // Do nothing
    }

    @Override
    public void executeReasoningCycle(ShanksSimulation simulation) {
        if( simulation instanceof MyShanksSimulation) ;
            ((MyShanksSimulation)simulation).increaseCounter();
    }

}
