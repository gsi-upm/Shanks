/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.movement;

/**
 * Interface that an agent have to implement to use ShanksAgentMovementCapability
 * 
 * @author a.carrera
 *
 */
public interface MobileShanksAgent {
    
    /**
     * Fix the speed of the agent
     * 
     * @param speed
     */
    public void setSpeed(Double speed);
    
    /**
     * @return the speed of the agent
     */
    public double getSpeed();
    
    /**
     * Stop the agent
     */
    public void stopMovement();
    
    /**
     * Allow the agent to move
     */
    public void startMovement();
    
    /**
     * @return true if the agent is allowed to move, false if not
     */
    public boolean isAllowedToMove();
    
    /**
     * @return the object Location of the agent
     */
    public Location getCurrentLocation();
    
    /**
     * @return the object Location that represent the target location of the agent
     */
    public Location getTargetLocation();

    /**
     * Set the current location of the agent
     */
    public void setCurrentLocation(Location location);
    
    /**
     * Set the target location of the agent
     */
    public void setTargetLocation(Location location);

}
