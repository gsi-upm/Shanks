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
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayID;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

/**
 * ShanksSimulation2DGUI class
 * 
 * This abstract class is the graphic interface in 2D
 * 
 * @author a.carrera
 * @version 0.1
 * 
 */
public abstract class ShanksSimulation2DGUI extends GUIState {

    public Logger logger = Logger.getLogger(ShanksSimulation2DGUI.class.getName());

    private HashMap<String,Display2D> displayList;
    private HashMap<String,JFrame> frameList;
    
    public static final String MAIN_DISPLAY = "MainDisplay";

    /**
     * @param sim
     */
    public ShanksSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
        this.createController();
    }

    /**
     * @param img
     * @return
     */
    public static Image loadImage(String img) {
        return new ImageIcon(ShanksSimulation.class.getResource(img)).getImage();
    }

    /* (non-Javadoc)
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

    /**
     * @return
     */
    public HashMap<String, Display2D> getDisplayList() {
        return displayList;
    }

    /**
     * @param displayList
     */
    public void setDisplayList(HashMap<String, Display2D> displayList) {
        this.displayList = displayList;
    }
    
    /**
     * @param displayID
     * @param display
     * @throws DuplictaedDisplayID 
     */
    public void addDisplay(String displayID, Display2D display) throws DuplictaedDisplayID {
        if (this.displayList.containsKey(displayID)) {
            throw new DuplictaedDisplayID(displayID);
        }
        this.displayList.put(displayID, display);
    }
    
    /**
     * @param displayID
     */
    public void removeDisplay(String displayID) {
        this.displayList.remove(displayID);
    }

    /**
     * @return
     */
    public HashMap<String, JFrame> getFrameList() {
        return frameList;
    }

    /**
     * @param frameList
     */
    public void setFrameList(HashMap<String, JFrame> frameList) {
        this.frameList = frameList;
    }
    
    /**
     * @param frameID
     * @param frame
     */
    public void addFrame(String frameID, JFrame frame) {
        this.frameList.put(frameID, frame);
    }
    
    /**
     * @param frameID
     */
    public void removeFrame(String frameID) {
        this.frameList.remove(frameID);
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

    /* (non-Javadoc)
     * @see sim.display.GUIState#start()
     */
    @Override
    public void start() {
        super.start();
        try {
            this.getSimulation().getScenarioPortrayal().setupPortrayals();
        } catch (DuplicatedPortrayalID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        for (String s : displayList.keySet()) {
            Display2D display = displayList.get(s);
            display.reset();
            display.repaint();            
        }

        ShanksSimulation sim = this.getSimulation();
        ScenarioPortrayal sp;
        try {
            sp = sim.getScenarioPortrayal();
            HashMap<String,HashMap<String, Portrayal>> portrayals = sp.getPortrayals();
            Set<String> set = portrayals.keySet();
            for (String displayID : set) {
                Display2D display = this.displayList.get(displayID);
                HashMap<String,Portrayal> displayPortrayals = portrayals.get(displayID);
                for (String portrayalID : displayPortrayals.keySet()) {
                    display.attach((FieldPortrayal2D) displayPortrayals.get(portrayalID), portrayalID);   
                }
            }
        } catch (DuplicatedPortrayalID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }


    /* (non-Javadoc)
     * @see sim.display.GUIState#load(sim.engine.SimState)
     */
    public void load(ShanksSimulation state) throws DuplicatedPortrayalID
        {
        super.load(state);
        this.getSimulation().getScenarioPortrayal().setupPortrayals();
        }

    /* (non-Javadoc)
     * @see sim.display.GUIState#init(sim.display.Controller)
     */
    @Override
    public void init(Controller c) {
        super.init(c);
        Display2D mainDisplay = new Display2D(600, 600, this);
        mainDisplay.setBackdrop(Color.white);
        try {
            this.addDisplay(ShanksSimulation2DGUI.MAIN_DISPLAY, mainDisplay);
        } catch (DuplictaedDisplayID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        
        for (String displayID : this.displayList.keySet()) {
            Display2D display = this.displayList.get(displayID);
            display.setClipping(false);
            JFrame frame = display.createFrame();
            this.frameList.put(displayID, frame);
            frame.setTitle(displayID);
            c.registerFrame(frame);
            frame.setVisible(true);            
        }
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#quit()
     */
    @Override
    public void quit() {
        super.quit();

        for (String frameID : this.frameList.keySet()) {
            JFrame frame = this.frameList.get(frameID);
            if (frame != null) {
                frame.dispose();
                this.frameList.remove(frameID);
                this.displayList.remove(frameID);
            }
            
        }
    }

}
