package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;
import java.util.Map.Entry;

import sim.field.continuous.Continuous3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public abstract class ComplexScenario3DPortrayal extends Scenario3DPortrayal {

    public ComplexScenario3DPortrayal(Scenario scenario, long width,
            long height, long length) throws DuplicatedPortrayalIDException,
            ScenarioNotFoundException {
        super(scenario, width, height, length);
        this.placeScenarios();
    }

    /**
     * Place all scenarios of the ComplexScenario
     * 
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    abstract public void placeScenarios() throws DuplicatedPortrayalIDException,
            ScenarioNotFoundException;

    /**
     * Situate the scenario in the complex scenario main display
     * 
     * @param scenario
     * @param position
     * @param alpha
     * @param beta
     * @param gamma
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void situateScenario(Scenario scenario, Double3D position,
            Double2D alpha, Double2D beta, Double2D gamma)
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
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
            for (Entry<String, NetworkElement> entry : elements.entrySet()) {
                if (entry.getValue() instanceof Link) {
                    this.drawLink((Link) entry.getValue());
                }
            }
            if (scenario instanceof ComplexScenario) {
                this.drawScenarioLinksLinks((ComplexScenario) scenario);
            }
        } catch (DuplicatedPortrayalIDException e) {
            throw e;
        }
    }

    /**
     * @param complexScenario
     */
    private void drawScenarioLinksLinks(ComplexScenario complexScenario) {
        HashMap<String, NetworkElement> elements = complexScenario
                .getCurrentElements();
        for (Entry<String, NetworkElement> entry : elements.entrySet()) {
            if (entry.getValue() instanceof Link) {
                this.drawLink((Link) entry.getValue());
            }
        }
        for (Scenario scenario : complexScenario.getScenarios()) {
            if (scenario instanceof ComplexScenario) {
                this.drawScenarioLinksLinks((ComplexScenario) scenario);
            } else {
                elements = scenario.getCurrentElements();
                for (String eid : elements.keySet()) {
                    NetworkElement e = elements.get(eid);
                    if (e instanceof Link) {
                        this.drawLink((Link) e);
                    }

                }
            }
        }
    }

}
