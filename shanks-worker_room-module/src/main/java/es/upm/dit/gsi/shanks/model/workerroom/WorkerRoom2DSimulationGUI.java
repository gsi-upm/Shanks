package es.upm.dit.gsi.shanks.model.workerroom;

import java.util.HashMap;

import javax.swing.JFrame;

import sim.display.Display2D;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.workerroom.scenario.portrayal.WorkerRoomScenario2DPortrayal;

public class WorkerRoom2DSimulationGUI extends ShanksSimulation2DGUI{

	public WorkerRoom2DSimulationGUI(ShanksSimulation sim) {
		super(sim);
	}

	@Override
	public void addCharts(Scenario2DPortrayal scenarioPortrayal)
			throws DuplicatedChartIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		
	}
	
	public static String getName() {
        return "Worker Room Simulation";
    }

	public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
        Display2D failureDisplay = new Display2D(600, 100, this);
        try {
            this.addDisplay(
                    WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID,
                    failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
//        JFrame failureFrame = frames.get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID);
//        JFrame chartFrame = frames.get(FailuresChartPainter.RESOLVED_FAILURES_PER_AGENT_CHART_ID);
        
        mainFrame.setLocation(100, 100);
//        failureFrame.setLocation(500, 0);
//        chartFrame.setLocation(600, 200);

    }

}
