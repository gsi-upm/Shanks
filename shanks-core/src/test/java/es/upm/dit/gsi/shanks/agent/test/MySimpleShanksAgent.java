/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.test;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;

/**
 * @author a.carrera
 *
 */
public class MySimpleShanksAgent extends SimpleShanksAgent {

    /**
     * 
     */
    private static final long serialVersionUID = 263836274462865563L;

    public MySimpleShanksAgent(String id) {
        super(id);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#checkMail()
     */
    @Override
    public void checkMail() {
        this.getInbox();
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.agent.SimpleShanksAgent#executeReasoningCycle(es.upm.dit.gsi.shanks.ShanksSimulation)
     */
    @Override
    public void executeReasoningCycle(ShanksSimulation simulation) {
        long steps = simulation.getSchedule().getSteps();
        if (steps%500==0) {
            System.out.println("Executed simple shanks agent reasoning cycle in step " + steps + " by agent " + this.getID());   
        }
    }

}
