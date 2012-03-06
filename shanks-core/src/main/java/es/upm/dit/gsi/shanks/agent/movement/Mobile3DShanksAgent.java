/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.movement;

import sim.util.Double3D;

/**
 * @author a.carrera
 *
 */
public interface Mobile3DShanksAgent extends MobileShanksAgent {

    /**
     * Update the location of a shanks agent in agent belief and in simulation portrayal
     * 
     * @param location
     */
    public void updateLocation(Double3D location);
    
    /**
     * Fix a location goal to the agent in agent belief
     * 
     * @param location
     */
    public void goTo(Double3D location);

}
