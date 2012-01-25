package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.GUIState;
import sim.display3d.Display3D;
import sim.field.continuous.Continuous3D;
import sim.portrayal.Portrayal;
import sim.portrayal3d.FieldPortrayal3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayID;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

/**
 * ShanksSimulation3DGUI class
 * 
 * This abstract class is the graphic interface in 3D
 * 
 * @author a.carrera
 * @version 0.1
 * 
 */
public class ShanksSimulation3DGUI extends GUIState {

    private Logger logger = Logger.getLogger(ShanksSimulation3DGUI.class
            .getName());

    /**
     * @param sim
     */
    public ShanksSimulation3DGUI(ShanksSimulation sim) {
        super(sim);
        this.createController();
    }

    /**
     * @param img
     * @return
     */
    public static Image loadImage(String img) {
        return new ImageIcon(ShanksSimulation.class.getResource(img))
                .getImage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#getSimulationInspectedObject()
     */
    @Override
    public Object getSimulationInspectedObject() {
        return state;
    }

    /**
     * @return
     */
    public ShanksSimulation getSimulation() {
        return (ShanksSimulation) state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#createController()
     */
    public Controller createController() {
        Console console = new Console(this);
        console.setVisible(true);
        return console;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#start()
     */
    @Override
    public void start() {
        super.start();
        try {
            this.getSimulation().getScenarioPortrayal().setupPortrayals();
            Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                    .getSimulation().getScenarioPortrayal();
            HashMap<String, Display3D> displays = scenarioPortrayal
                    .getDisplayList();
            for (String s : displays.keySet()) {
                Display3D display = displays.get(s);
                display.reset();
                display.repaint();
            }

            ShanksSimulation sim = this.getSimulation();
            ScenarioPortrayal sp;
            sp = sim.getScenarioPortrayal();
            HashMap<String, HashMap<String, Portrayal>> portrayals = sp
                    .getPortrayals();
            Set<String> set = portrayals.keySet();
            for (String displayID : set) {
                Display3D display = displays.get(displayID);
                HashMap<String, Portrayal> displayPortrayals = portrayals
                        .get(displayID);
                for (String portrayalID : displayPortrayals.keySet()) {
                    display.attach((FieldPortrayal3D) displayPortrayals
                            .get(portrayalID), portrayalID);
                }
            }
        } catch (DuplicatedPortrayalID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#load(sim.engine.SimState)
     */
    public void load(ShanksSimulation state) throws DuplicatedPortrayalID {
        super.load(state);
        this.getSimulation().getScenarioPortrayal().setupPortrayals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#init(sim.display.Controller)
     */
    @Override
    public void init(Controller c) {
        super.init(c);
        ShanksSimulation sim = (ShanksSimulation) state;

        Display3D mainDisplay = new Display3D(800, 800, this);
        mainDisplay.setBackdrop(Color.white);
        try {
            Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                    .getSimulation().getScenarioPortrayal();

            HashMap<String, Display3D> displays = scenarioPortrayal
                    .getDisplayList();
            ContinuousPortrayal3D devicesPortrayal;
            devicesPortrayal = (ContinuousPortrayal3D) sim
                    .getScenarioPortrayal().getPortrayals()
                    .get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                    .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
            Continuous3D devicesGrid = (Continuous3D) devicesPortrayal
                    .getField();
            mainDisplay.scale(0.08 / devicesGrid.getHeight());
            mainDisplay.setShowsAxes(true);
            scenarioPortrayal.addDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID,
                    mainDisplay);

            for (String displayID : displays.keySet()) {
                Display3D display = displays.get(displayID);
                JFrame frame = display.createFrame();
                scenarioPortrayal.addFrame(displayID, frame);
                frame.setTitle(displayID);
                c.registerFrame(frame);
                frame.setVisible(true);
                display.getSelectionBehavior().setTolerance(10.0f);
            }
        } catch (DuplictaedDisplayID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (DuplicatedPortrayalID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#quit()
     */
    @Override
    public void quit() {
        super.quit();

        Scenario3DPortrayal scenarioPortrayal;
        try {
            scenarioPortrayal = (Scenario3DPortrayal) this.getSimulation()
                    .getScenarioPortrayal();

            HashMap<String, Display3D> displays = scenarioPortrayal
                    .getDisplayList();
            HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
            for (String frameID : frames.keySet()) {
                JFrame frame = frames.get(frameID);
                if (frame != null) {
                    frame.dispose();
                    frames.remove(frameID);
                    displays.remove(frameID);
                }

            }
        } catch (DuplicatedPortrayalID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

}
