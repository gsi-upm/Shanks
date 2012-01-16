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
import es.upm.dit.gsi.shanks.Simulation;

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

public class Simulation3DGUI extends GUIState {

    public Logger log = Logger.getLogger("ModelGUI");

    public Display3D display;
    public JFrame displayFrame;

    public Display3D legendDisplay;
    public JFrame legendFrame;

    public Display2D displayMessage;
    public JFrame frameMessage;

    public static SparseGridPortrayal2D messagePortrayal = new SparseGridPortrayal2D();
    public static ContinuousPortrayal3D elementsPortrayal = new ContinuousPortrayal3D();
    public static ContinuousPortrayal3D legendPortrayal = new ContinuousPortrayal3D();
    NetworkPortrayal3D edgePortrayal = new NetworkPortrayal3D();

    public static void main(String[] args) {
        new Simulation3DGUI().createController();
    }

    public Simulation3DGUI() {
        super(new Simulation(System.currentTimeMillis()));
    }

    public Simulation3DGUI(SimState state) {
        super(state);
    }

    public static String getName() {
        return "SHANKS";
    }

    public static Object getInfo() {
        return "<H2>SHANKS</H2> Simulation of Heterogeneal and Autonomous Networks";
    }

    @Override
    public Object getSimulationInspectedObject() {
        return state;
    }

    @Override
    public void start() {
        super.start();
        setupPortrayals();
    }

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
    public Controller createController() {
        Console console = new Console(this);
        console.setVisible(true);
        return console;
    }

    @Override
    public void quit() {
        super.quit();
        if (displayFrame != null && frameMessage != null && legendFrame != null) {
            displayFrame.dispose();
            frameMessage.dispose();
            legendFrame.dispose();
            frameMessage = null;
            displayFrame = null;
            legendFrame = null;
            display = null;
            displayMessage = null;
            legendDisplay = null;
        }
    }

    public static Image loadImage(String img) {
        return new ImageIcon(Simulation.class.getResource(img)).getImage();
    }

    public void setupPortrayals() {
        display.destroySceneGraph();
        legendDisplay.destroySceneGraph();
        Simulation mod = (Simulation) state;

        edgePortrayal
                .setField(new SpatialNetwork3D(mod.elements3d, mod.links1));
        SimpleEdgePortrayal3D linkportrayal = new CylinderEdgePortrayal3D(1);
        edgePortrayal.setPortrayalForAll(linkportrayal);
        elementsPortrayal.setField(mod.elements3d);
        legendPortrayal.setField(mod.legend);

        messagePortrayal.setField(Simulation.problems);
        DevicePortrayal dport = new DevicePortrayal();

        elementsPortrayal.setPortrayalForAll(dport);
        legendPortrayal.setPortrayalForAll(dport);
        displayMessage.reset();
        display.reset();
        legendDisplay.reset();
        displayMessage.setBackdrop(Color.white);
        display.createSceneGraph();
        legendDisplay.createSceneGraph();
        displayMessage.repaint();
    }

    @Override
    public void init(Controller c) {
        super.init(c);
        Simulation mod = (Simulation) state;

        display = new Display3D(800, 800, this, 1);
        legendDisplay = new Display3D(450, 450, this, 1);
        displayMessage = new Display2D(365, 50, this, 1);

        legendDisplay.attach(legendPortrayal, "Legend");
        displayMessage.attach(messagePortrayal, "Problems");
        display.attach(edgePortrayal, "EDGES");
        display.attach(elementsPortrayal, "SHANKS");
        display.scale(1.0 / mod.gridHeight * 1.05);
        legendDisplay.scale(1.0 / mod.gridHeight * 1.05);

        displayFrame = display.createFrame();
        legendFrame = legendDisplay.createFrame();
        frameMessage = displayMessage.createFrame();

        frameMessage.setLocation(0, 855);
        frameMessage.setTitle("Problems Display");
        legendFrame.setLocation(800, 400);
        legendFrame.setTitle("Scenario Legend");

        c.registerFrame(frameMessage);
        c.registerFrame(legendFrame);
        c.registerFrame(displayFrame);

        frameMessage.setVisible(true);
        legendFrame.setVisible(true);
        displayFrame.setVisible(true);

        display.mSelectBehavior.setTolerance(10.0f);
        legendDisplay.mSelectBehavior.setTolerance(10.0f);
    }

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

        public void setErrorGenerationChoice(int val) {
            if (val == 0) {
                error = val;
                Simulation.SELECT_ERROR_GENERATION = "ErrorList";
            } else if (val == 1) {
                error = val;
                Simulation.SELECT_ERROR_GENERATION = "ProbBroken";
            }
        }

        public void setScenarioChoice(int val) {
            if (val == 0) {
                cells = val;
                Simulation.SELECT_SCENARIO = "REAL FTTH";
            } else if (val == 1) {
                cells = val;
                Simulation.SELECT_SCENARIO = "SIMPLE FTTH";
            }

            // // reattach the portrayals
            // display.detatchAll();
            // display.attach(elementsPortrayal, "Devices");
            // display.attach(edgePortrayal, "Edges");
            //
            // // redisplay
            // if (display != null)
            // display.repaint();
        }
    }

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
