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
 * 
 */
package es.upm.dit.gsi.shanks.agent.test;

import java.util.logging.Logger;

import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.movement.MobileShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.ShanksAgentMovementCapability;
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.perception.ShanksAgentPerceptionCapability;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;

/**
 * @author a.carrera
 * 
 */
public class MySimpleShanksAgent extends SimpleShanksAgent implements
        MobileShanksAgent, PercipientShanksAgent {

    private Location currentLocation;
    private Location targetLocation;
    private Double speed;
    private boolean allowToMove;
    private double perceptionRange;

    private boolean hasBeenNearToSomething;

    /**
     * 
     */
    private static final long serialVersionUID = 263836274462865563L;

    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MySimpleShanksAgent(String id, double speed, double perceptionRange, Logger logger) {
        super(id, logger);
        this.currentLocation = new Location();
        this.targetLocation = new Location();
        this.speed = speed;
        this.perceptionRange = perceptionRange;
        this.hasBeenNearToSomething = false;
    }

    public boolean hasBeenNearToSomething() {
        return this.hasBeenNearToSomething;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#checkMail()
     */
    @Override
    public void checkMail() {
        this.getInbox();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.agent.SimpleShanksAgent#executeReasoningCycle(es
     * .upm.dit.gsi.shanks.ShanksSimulation)
     */
    @Override
    public void executeReasoningCycle(ShanksSimulation simulation) {
        this.allowToMove = simulation.random.nextBoolean();
        try {
            if (simulation.getScenarioPortrayal() instanceof Scenario2DPortrayal) {
                if (currentLocation.getLocation2D() == null) {
                    Location location = new Location(new Double2D(50.0, 50.0));
                    ShanksAgentMovementCapability.updateLocation(simulation,
                            this, location);
                }
            } else if (simulation.getScenarioPortrayal() instanceof Scenario3DPortrayal) {
                if (currentLocation.getLocation3D() == null) {
                    Location location = new Location(new Double3D(10.0, 10.0,
                            10.0));
                    ShanksAgentMovementCapability.updateLocation(simulation,
                            this, location);
                }
            }

            if (simulation.getScenarioPortrayal() != null) {
                // 1st step of the reasoning cycle -> It bites if it is near to
                // a
                // device

                Bag objects = ShanksAgentPerceptionCapability.getAllObjects(
                        simulation, this);
                objects = ShanksAgentPerceptionCapability.getPercepts(
                        simulation, this);
                for (Object o : objects) {
                    if (o instanceof Device) {
                        Location objectLocation = ShanksAgentPerceptionCapability
                                .getObjectLocation(simulation, this, o);
//                        double distance = ShanksAgentPerceptionCapability
//                                .getDistanceTo(simulation, this, o);
                        if (this.currentLocation.isNearTo(objectLocation, 10)) {
                            if (!hasBeenNearToSomething) {
                                this.hasBeenNearToSomething = true;
                            }
                        }
                    }
                }

                if (simulation.schedule.getSteps() % 1000 == 0) {
                    Location randomObjLocation = ShanksAgentPerceptionCapability
                            .getRandomObjectLocation(simulation, this);
                    this.setTargetLocation(randomObjLocation);
                }

                if (simulation.schedule.getSteps() % 300 == 0) {
                    Location randomObjLocation = ShanksAgentPerceptionCapability
                            .getRandomObjectLocationInPerceptionRange(
                                    simulation, this);
                    if (randomObjLocation != null
                            && this.currentLocation.isNearTo(randomObjLocation,
                                    5.0)) {
                        this.setTargetLocation(randomObjLocation);
                    }
                }

                if (this.allowToMove) {
                    ShanksAgentMovementCapability.goTo(simulation, this,
                            this.currentLocation, this.targetLocation,
                            this.speed);
                }

            }
        } catch (Exception e) {
            logger.severe("Exception: " + e.getMessage());
        }
    }

    @Override
    public double getPerceptionRange() {
        return this.perceptionRange;
    }

    @Override
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void stopMovement() {
        this.allowToMove = false;
    }

    @Override
    public void startMovement() {
        this.allowToMove = true;
    }

    @Override
    public boolean isAllowedToMove() {
        return this.allowToMove;
    }

    @Override
    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    @Override
    public Location getTargetLocation() {
        return this.targetLocation;
    }

    @Override
    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    @Override
    public void setTargetLocation(Location location) {
        this.targetLocation = location;
    }

}
