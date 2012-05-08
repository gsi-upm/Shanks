package es.upm.dit.gsi.shanks.shanks_enterprise_module.model;

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
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.test.MyComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.test.steppable.FailuresChartPainter;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.EnterpriseScenario;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.portrayal.EnterpriseScenario2DPortrayal;

public class EnterpriseSimulation2DGUI extends ShanksSimulation2DGUI{

	public EnterpriseSimulation2DGUI(ShanksSimulation sim) {
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
                   EnterpriseScenario2DPortrayal.FAILURE_DISPLAY_ID,
                    failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void addCharts(Scenario2DPortrayal scenarioPortrayal)
			throws DuplicatedChartIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		
	}

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
        JFrame failureFrame = frames.get(EnterpriseScenario2DPortrayal.FAILURE_DISPLAY_ID);
        
        mainFrame.setLocation(100, 100);
        failureFrame.setLocation(500, 0);
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
		Properties configProperties = new Properties();
		configProperties.put(MyShanksSimulation.CONFIGURATION, "3");
		EnterpriseSimulation sim = new EnterpriseSimulation(
		        System.currentTimeMillis(), EnterpriseScenario.class,
		        "EnterpriseScenario", EnterpriseScenario.SUNNY,
		        scenarioProperties, configProperties);
		EnterpriseSimulation2DGUI gui = new EnterpriseSimulation2DGUI(sim);
		gui.start();
}

}
