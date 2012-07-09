package es.upm.dit.gsi.shanks.tutorial.simulation;

import java.util.HashMap;
import java.util.Properties;

import javax.swing.JFrame;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.tutorial.model.scenario.LANScenario;

/**
 * 
 * @author Daniel Lara
 *
 */

public class LANSimulation2DGUI extends ShanksSimulation2DGUI{

	public LANSimulation2DGUI(ShanksSimulation sim) {
		super(sim);
	}
	
	public static String getName() {
        return "TutorialSimulation2DGUI";
    }

	@Override
	public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
		
		
	}

	@Override
	public void addCharts(Scenario2DPortrayal scenarioPortrayal)
			throws ShanksException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
        mainFrame.setLocation(100, 100);
		
	}
	
	 public static void main(String[] args) throws ShanksException {

	        Properties scenarioProperties = new Properties();
//	        scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
	        Properties configProperties = new Properties();
	        configProperties.put(LANSimulation.CONFIGURATION, "3");
	        LANSimulation sim = new LANSimulation(
	                System.currentTimeMillis(), LANScenario.class,
	                "Tutorial Scenario", LANScenario.SUNNY,
	                scenarioProperties, configProperties);
	        LANSimulation2DGUI gui = new LANSimulation2DGUI(sim);
	        gui.start();
	    }

}
