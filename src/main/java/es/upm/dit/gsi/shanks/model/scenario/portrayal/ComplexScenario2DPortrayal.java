package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;

import sim.field.continuous.Continuous2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.util.Bag;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public abstract class ComplexScenario2DPortrayal extends Scenario2DPortrayal {

    public ComplexScenario2DPortrayal(Scenario scenario, int width, int height)
            throws DuplicatedPortrayalID, ScenarioNotFoundException {
        super(scenario, width, height);
        this.placeScenarios();
    }

    /**
     * Place all scenarios of the ComplexScenario
     * 
     * @throws DuplicatedPortrayalID
     * @throws ScenarioNotFoundException
     */
    abstract public void placeScenarios() throws DuplicatedPortrayalID,
            ScenarioNotFoundException;

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
    public void situateScenario(Scenario scenario, Double2D position,
            Double2D alpha, Double2D beta) throws DuplicatedPortrayalID,
            ScenarioNotFoundException {
        Scenario2DPortrayal portrayal;
        try {
            portrayal = (Scenario2DPortrayal) scenario
                    .createScenarioPortrayal();
            ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) portrayal
                    .getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
                    .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
            Continuous2D devicesSpace = (Continuous2D) devicesPortrayal
                    .getField();
            Bag allElements = devicesSpace.getAllObjects();
            Object[] all = allElements.objs;
            for (int i = 0; i < all.length; i++) {
                Device device = (Device) all[i];
                if (device != null) {
                    Double2D devicePosition = devicesSpace
                            .getObjectLocation(device);
                    Double2D rotated = ShanksMath.rotate(devicePosition, alpha,
                            beta);
                    Double2D finalPosition = ShanksMath.add(rotated, position);
                    this.situateDevice(device, finalPosition.x, finalPosition.y);
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
            if (scenario instanceof ComplexScenario) {
                this.drawScenarioLinksLinks((ComplexScenario) scenario);
            }
        } catch (DuplicatedPortrayalID e) {
            throw e;
        }
    }

    /**
     * @param complexScenario
     */
    private void drawScenarioLinksLinks(ComplexScenario complexScenario) {
        HashMap<String, NetworkElement> elements = complexScenario
                .getCurrentElements();
        for (String id : elements.keySet()) {
            NetworkElement element = elements.get(id);
            if (element instanceof Link) {
                this.drawLink((Link) element);
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
}
