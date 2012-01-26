package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map.Entry;
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
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

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
     * @return Image object
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
     * @return Simulation of the GUI
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
            for (Entry<String, Display3D> entry : displays.entrySet()) {
                entry.getValue().reset();
                entry.getValue().repaint();
            }

            ShanksSimulation sim = this.getSimulation();
            ScenarioPortrayal sp;
            sp = sim.getScenarioPortrayal();
            HashMap<String, HashMap<String, Portrayal>> portrayals = sp
                    .getPortrayals();
            for (Entry<String, HashMap<String, Portrayal>> displayEntry : portrayals.entrySet()) {
                Display3D display = displays.get(displayEntry.getKey());
                HashMap<String, Portrayal> displayPortrayals = displayEntry.getValue();
                for (Entry<String, Portrayal> portrayalEntry : displayPortrayals.entrySet()) {
                    display.attach((FieldPortrayal3D) portrayalEntry.getValue(), portrayalEntry.getKey());
                }
            }
        } catch (DuplicatedPortrayalIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#load(sim.engine.SimState)
     */
    public void load(ShanksSimulation state) throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
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
            
            this.addDisplays(scenarioPortrayal);
            
            for (Entry<String, Display3D> displayEntry : displays.entrySet()) {
                JFrame frame = displayEntry.getValue().createFrame();
                scenarioPortrayal.addFrame(displayEntry.getKey(), frame);
                frame.setTitle(displayEntry.getKey());
                c.registerFrame(frame);
                frame.setVisible(true);
                displayEntry.getValue().getSelectionBehavior().setTolerance(10.0f);
            }
        } catch (DuplictaedDisplayIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (DuplicatedPortrayalIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * To create extra displays
     * @param scenarioPortrayal
     */
    public void addDisplays(Scenario3DPortrayal scenarioPortrayal) {
        logger.info("No extra display to show");
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
            for (Entry<String, JFrame> frameEntry : frames.entrySet()) {
                if (frameEntry.getValue() != null) {
                    frameEntry.getValue().dispose();
                    frames.remove(frameEntry.getKey());
                    displays.remove(frameEntry.getKey());
                }

            }
        } catch (DuplicatedPortrayalIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

}
