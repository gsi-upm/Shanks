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
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;

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

    
    public Logger log = Logger.getLogger(ShanksSimulation3DGUI.class.getName());
    
    public Display3D display;
    public JFrame frame;
//TOIMP hacer HashMap
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
    
    /* (non-Javadoc)
     * @see sim.display.GUIState#createController()
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
        this.getSimulation().getScenarioPortrayal().setupPortrayals();
        display.reset();
        display.setBackdrop(Color.white);
        display.repaint();

        ShanksSimulation sim = this.getSimulation();
        ScenarioPortrayal sp = sim.getScenarioPortrayal();
        HashMap<String, Portrayal> portrayals = sp.getPortrayals();
        Set<String> set = portrayals.keySet();
        for (String name : set) {
            display.attach((FieldPortrayal3D) this.getSimulation().getScenarioPortrayal().getPortrayals().get(name), name);
          //TOIMP hacer que se pueda elegir a que display se hacen attachs
        }
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#load(sim.engine.SimState)
     */
    public void load(ShanksSimulation state) {
        super.load(state);
        this.getSimulation().getScenarioPortrayal().setupPortrayals();
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#init(sim.display.Controller)
     */
    @Override
    public void init(Controller c) {
        super.init(c);
        ShanksSimulation sim = (ShanksSimulation) state;
        
        display = new Display3D(600, 600, this);
//        display.setClipping(false);
        
        ContinuousPortrayal3D devicesPortrayal = (ContinuousPortrayal3D) sim.getScenarioPortrayal().getPortrayals().get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        Continuous3D devicesGrid = (Continuous3D) devicesPortrayal.getField();
        display.scale(1.0 / devicesGrid.getHeight() * 1.05);

        frame = display.createFrame();
        frame.setTitle(getName());
        c.registerFrame(frame);
        frame.setVisible(true);
        display.getSelectionBehavior().setTolerance(10.0f);
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#quit()
     */
    @Override
    public void quit() {
        super.quit();

        if (frame != null) {
            frame.dispose();
            frame = null;
            display = null;
        }
    }

}
