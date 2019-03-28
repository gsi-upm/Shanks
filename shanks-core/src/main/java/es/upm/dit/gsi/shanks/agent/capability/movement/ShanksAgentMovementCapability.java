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

import sim.field.continuous.Continuous2D;
import sim.field.continuous.Continuous3D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.util.Double2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;

/**
 * This class defines the behavior of an agent that inherits the Movement
 * Capability.
 * 
 * @author a.carrera
 * 
 */
public class ShanksAgentMovementCapability {

    /**
     * Update the location of the agent in the simulation
     * 
     * @param simulation
     * @param agent
     * @param location
     */
    public static void updateLocation(ShanksSimulation simulation,
            MobileShanksAgent agent, Location location) {
        if (location.is2DLocation()) {
            ShanksAgentMovementCapability.updateLocation(simulation, agent,
                    location.getLocation2D());
        } else if (location.is3DLocation()) {
            ShanksAgentMovementCapability.updateLocation(simulation, agent,
                    location.getLocation3D());
        }
    }

    /**
     * Update the location of the agent in the simulation
     * 
     * @param simulation
     * @param agent
     * @param location
     */
    private static void updateLocation(ShanksSimulation simulation,
            MobileShanksAgent agent, Double3D location) {
        agent.getCurrentLocation().setLocation3D(location);
        ContinuousPortrayal3D devicesPortrayal;
        try {
            devicesPortrayal = (ContinuousPortrayal3D) simulation
                    .getScenarioPortrayal().getPortrayals()
                    .get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                    .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
            Continuous3D devicesField = (Continuous3D) devicesPortrayal
                    .getField();
            devicesField.setObjectLocation(agent, location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the location of the agent in the simulation
     * 
     * @param simulation
     * @param agent
     * @param location
     */
    private static void updateLocation(ShanksSimulation simulation,
            MobileShanksAgent agent, Double2D location) {
        agent.getCurrentLocation().setLocation2D(location);
        ContinuousPortrayal2D devicesPortrayal;
        try {
            devicesPortrayal = (ContinuousPortrayal2D) simulation
                    .getScenarioPortrayal().getPortrayals()
                    .get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                    .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
            Continuous2D devicesField = (Continuous2D) devicesPortrayal
                    .getField();
            devicesField.setObjectLocation(agent, location);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Move the agent to the target location with the specific speed. Call this
     * method always you want to move. This method only moves the agent a
     * fragment equals to the velocity.
     * 
     * @param simulation
     * @param agent
     * @param currentLocation
     * @param targetLocation
     * @param speed
     */
    public static void goTo(ShanksSimulation simulation,
            MobileShanksAgent agent, Location currentLocation,
            Location targetLocation, double speed) {
        if (currentLocation.is2DLocation() && targetLocation.is2DLocation()) {
            ShanksAgentMovementCapability.goTo(simulation, agent,
                    currentLocation.getLocation2D(),
                    targetLocation.getLocation2D(), speed);
        } else if (currentLocation.is3DLocation()
                && targetLocation.is3DLocation()) {
            ShanksAgentMovementCapability.goTo(simulation, agent,
                    currentLocation.getLocation3D(),
                    targetLocation.getLocation3D(), speed);
        }
    }

    /**
     * Move the agent to the target location with the specific speed. Call this
     * method always you want to move. This method only moves the agent a
     * fragment equals to the velocity.
     * 
     * @param simulation
     * @param agent
     * @param currentLocation
     * @param targetLocation
     * @param speed
     */
    private static void goTo(ShanksSimulation simulation,
            MobileShanksAgent agent, Double2D currentLocation,
            Double2D targetLocation, double speed) {
        if (!targetLocation.equals(currentLocation) && agent.isAllowedToMove()) {
            Double2D direction = targetLocation.subtract(currentLocation);
            direction = direction.normalize();
            Double2D movement = direction.multiply(speed);
            ShanksAgentMovementCapability.updateLocation(simulation, agent,
                    currentLocation.add(movement));
        }
    }

    /**
     * Move the agent to the target location with the specific speed. Call this
     * method always you want to move. This method only moves the agent a
     * fragment equals to the velocity.
     * 
     * @param simulation
     * @param agent
     * @param currentLocation
     * @param targetLocation
     * @param speed
     */
    private static void goTo(ShanksSimulation simulation,
            MobileShanksAgent agent, Double3D currentLocation,
            Double3D targetLocation, double speed) {
        if (!targetLocation.equals(currentLocation) && agent.isAllowedToMove()) {
            Double3D direction = new Double3D(targetLocation.x
                    - currentLocation.x, targetLocation.y - currentLocation.y,
                    targetLocation.z - currentLocation.z);
            Double3D normalized = ShanksAgentMovementCapability
                    .normalizeDouble3D(direction);
            Double3D movement = new Double3D(normalized.x * speed, normalized.y
                    * speed, normalized.z * speed);
            ShanksAgentMovementCapability.updateLocation(simulation, agent,
                    new Double3D(currentLocation.x + movement.x,
                            currentLocation.y + movement.y, currentLocation.z
                                    + movement.z));
        }
    }

    /**
     * Normalize a Double3D object
     * 
     * @param vector
     * @return the normalized vector
     */
    private static Double3D normalizeDouble3D(Double3D vector) {
        double invertedlen = 1.0D / Math.sqrt(vector.x * vector.x + vector.y
                * vector.y + vector.z * vector.z);
        if ((invertedlen == (1.0D / 0.0D)) || (invertedlen == (-1.0D / 0.0D))
                || (invertedlen == 0.0D) || (invertedlen != invertedlen))
            throw new ArithmeticException("Vector length is "
                    + Math.sqrt(vector.x * vector.x + vector.y * vector.y
                            + vector.z * vector.z) + ", cannot normalize");
        return new Double3D(vector.x * invertedlen, vector.y * invertedlen,
                vector.z * invertedlen);
    }

}
