package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;

import sim.field.continuous.Continuous3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public abstract class ComplexScenario3DPortrayal extends Scenario3DPortrayal {

    public ComplexScenario3DPortrayal(Scenario scenario, long width,
            long height, long length)
            throws DuplicatedPortrayalID, ScenarioNotFoundException {
        super(scenario, width, height, length);
        this.placeScenarios();
    }

    /**
     * Place all scenarios of the ComplexScenario
     * 
     * @throws DuplicatedPortrayalID
     * @throws ScenarioNotFoundException 
     */
    abstract public void placeScenarios() throws DuplicatedPortrayalID, ScenarioNotFoundException;

    /**
     * Situate the scenario in the complex scenario main display
     * 
     * @param scenario
     * @param position
     * @param alpha
     * @param beta
     * @param gamma
     * @throws DuplicatedPortrayalID
     * @throws ScenarioNotFoundException 
     */
    public void situateScenario(Scenario scenario, Double3D position,
            Double2D alpha, Double2D beta, Double2D gamma)
            throws DuplicatedPortrayalID, ScenarioNotFoundException {
        Scenario3DPortrayal portrayal;
        try {
            portrayal = (Scenario3DPortrayal) scenario
                    .createScenarioPortrayal();
            ContinuousPortrayal3D devicesPortrayal = (ContinuousPortrayal3D) portrayal
                    .getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                    .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
            Continuous3D devicesSpace = (Continuous3D) devicesPortrayal
                    .getField();
            Bag allElements = devicesSpace.getAllObjects();
            Object[] all = allElements.objs;
            for (int i = 0; i < all.length; i++) {
                Device device = (Device) all[i];
                if (device != null) {
                    Double3D devicePosition = devicesSpace
                            .getObjectLocation(device);
                    Double3D rotated = ShanksMath.rotate(devicePosition, alpha,
                            beta, gamma);
                    Double3D finalPosition = ShanksMath.add(rotated, position);
                    this.situateDevice(device, finalPosition.x,
                            finalPosition.y, finalPosition.z);
                }
            }
            HashMap<String, NetworkElement> elements = scenario
                    .getCurrentElements();
            for (String id : elements.keySet()) {
                NetworkElement element = elements.get(id);
                if (element instanceof Link) {
                    this.drawLink((Link) element);
                }
            }
        } catch (DuplicatedPortrayalID e) {
            throw e;
        }
    }

}
