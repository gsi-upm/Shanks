package es.upm.dit.gsi.shanks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal;
import es.upm.dit.gsi.shanks.ShanksSimulation;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.display3d.Display3D;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.SimpleInspector;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.ImagePortrayal2D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.CylinderEdgePortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import sim.portrayal3d.network.SimpleEdgePortrayal3D;
import sim.portrayal3d.network.SpatialNetwork3D;
import sim.portrayal3d.simple.ImagePortrayal3D;

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
    public JFrame displayFrame;

    /**
     * @param sim
     */
    public ShanksSimulation3DGUI(ShanksSimulation sim) {
        super(sim);
        this.createController();
    }

    /**
     * @param state
     */
    public ShanksSimulation3DGUI(SimState state) {
        super(state);
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
     * @see sim.display.GUIState#start()
     */
    @Override
    public void start() {
        super.start();
        setupPortrayals();
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#load(sim.engine.SimState)
     */
    @Override
    public void load(SimState state) {
        super.load(state);
        setupPortrayals();
    }

    /**
     * Creates and returns a controller ready for the user to manipulate. By
     * default this method creates a Console, sets it visible, and returns it.
     * You can override this to provide some other kind of controller.
     */
    /* (non-Javadoc)
     * @see sim.display.GUIState#createController()
     */
    public Controller createController() {
        Console console = new Console(this);
        console.setVisible(true);
        return console;
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#quit()
     */
    @Override
    public void quit() {
        super.quit();
//        if (displayFrame != null && frameMessage != null && legendFrame != null) {
//            displayFrame.dispose();
//            frameMessage.dispose();
//            legendFrame.dispose();
//            frameMessage = null;
//            displayFrame = null;
//            legendFrame = null;
//            display = null;
//            displayMessage = null;
//            legendDisplay = null;
//        }
    }

    /**
     * @param img
     * @return
     */
    public static Image loadImage(String img) {
        return new ImageIcon(ShanksSimulation.class.getResource(img)).getImage();
    }

    /**
     * 
     */
    public void setupPortrayals() {
        display.destroySceneGraph();
        ShanksSimulation mod = (ShanksSimulation) state;

//        edgePortrayal
//                .setField(new SpatialNetwork3D(mod.elements3d, mod.links1));
//        SimpleEdgePortrayal3D linkportrayal = new CylinderEdgePortrayal3D(1);
//        edgePortrayal.setPortrayalForAll(linkportrayal);
//        elementsPortrayal.setField(mod.elements3d);
//        legendPortrayal.setField(mod.legend);
//
//        messagePortrayal.setField(ShanksSimulation.problems);
//        DevicePortrayal dport = new DevicePortrayal();
//
//        elementsPortrayal.setPortrayalForAll(dport);
//        legendPortrayal.setPortrayalForAll(dport);
//        displayMessage.reset();
//        display.reset();
//        legendDisplay.reset();
//        displayMessage.setBackdrop(Color.white);
//        display.createSceneGraph();
//        legendDisplay.createSceneGraph();
//        displayMessage.repaint();
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#init(sim.display.Controller)
     */
    @Override
    public void init(Controller c) {
        super.init(c);
        ShanksSimulation mod = (ShanksSimulation) state;

        display = new Display3D(800, 800, this, 1);
//        legendDisplay = new Display3D(450, 450, this, 1);
//        displayMessage = new Display2D(365, 50, this, 1);
//
//        legendDisplay.attach(legendPortrayal, "Legend");
//        displayMessage.attach(messagePortrayal, "Problems");
//        display.attach(edgePortrayal, "EDGES");
//        display.attach(elementsPortrayal, "SHANKS");
//        display.scale(1.0 / mod.gridHeight * 1.05);
//        legendDisplay.scale(1.0 / mod.gridHeight * 1.05);
//
//        displayFrame = display.createFrame();
//        legendFrame = legendDisplay.createFrame();
//        frameMessage = displayMessage.createFrame();
//
//        frameMessage.setLocation(0, 855);
//        frameMessage.setTitle("Problems Display");
//        legendFrame.setLocation(800, 400);
//        legendFrame.setTitle("Scenario Legend");
//
//        c.registerFrame(frameMessage);
//        c.registerFrame(legendFrame);
//        c.registerFrame(displayFrame);
//
//        frameMessage.setVisible(true);
//        legendFrame.setVisible(true);
//        displayFrame.setVisible(true);
//
//        display.mSelectBehavior.setTolerance(10.0f);
//        legendDisplay.mSelectBehavior.setTolerance(10.0f);
    }

    /**
     * @author a.carrera
     *
     */
    public class ScenarioChoice {
        int cells = 0;
        int error = 0;

        public Object domScenarioChoice() {
            return new Object[] { "REAL FTTH", "SIMPLE FTTH" };
        }

        public Object domErrorGenerationChoice() {
            return new Object[] { "ErrorList", "ProbBroken" };
        }

        public int getScenarioChoice() {
            return cells;
        }

        public int getErrorGenerationChoice() {
            return error;
        }

//        public void setErrorGenerationChoice(int val) {
//            if (val == 0) {
//                error = val;
//                ShanksSimulation.SELECT_ERROR_GENERATION = "ErrorList";
//            } else if (val == 1) {
//                error = val;
//                ShanksSimulation.SELECT_ERROR_GENERATION = "ProbBroken";
//            }
//        }
//
//        public void setScenarioChoice(int val) {
//            if (val == 0) {
//                cells = val;
//                ShanksSimulation.SELECT_SCENARIO = "REAL FTTH";
//            } else if (val == 1) {
//                cells = val;
//                ShanksSimulation.SELECT_SCENARIO = "SIMPLE FTTH";
//            }
//
//            // // reattach the portrayals
//            // display.detatchAll();
//            // display.attach(elementsPortrayal, "Devices");
//            // display.attach(edgePortrayal, "Edges");
//            //
//            // // redisplay
//            // if (display != null)
//            // display.repaint();
//        }
    }

    /* (non-Javadoc)
     * @see sim.display.GUIState#getInspector()
     */
    @Override
    public Inspector getInspector() {

        log.fine("-> Inspector");

        final Inspector originalInspector = super.getInspector();
        final SimpleInspector scenarioInspector = new SimpleInspector(
                new ScenarioChoice(), this);

        originalInspector.setVolatile(true);

        Inspector newInspector = new Inspector() {
            private static final long serialVersionUID = -8213271730234903099L;

            @Override
            public void updateInspector() {
                originalInspector.updateInspector();
            }
        };

        newInspector.setVolatile(false);

        Box b = new Box(BoxLayout.X_AXIS) {

            private static final long serialVersionUID = 7429894079728338277L;

            @Override
            public Insets getInsets() {
                return new Insets(2, 2, 2, 2);
            }
        };

        b.add(newInspector.makeUpdateButton());
        b.add(Box.createGlue());

        log.info("Before Box b2");

        Box b2 = new Box(BoxLayout.Y_AXIS);
        b2.add(b);
        b2.add(scenarioInspector);
        b2.add(Box.createGlue());

        newInspector.setLayout(new BorderLayout());
        newInspector.add(b2, BorderLayout.NORTH);
        newInspector.add(originalInspector, BorderLayout.CENTER);

        log.fine("Inspector ->");

        return newInspector;
    }

}
