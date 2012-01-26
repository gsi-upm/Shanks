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
import sim.display.Display2D;
import sim.display.GUIState;
import sim.portrayal.FieldPortrayal2D;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * ShanksSimulation2DGUI class
 * 
 * This abstract class is the graphic interface in 2D
 * 
 * @author a.carrera
 * @version 0.1
 * 
 */
public class ShanksSimulation2DGUI extends GUIState {

    public Logger logger = Logger.getLogger(ShanksSimulation2DGUI.class
            .getName());

    /**
     * @param sim
     */
    public ShanksSimulation2DGUI(ShanksSimulation sim) {
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

    /**
     * Creates and returns a controller ready for the user to manipulate. By
     * default this method creates a Console, sets it visible, and returns it.
     * You can override this to provide some other kind of controller.
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
            Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this
                    .getSimulation().getScenarioPortrayal();
            HashMap<String, Display2D> displays = scenarioPortrayal
                    .getDisplayList();
            for (String s : displays.keySet()) {
                Display2D display = displays.get(s);
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
                Display2D display = displays.get(displayID);
                HashMap<String, Portrayal> displayPortrayals = portrayals
                        .get(displayID);
                for (String portrayalID : displayPortrayals.keySet()) {
                    display.attach((FieldPortrayal2D) displayPortrayals
                            .get(portrayalID), portrayalID);
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
        Display2D mainDisplay = new Display2D(600, 600, this);
        mainDisplay.setBackdrop(Color.white);
        try {
            Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this
                    .getSimulation().getScenarioPortrayal();
            scenarioPortrayal.addDisplay(Scenario2DPortrayal.MAIN_DISPLAY_ID,
                    mainDisplay);

            HashMap<String, Display2D> displays = scenarioPortrayal
                    .getDisplayList();
            
            this.addDisplays(scenarioPortrayal);
            
            for (String displayID : displays.keySet()) {
                Display2D display = displays.get(displayID);
                display.setClipping(false);
                JFrame frame = display.createFrame();
                scenarioPortrayal.addFrame(displayID, frame);
                frame.setTitle(displayID);
                c.registerFrame(frame);
                frame.setVisible(true);
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
    public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
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

        Scenario2DPortrayal scenarioPortrayal;
        try {
            scenarioPortrayal = (Scenario2DPortrayal) this.getSimulation()
                    .getScenarioPortrayal();

            HashMap<String, Display2D> displays = scenarioPortrayal
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
        } catch (DuplicatedPortrayalIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

}
