/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.perception;

import sim.field.continuous.Continuous2D;
import sim.field.continuous.Continuous3D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.movement.Location;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 * 
 */
public class ShanksAgentPerceptionCapability {

    /**
     * @param simulation
     * @param agent
     * @return the objects that the agent percepts in its perception range
     */
    public static Bag getPercepts(ShanksSimulation simulation,
            PercipientShanksAgent agent) {
        Location agentLocation = agent.getCurrentLocation();
        if (agentLocation.is3DLocation()) {
            try {
                ContinuousPortrayal3D devicesPortrayal = (ContinuousPortrayal3D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous3D devicesField = (Continuous3D) devicesPortrayal
                        .getField();
                Bag objects = devicesField.getObjectsWithinDistance(
                        agentLocation.getLocation3D(),
                        agent.getPerceptionRange(), false);
                return objects;
            } catch (DuplicatedPortrayalIDException e) {
                e.printStackTrace();
            } catch (ScenarioNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (agentLocation.is2DLocation()) {
            try {
                ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous2D devicesField = (Continuous2D) devicesPortrayal
                        .getField();
                Bag objects = devicesField.getObjectsWithinDistance(
                        agentLocation.getLocation2D(),
                        agent.getPerceptionRange(), false);
                return objects;
            } catch (DuplicatedPortrayalIDException e) {
                e.printStackTrace();
            } catch (ScenarioNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param simulation
     * @param agent
     * @param o
     *            object to measure the distance between the agent and the
     *            object
     * @return the distance
     */
    public static double getDistanceTo(ShanksSimulation simulation,
            PercipientShanksAgent agent, Object o) {
        Location agentLocation = agent.getCurrentLocation();
        if (agentLocation.is3DLocation()) {
            try {
                ContinuousPortrayal3D devicesPortrayal = (ContinuousPortrayal3D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous3D devicesField = (Continuous3D) devicesPortrayal
                        .getField();
                Double3D objectLocation = devicesField
                        .getObjectLocationAsDouble3D(o);
                return agentLocation.getLocation3D().distance(objectLocation);
            } catch (DuplicatedPortrayalIDException e) {
                e.printStackTrace();
            } catch (ScenarioNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (agentLocation.is2DLocation()) {
            try {
                ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous2D devicesField = (Continuous2D) devicesPortrayal
                        .getField();
                Double2D objectLocation = devicesField.getObjectLocation(o);
                return agentLocation.getLocation2D().distance(objectLocation);
            } catch (DuplicatedPortrayalIDException e) {
                e.printStackTrace();
            } catch (ScenarioNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Double.MAX_VALUE;
    }

    /**
     * @param simulation
     * @param agent
     * @return A random object location. This location never will be the
     *         location of the agent.
     */
    public static Location getRandomObject(ShanksSimulation simulation,
            PercipientShanksAgent agent) {
        Location agentLocation = agent.getCurrentLocation();
        try {
            if (agentLocation.is3DLocation()) {
                ContinuousPortrayal3D devicesPortrayal = (ContinuousPortrayal3D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous3D devicesField = (Continuous3D) devicesPortrayal
                        .getField();
                Bag objects = devicesField.getAllObjects();
                Object o;
                Double3D newTarget = null;
                do {
                    int size = objects.size();
                    o = objects.get(simulation.random.nextInt(size));
                    newTarget = devicesField.getObjectLocation(o);
                } while (o.equals(agent));
                return new Location(newTarget);
            } else if (agentLocation.is2DLocation()) {
                ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous2D devicesField = (Continuous2D) devicesPortrayal
                        .getField();
                Bag objects = devicesField.getAllObjects();
                Object o;
                Double2D newTarget = null;
                do {
                    int size = objects.size();
                    o = objects.get(simulation.random.nextInt(size));
                    newTarget = devicesField.getObjectLocation(o);
                } while (o.equals(agent));
                return new Location(newTarget);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param object
     * @return the Location object that represent the position of the object
     */
    public static Location getObjectLocation(ShanksSimulation simulation,
            PercipientShanksAgent agent, Object object) {
        try {
            if (agent.getCurrentLocation().is3DLocation()) {
                ContinuousPortrayal3D devicesPortrayal;
                devicesPortrayal = (ContinuousPortrayal3D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous3D devicesField = (Continuous3D) devicesPortrayal
                        .getField();
                Double3D loc = devicesField.getObjectLocation(object);
                return new Location(loc);
            } else if (agent.getCurrentLocation().is2DLocation()) {
                ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) simulation
                        .getScenarioPortrayal().getPortrayals()
                        .get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
                        .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
                Continuous2D devicesField = (Continuous2D) devicesPortrayal
                        .getField();
                Double2D loc = devicesField.getObjectLocation(object);
                return new Location(loc);
            }
        } catch (DuplicatedPortrayalIDException e) {
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
