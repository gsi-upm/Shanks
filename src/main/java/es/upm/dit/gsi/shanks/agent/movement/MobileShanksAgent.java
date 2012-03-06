/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.movement;

/**
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
     * Stop the agent
     */
    public void stopMovement();
    
    /**
     * Allow the agent to move
     */
    public void startMovement();
    
    /**
     * @return the object Location of the agent
     */
    public Location getLocation();

}
