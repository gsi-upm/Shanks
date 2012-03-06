/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.perception;

import es.upm.dit.gsi.shanks.agent.capability.movement.Location;

/**
 * @author a.carrera
 *
 */
public interface PercipientShanksAgent {
    
    /**
     * @return return the perception range of the agent
     */
    public double getPerceptionRange();
    
    /**
     * @return the object Location of the agent
     */
    public Location getCurrentLocation();

}
