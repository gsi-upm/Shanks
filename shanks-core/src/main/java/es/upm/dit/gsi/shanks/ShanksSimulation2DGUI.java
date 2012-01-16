package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Image;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;

/**
 * ModelGUI class
 * 
 * This class create the graphic interface
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

// The GUI of the model
public abstract class ShanksSimulation2DGUI extends GUIState {

    public Logger log = Logger.getLogger("ModelGUI");

    public Display2D display;
    public JFrame frame;
    private Scenario2DPortrayal scenarioPortrayal;

    /**
     * @param img
     * @return
     */
    public static Image loadImage(String img) {
        return new ImageIcon(ShanksSimulation.class.getResource(img)).getImage();
    }

    /**
     * @param sim
     * @param scenarioPortrayal
     */
    public ShanksSimulation2DGUI(ShanksSimulation sim, Scenario2DPortrayal scenarioPortrayal) {
        super(sim);
        this.scenarioPortrayal = scenarioPortrayal;
        this.createController();
    }

    /**
     * @return
     */
    public static String getName() {
        return "SHANKS Console Control - This is a default name";
    }

    /**
     * @return
     */
    public static Object getInfo() {
        return "<H2>Simulation for Heterogeneous and Autonomic Networks with MASON</H2>";
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
        setupPortrayals();
    }

    
    /**
     * 
     */
    public void setupPortrayals() {
        ShanksSimulation sim = this.getSimulation();
        agents.setField(ShanksSimulation.problems);
        links.setField(new SpatialNetwork2D(ShanksSimulation.problems, model.links));
        // links.setPortrayalForAll(new Links());
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
        display = new Display2D(600, 600, this, 1);
        display.setClipping(false);
        frame = display.createFrame();
        frame.setTitle("Agent Simulation");
        c.registerFrame(frame);
        frame.setVisible(true);
        display.attach(links, "Links");
        display.attach(agents, "Agens");

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
