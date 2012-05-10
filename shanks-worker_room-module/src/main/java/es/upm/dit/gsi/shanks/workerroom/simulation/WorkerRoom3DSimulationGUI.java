package es.upm.dit.gsi.shanks.workerroom.simulation;

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
import es.upm.dit.gsi.shanks.workerroom.model.scenario.portrayal.WorkerRoomScenario2DPortrayal;

public class WorkerRoom3DSimulationGUI extends ShanksSimulation3DGUI{

	public WorkerRoom3DSimulationGUI(ShanksSimulation sim) {
		super(sim);
	}
	
	public static String getName() {
        return "Worker Room 3D GUI";
    }

	@Override
	public void addCharts(Scenario3DPortrayal arg0)
			throws DuplicatedChartIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		Display3D failureDisplay = new Display3D(400, 400, this);
        failureDisplay.scale(0.005);
        failureDisplay.setBackdrop(Color.white);
        try {
            this.addDisplay(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID, failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void addDisplays(Scenario3DPortrayal scenarioPortrayal) {
		Display3D failureDisplay = new Display3D(400, 400, this);
        failureDisplay.scale(0.005);
        failureDisplay.setBackdrop(Color.white);
        try {
            this.addDisplay(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID, failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void locateFrames(Scenario3DPortrayal scenarioPortrayal) {
		 HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
	        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
	        JFrame failureFrame = frames.get(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID);
	        //JFrame chartFrame = frames.get(FailuresChartPainter.RESOLVED_FAILURES_PER_AGENT_CHART_ID);
	        
	        mainFrame.setLocation(100, 100);
	        failureFrame.setLocation(500, 0);
	       // chartFrame.setLocation(600, 200);
		
	}

	

}
