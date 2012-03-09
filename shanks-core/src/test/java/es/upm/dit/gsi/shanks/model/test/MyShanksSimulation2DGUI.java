package es.upm.dit.gsi.shanks.model.test;

import java.util.HashMap;

import javax.swing.JFrame;

import sim.display.Display2D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;

/**
 * @author a.carrera
 * 
 */
public class MyShanksSimulation2DGUI extends ShanksSimulation2DGUI {

     
    
    /**
     * @param sim
     */
    public MyShanksSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "MyShanksSimulation2DGUI! :)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#addDisplays(es.upm.dit.gsi
     * .shanks.model.scenario.portrayal.Scenario2DPortrayal)
     */
    public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
        Display2D failureDisplay = new Display2D(600, 100, this);
        try {
            this.addDisplay(
                    MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID,
                    failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#addCharts(es.upm.dit.gsi.
     * shanks.model.scenario.portrayal.Scenario2DPortrayal)
     */
    @Override
    public void addCharts(Scenario2DPortrayal scenarioPortrayal) {
        try {
            this.addTimeChart(MyShanksSimulation.RESOLVED_FAILURES_PER_AGENT_CHART_ID, "Time / Steps", "Resolved failures");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#locateFrames(es.upm.dit.gsi
     * .shanks.model.scenario.portrayal.Scenario2DPortrayal)
     */
    @Override
    public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
        HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
        JFrame failureFrame = frames.get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID);
        JFrame chartFrame = frames.get(MyShanksSimulation.RESOLVED_FAILURES_PER_AGENT_CHART_ID);
        
        mainFrame.setLocation(100, 100);
        failureFrame.setLocation(500, 0);
        chartFrame.setLocation(600, 200);

    }

}
