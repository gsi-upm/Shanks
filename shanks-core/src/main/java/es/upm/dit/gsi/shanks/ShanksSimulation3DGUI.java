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
import sim.util.media.chart.TimeSeriesChartGenerator;
import es.upm.dit.gsi.shanks.agent.exception.DuplictaedDisplayIDException;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
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
public abstract class ShanksSimulation3DGUI extends GUIState {

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
                    .getDisplays();
            for (Entry<String, Display3D> entry : displays.entrySet()) {
                entry.getValue().reset();
                entry.getValue().repaint();
            }

            ShanksSimulation sim = this.getSimulation();
            ScenarioPortrayal sp;
            sp = sim.getScenarioPortrayal();
            HashMap<String, HashMap<String, Portrayal>> portrayals = sp
                    .getPortrayals();
            for (Entry<String, HashMap<String, Portrayal>> displayEntry : portrayals
                    .entrySet()) {
                Display3D display = displays.get(displayEntry.getKey());
                HashMap<String, Portrayal> displayPortrayals = displayEntry
                        .getValue();
                for (Entry<String, Portrayal> portrayalEntry : displayPortrayals
                        .entrySet()) {
                    display.attach(
                            (FieldPortrayal3D) portrayalEntry.getValue(),
                            portrayalEntry.getKey());
                }
            }
        } catch (DuplicatedPortrayalIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ShanksException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see sim.display.GUIState#load(sim.engine.SimState)
     */
    public void load(ShanksSimulation state)
            throws ShanksException {
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
                    .getDisplays();
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
            this.addCharts(scenarioPortrayal);

            HashMap<String, TimeSeriesChartGenerator> timeCharts = scenarioPortrayal
                    .getTimeCharts();

            for (Entry<String, Display3D> displayEntry : displays.entrySet()) {
                JFrame frame = displayEntry.getValue().createFrame();
                scenarioPortrayal.addFrame(displayEntry.getKey(), frame);
                frame.setTitle(displayEntry.getKey());
                c.registerFrame(frame);
                frame.setVisible(true);
                displayEntry.getValue().getSelectionBehavior()
                        .setTolerance(10.0f);
            }

            for (Entry<String, TimeSeriesChartGenerator> chartEntry : timeCharts
                    .entrySet()) {
                JFrame frame = chartEntry.getValue().createFrame();
                scenarioPortrayal.addFrame(chartEntry.getKey(), frame);
                frame.setTitle(chartEntry.getKey());
                c.registerFrame(frame);
                frame.setVisible(true);
            }

            this.locateFrames(scenarioPortrayal);

            // } catch (DuplictaedDisplayIDException e) {
            // logger.severe(e.getMessage());
            // e.printStackTrace();
            // } catch (DuplicatedPortrayalIDException e) {
            // logger.severe(e.getMessage());
            // e.printStackTrace();
            // } catch (ScenarioNotFoundException e) {
            // logger.severe(e.getMessage());
            // e.printStackTrace();
            // } catch (DuplictedFrameIDException e) {
            // e.printStackTrace();
            // } catch (DuplicatedChartIDException e) {
            // e.printStackTrace();
        } catch (ShanksException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * To create extra displays
     * 
     * @param scenarioPortrayal
     */
    public abstract void addDisplays(Scenario3DPortrayal scenarioPortrayal);

    /**
     * To create charts
     * 
     * @param scenarioPortrayal
     */
    public abstract void addCharts(Scenario3DPortrayal scenarioPortrayal)
            throws ShanksException;

    /**
     * To move the frame for the simulation
     * 
     * @param scenarioPortrayal
     */
    public abstract void locateFrames(Scenario3DPortrayal scenarioPortrayal);

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
                    .getDisplays();
            HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
            for (Entry<String, JFrame> frameEntry : frames.entrySet()) {
                if (frameEntry.getValue() != null) {
                    frameEntry.getValue().dispose();
                }
            }
            frames.clear();
            displays.clear();
        } catch (DuplicatedPortrayalIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ShanksException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Add a display to the simulation
     * 
     * @param displayID
     * @param display
     * @throws DuplictaedDisplayIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void addDisplay(String displayID, Display3D display)
            throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        HashMap<String, Display3D> displays = scenarioPortrayal.getDisplays();
        if (!displays.containsKey(displayID)) {
            displays.put(displayID, display);
        } else {
            throw new DuplictaedDisplayIDException(displayID);
        }
    }

    /**
     * Remove a display from the simulation
     * 
     * @param displayID
     * @throws ScenarioNotFoundException
     * @throws DuplicatedPortrayalIDException
     */
    public void removeDisplay(String displayID) throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        HashMap<String, Display3D> displays = scenarioPortrayal.getDisplays();
        if (displays.containsKey(displayID)) {
            displays.remove(displayID);
        }
    }

    /**
     * Add a chart to the simulation
     * 
     * @param chartID
     * @param xAxisLabel
     * @param yAxisLabel
     * @throws DuplicatedChartIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void addTimeChart(String chartID, String xAxisLabel,
            String yAxisLabel) throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        scenarioPortrayal.addTimeChart(chartID, xAxisLabel, yAxisLabel);
    }

    /**
     * Remove a chart to the simulation
     * 
     * @param chartID
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void removeTimeChart(String chartID)
            throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        scenarioPortrayal.removeTimeChart(chartID);
    }

    /**
     * Add a Scatter Plot to the simulation
     * 
     * @param scatterID
     *            - The name of the plot
     * @param xAxisLabel
     *            - The name of the x axis
     * @param yAxisLabel
     *            - The name of the y axis
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     * @throws DuplicatedChartIDException
     */
    public void addScatterPlot(String scatterID, String xAxisLabel,
            String yAxisLabel) throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        scenarioPortrayal.addScatterPlot(scatterID, xAxisLabel, yAxisLabel);
    }

    /**
     * Remove a Scatter plot from the simulation
     * 
     * @param scatterID
     *            - The name of the Scatter Plot
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void removeScatterPlot(String scatterID)
            throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        scenarioPortrayal.removeScatterPlot(scatterID);
    }

    /**
     * Add a Histogram to the simulation
     * 
     * @param histogramID
     *            - The name of the Histogram
     * @param xAxisLabel
     *            - The label for the x axis
     * @param yAxisLabel
     *            - The label fot the y axis
     * @throws DuplicatedChartIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void addHistogram(String histogramID, String xAxisLabel,
            String yAxisLabel) throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        scenarioPortrayal.addHistogram(histogramID, xAxisLabel, yAxisLabel);
    }

    /**
     * Remove a Histogram from the simulation
     * 
     * @param histogramID
     *            - The name of the histogram
     * @throws DuplicatedChartIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void removeHistogram(String histogramID)
            throws ShanksException {
        Scenario3DPortrayal scenarioPortrayal = (Scenario3DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        scenarioPortrayal.removeHistogram(histogramID);
    }
}
