package es.upm.dit.gsi.shanks.model.test;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;

import sim.display3d.Display3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;

/**
 * @author a.carrera
 *
 */
public class MyShanksSimulation3DGUI extends ShanksSimulation3DGUI {

    /**
     * @param sim
     */
    public MyShanksSimulation3DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "MyShanksSimulation3DGUI! :)";
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#addDisplays(es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal)
     */
    public void addDisplays(Scenario3DPortrayal scenarioPortrayal) {
        
        Display3D failureDisplay = new Display3D(400, 400, this);
        failureDisplay.scale(0.005);
        failureDisplay.setBackdrop(Color.white);
        try {
            this.addDisplay(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID, failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.ShanksSimulation3DGUI#addCharts(es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal)
     */
    @Override
    public void addCharts(Scenario3DPortrayal scenarioPortrayal)
            throws DuplicatedChartIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException {
            this.addTimeChart(MyShanksSimulation.RESOLVED_FAILURES_PER_AGENT_CHART_ID, "Time / Steps", "Resolved failures");
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.ShanksSimulation3DGUI#locateFrames(es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal)
     */
    @Override
    public void locateFrames(Scenario3DPortrayal scenarioPortrayal) {
        HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
        JFrame failureFrame = frames.get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID);
        JFrame chartFrame = frames.get(MyShanksSimulation.RESOLVED_FAILURES_PER_AGENT_CHART_ID);
        
        mainFrame.setLocation(100, 100);
        failureFrame.setLocation(500, 0);
        chartFrame.setLocation(600, 200);
    }

}
