package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import sim.display.Display2D;
import sim.field.continuous.Continuous2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.util.Bag;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayID;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public abstract class ComplexScenario2DPortrayal extends Scenario2DPortrayal {

    private List<Scenario2DPortrayal> scenarioPortrayals;

    public ComplexScenario2DPortrayal(Scenario scenario, int width, int height,
            boolean showAllScenariosDisplays) throws DuplicatedPortrayalID {
        super(scenario, width, height);
        this.scenarioPortrayals = new ArrayList<Scenario2DPortrayal>();
        this.placeScenarios();
        if (showAllScenariosDisplays) {
            this.addScenariosDisplays();
        }
    }

    private void addScenariosDisplays() {
        for (Scenario2DPortrayal scenarioPortrayal : this.scenarioPortrayals) {
            HashMap<String, Display2D> displays = scenarioPortrayal
                    .getDisplayList();
            HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
            String scenarioID = scenarioPortrayal.getScenario().getID();
            for (String id : displays.keySet()) {
                Display2D display = displays.get(id);
                JFrame frame = frames.get(id);
                try {
                    this.addDisplay(id + "_" + scenarioID, display);
                } catch (DuplictaedDisplayID e) {
                    // TODO Impossible at this point, this was checked before
                    // when the scenario portrayal of each scenario is created
                }
                this.addFrame(id + "_" + scenarioID, frame);
            }
        }
    }

    /**
     * Place all scenarios of the ComplexScenario
     * @throws DuplicatedPortrayalID 
     */
    abstract public void placeScenarios() throws DuplicatedPortrayalID;

    /**
     * Situate the scenario in the complex scenario main display
     * 
     * @param scenario
     * @param position
     * @param alpha
     * @param beta
     * @param gamma
     * @throws DuplicatedPortrayalID
     */
    public void situateScenario(Scenario scenario, Double2D position,
            Double2D alpha, Double2D beta) throws DuplicatedPortrayalID {
        Scenario2DPortrayal portrayal;
        try {
            portrayal = (Scenario2DPortrayal) scenario
                    .createScenarioPortrayal();
            this.scenarioPortrayals.add(portrayal);
            ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) portrayal
                    .getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
                    .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
            Continuous2D devicesSpace = (Continuous2D) devicesPortrayal
                    .getField();
            Bag allElements = devicesSpace.getAllObjects();
            Object[] all = allElements.objs;
            for (int i = 0; i < all.length; i++) {
                Device device = (Device) all[i];
                if (device!=null) {
                    Double2D devicePosition = devicesSpace
                            .getObjectLocation(device);
                    Double2D rotated = ShanksMath.rotate(devicePosition, alpha, beta);
                    Double2D finalPosition = ShanksMath.add(rotated, position);
                    this.situateDevice(device, finalPosition.x, finalPosition.y);                    
                }
            }
            HashMap<String,NetworkElement> elements = scenario.getCurrentElements();
            for (String id : elements.keySet()) {
                NetworkElement element = elements.get(id);
                if (element instanceof Link) {
                    this.drawLink((Link)element);
                }
            }
        } catch (DuplicatedPortrayalID e) {
            throw e;
        }
    }

}
