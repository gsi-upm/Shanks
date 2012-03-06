/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.movement;

import sim.util.Double2D;

/**
 * @author a.carrera
 *
 */
public interface Mobile2DShanksAgent extends MobileShanksAgent {

    /**
     * Update the location of a shanks agent in agent belief and in simulation portrayal
     * 
     * @param location
     */
    public void updateLocation(Double2D location);
    
    /**
     * Fix a location goal to the agent in agent belief
     * 
     * @param location
     */
    public void goTo(Double2D location);
    
}
