/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.capability.perception;

import es.upm.dit.gsi.shanks.agent.capability.movement.Location;

/**
 * Interface that an agent have to implement to use
 * ShanksAgentPerceptionCapability
 * 
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
