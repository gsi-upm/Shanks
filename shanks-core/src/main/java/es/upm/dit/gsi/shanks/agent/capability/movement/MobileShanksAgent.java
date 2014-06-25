/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
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
