package es.upm.dit.gsi.shanks.shanks_isp_module.simulation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JFrame;

import sim.display.Display2D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario.ISPScenario;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario.portrayal.ISPScenario2DPortrayal;

public class ISPSimulation2DGUI extends ShanksSimulation2DGUI{

	public ISPSimulation2DGUI(ShanksSimulation sim) {
		super(sim);
	}

	public static String getName() {
        return "Enterprise Scenario";
    }
	
	@Override
	public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
		Display2D failureDisplay = new Display2D(600, 100, this);
        try {
            this.addDisplay(
                   ISPScenario2DPortrayal.FAILURE_DISPLAY_ID,
                    failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void addCharts(Scenario2DPortrayal scenarioPortrayal)
			throws DuplicatedChartIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
        this.addTimeChart(Values.DDOS_NUMBER, "Time / Steps", "How many attacks of each type has a Hacker made?");
		
		
	}

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
        JFrame failureFrame = frames.get(ISPScenario2DPortrayal.FAILURE_DISPLAY_ID);
        JFrame chartFrame = frames.get(Values.DDOS_NUMBER);
        mainFrame.setLocation(100, 100);
        failureFrame.setLocation(500, 0);
        chartFrame.setLocation(200, 200);
	}
	
	public static void main(String[] args) throws SecurityException,
			    IllegalArgumentException, NoSuchMethodException,
			    InstantiationException, IllegalAccessException,
			    InvocationTargetException,
			    UnsupportedNetworkElementStatusException,
			    TooManyConnectionException, UnsupportedScenarioStatusException,
			    DuplicatedIDException, DuplicatedPortrayalIDException,
			    ScenarioNotFoundException, DuplicatedAgentIDException, DuplicatedActionIDException {

		Properties scenarioProperties = new Properties();
		scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
		scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
		ISPSimulation sim = new ISPSimulation(
		        System.currentTimeMillis(), ISPScenario.class,
		        "ISPScenario", ISPScenario.STATUS_NORMAL,
		        scenarioProperties);
		ISPSimulation2DGUI gui = new ISPSimulation2DGUI(sim);
		gui.start();
	}

}
