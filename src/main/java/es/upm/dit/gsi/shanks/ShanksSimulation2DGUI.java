package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Image;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.portrayal.FieldPortrayal2D;

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

    public Display2D display;
    public JFrame frame;

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
        this.getSimulation().getScenarioPortrayal().setupPortrayals();
        display.reset();
        display.setBackdrop(Color.white);
        display.repaint();
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#init(sim.display.Controller)
     */
    @Override
    public void init(Controller c) {
        super.init(c);
        display = new Display2D(600, 600, this);
        display.setClipping(false);
        frame = display.createFrame();
        frame.setTitle(getName());
        c.registerFrame(frame);
        frame.setVisible(true);
        
        for (String name : this.getSimulation().getScenarioPortrayal().getPortrayals().keySet()) {
            display.attach((FieldPortrayal2D) this.getSimulation().getScenarioPortrayal().getPortrayals().get(name), name);
        }
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
