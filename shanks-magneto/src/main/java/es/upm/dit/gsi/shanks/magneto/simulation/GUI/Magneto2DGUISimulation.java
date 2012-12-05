package es.upm.dit.gsi.shanks.magneto.simulation.GUI;

import java.util.HashMap;
import java.util.Properties;

import javax.swing.JFrame;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.model.scenario.MagnetoScenario;
import es.upm.dit.gsi.shanks.magneto.simulation.MagnetoSimulation;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;

public class Magneto2DGUISimulation extends ShanksSimulation2DGUI{

	public Magneto2DGUISimulation(ShanksSimulation sim) {
		super(sim);
	}
	
	public static String getName(){
		return "Magneto Simulation";
	}

	@Override
	public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
		
	}

	@Override
	public void addCharts(Scenario2DPortrayal scenarioPortrayal)
			throws ShanksException {
		
	}

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
        mainFrame.setLocation(100, 100);

	}
	
	public static void main (String[] args) throws ShanksException {

        Properties scenarioProperties = new Properties();
//      scenarioProperties.put(MyScenario.CLOUDY_PROB, "5");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
        Properties configProperties = new Properties();
        configProperties.put(MagnetoSimulation.CONFIGURATION, "3");
        MagnetoSimulation sim = new MagnetoSimulation(
                System.currentTimeMillis(), MagnetoScenario.class,
                "Tutorial Scenario", MagnetoScenario.SUNNY,
                scenarioProperties, configProperties);
        Magneto2DGUISimulation gui = new Magneto2DGUISimulation(sim);
        gui.start();
	}


}
