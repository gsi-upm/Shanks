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
public abstract class ShanksSimulation3DGUI extends GUIState {

    private Logger logger = Logger.getLogger(ShanksSimulation3DGUI.class.getName());

    public HashMap<String, Display3D> displayList;
    public HashMap<String, JFrame> frameList;
    
    public static final String MAIN_DISPLAY = "MainDisplay";
    
    /**
     * @param sim
     */
    public ShanksSimulation3DGUI(ShanksSimulation sim) {
        super(sim);
        this.displayList = new HashMap<String, Display3D>();
        this.frameList = new HashMap<String, JFrame>();
        this.createController();
    }

    /**
     * @return
     */
    public HashMap<String, Display3D> getDisplayList() {
        return displayList;
    }

    /**
     * @param displayList
     */
    public void setDisplayList(HashMap<String, Display3D> displayList) {
        this.displayList = displayList;
    }
    
    /**
     * @param displayID
     * @param display
     * @throws DuplictaedDisplayID 
     */
    public void addDisplay(String displayID, Display3D display) throws DuplictaedDisplayID {
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
        } catch (DuplicatedPortrayalID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        for (String s : displayList.keySet()) {
            Display3D display = displayList.get(s);
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
                Display3D display = this.displayList.get(displayID);
                HashMap<String,Portrayal> displayPortrayals = portrayals.get(displayID);
                for (String portrayalID : displayPortrayals.keySet()) {
                    display.attach((FieldPortrayal3D) displayPortrayals.get(portrayalID), portrayalID);   
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

        Display3D mainDisplay = new Display3D(600, 600, this);
        mainDisplay.setBackdrop(Color.white);
        try {
            this.addDisplay(ShanksSimulation3DGUI.MAIN_DISPLAY, mainDisplay);
        } catch (DuplictaedDisplayID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        // display.setClipping(false);

        ContinuousPortrayal3D devicesPortrayal;
        try {
            devicesPortrayal = (ContinuousPortrayal3D) sim
                    .getScenarioPortrayal().getPortrayals().get(ShanksSimulation3DGUI.MAIN_DISPLAY)
                    .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
            Continuous3D devicesGrid = (Continuous3D) devicesPortrayal.getField();
            mainDisplay.scale(1.0 / devicesGrid.getHeight() * 1.05);
        } catch (DuplicatedPortrayalID e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }


        
        for (String displayID : this.displayList.keySet()) {
            Display3D display = this.displayList.get(displayID);
            JFrame frame = display.createFrame();
            this.frameList.put(displayID, frame);
            frame.setTitle(displayID);
            c.registerFrame(frame);
            frame.setVisible(true);        
            display.getSelectionBehavior().setTolerance(10.0f);    
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
