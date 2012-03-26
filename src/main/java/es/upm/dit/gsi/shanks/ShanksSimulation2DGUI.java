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
import sim.display.Display2D;
import sim.display.GUIState;
import sim.portrayal.FieldPortrayal2D;
import sim.portrayal.Portrayal;
import sim.util.media.chart.HistogramGenerator;
import sim.util.media.chart.ScatterPlotGenerator;
import sim.util.media.chart.TimeSeriesChartGenerator;
import es.upm.dit.gsi.shanks.exception.DuplictaedDisplayIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplictedFrameIDException;

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
                    .getDisplays();
            for (Entry<String, Display2D> entry : displays.entrySet()) {
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
                Display2D display = displays.get(displayEntry.getKey());
                HashMap<String, Portrayal> displayPortrayals = displayEntry
                        .getValue();
                for (Entry<String, Portrayal> portrayalEntry : displayPortrayals
                        .entrySet()) {
                    display.attach(
                            (FieldPortrayal2D) portrayalEntry.getValue(),
                            portrayalEntry.getKey());
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
    public void load(ShanksSimulation state)
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
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
                    .getDisplays();

            this.addDisplays(scenarioPortrayal);
            this.addCharts(scenarioPortrayal);

            HashMap<String, TimeSeriesChartGenerator> timeCharts = scenarioPortrayal
                    .getTimeCharts();

            for (Entry<String, Display2D> displayEntry : displays.entrySet()) {
                displayEntry.getValue().setClipping(false);
                JFrame frame = displayEntry.getValue().createFrame();
                scenarioPortrayal.addFrame(displayEntry.getKey(), frame);
                frame.setTitle(displayEntry.getKey());
                c.registerFrame(frame);
                frame.setVisible(true);
            }

            for (Entry<String, TimeSeriesChartGenerator> chartEntry : timeCharts.entrySet()) {
                JFrame frame = chartEntry.getValue().createFrame();
                scenarioPortrayal.addFrame(chartEntry.getKey(), frame);
                frame.setTitle(chartEntry.getKey());
                c.registerFrame(frame);
                frame.setVisible(true);
            }
            
            HashMap<String, ScatterPlotGenerator> scatterPlots = scenarioPortrayal
                    .getScatterPlots();
            
            for (Entry<String, ScatterPlotGenerator> scatterEntry : scatterPlots.entrySet()) {
                JFrame frame = scatterEntry.getValue().createFrame();
                scenarioPortrayal.addFrame(scatterEntry.getKey(), frame);
                frame.setTitle(scatterEntry.getKey());
                c.registerFrame(frame);
                frame.setVisible(true);
            }

            HashMap<String, HistogramGenerator> histograms = scenarioPortrayal
                    .getHistograms();
            
            for (Entry<String, HistogramGenerator> histogramrEntry : histograms.entrySet()) {
                JFrame frame = histogramrEntry.getValue().createFrame();
                scenarioPortrayal.addFrame(histogramrEntry.getKey(), frame);
                frame.setTitle(histogramrEntry.getKey());
                c.registerFrame(frame);
                frame.setVisible(true);
            }
            
            this.locateFrames(scenarioPortrayal);

        } catch (DuplictaedDisplayIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (DuplicatedPortrayalIDException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (DuplictedFrameIDException e) {
            e.printStackTrace();
        } catch (DuplicatedChartIDException e) {
            e.printStackTrace();
        }
    }

    /**
     * To create extra displays
     * 
     * @param scenarioPortrayal
     */
    public abstract void addDisplays(Scenario2DPortrayal scenarioPortrayal);

    /**
     * To create charts
     * 
     * @param scenarioPortrayal
     */
    public abstract void addCharts(Scenario2DPortrayal scenarioPortrayal) 
            throws DuplicatedChartIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException;
    
    
    /**
     * To move the frame for the simulation
     * 
     * @param scenarioPortrayal
     */
    public abstract void locateFrames(Scenario2DPortrayal scenarioPortrayal);

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
    public void addDisplay(String displayID, Display2D display)
            throws DuplictaedDisplayIDException,
            DuplicatedPortrayalIDException, ScenarioNotFoundException {
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        HashMap<String, Display2D> displays = scenarioPortrayal.getDisplays();
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
    public void removeDisplay(String displayID)
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this
                .getSimulation().getScenarioPortrayal();
        HashMap<String, Display2D> displays = scenarioPortrayal.getDisplays();
        if (displays.containsKey(displayID)) {
            displays.remove(displayID);
        }
    }
    
    /**
     * Add a time chart to the simulation
     * 
     * @param chartID
     * @param chart
     * @throws DuplicatedChartIDException 
     * @throws ScenarioNotFoundException 
     * @throws DuplicatedPortrayalIDException 
     */
    public void addTimeChart(String chartID, String xAxisLabel, String yAxisLabel) throws DuplicatedChartIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException {
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this.getSimulation().getScenarioPortrayal();
        scenarioPortrayal.addTimeChart(chartID, xAxisLabel, yAxisLabel);
    }
    
    /**
     * Remove a time chart to the simulation
     * 
     * @param chartID
     * @throws ScenarioNotFoundException 
     * @throws DuplicatedPortrayalIDException 
     */
    public void removeTimeChart(String chartID) throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this.getSimulation().getScenarioPortrayal();
        scenarioPortrayal.removeTimeChart(chartID);
    }
    
    /**
     * Add a Scatter Plot to the simulation
     * 
     * @param scatterID - The name of the plot
     * @param xAxisLabel - The name of the x axis
     * @param yAxisLabel - The name of the y axis
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     * @throws DuplicatedChartIDException
     */
    public void addScatterPlot(String scatterID, String xAxisLabel, String yAxisLabel) throws DuplicatedPortrayalIDException, ScenarioNotFoundException, DuplicatedChartIDException{
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this.getSimulation().getScenarioPortrayal();
        scenarioPortrayal.addScatterPlot(scatterID, xAxisLabel, yAxisLabel);
    }
    
    /**
     * Remove a Scatter plot from the simulation
     * 
     * @param scatterID - The name of the Scatter Plot
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void removeScatterPlot(String scatterID) throws DuplicatedPortrayalIDException, ScenarioNotFoundException{
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this.getSimulation().getScenarioPortrayal();
        scenarioPortrayal.removeScatterPlot(scatterID);
    }
    
    /**
     * Add a Histogram to the simulation
     * 
     * @param histogramID - The name of the Histogram
     * @param xAxisLabel - The label for the x axis
     * @param yAxisLabel - The label fot the y axis
     * @throws DuplicatedChartIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void addHistogram(String histogramID, String xAxisLabel, String yAxisLabel) throws DuplicatedChartIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException{
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this.getSimulation().getScenarioPortrayal();
        scenarioPortrayal.addHistogram(histogramID , xAxisLabel, yAxisLabel);
    }
    
    /**
     * Remove a Histogram from the simulation
     * 
     * @param histogramID - The name of the histogram
     * @throws DuplicatedChartIDException
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public void removeHistogram(String histogramID) throws DuplicatedChartIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException{
        Scenario2DPortrayal scenarioPortrayal = (Scenario2DPortrayal) this.getSimulation().getScenarioPortrayal();
        scenarioPortrayal.removeHistogram(histogramID);
    }
}
