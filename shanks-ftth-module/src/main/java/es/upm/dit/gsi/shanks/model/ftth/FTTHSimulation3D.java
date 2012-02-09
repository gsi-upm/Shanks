package es.upm.dit.gsi.shanks.model.ftth;

import java.awt.Color;
import java.util.HashMap;

import sim.display3d.Display3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.model.ftth.scenario.ScenarioDefinitions;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;

public class FTTHSimulation3D extends ShanksSimulation3DGUI{

	/**
     * @param sim
     */
	public FTTHSimulation3D(ShanksSimulation sim) {
		super(sim);
		
	}
	
	/**
     * @return
     */
    public static String getName() {
        return "FTTH 3D Simulation";
    }

	@Override
	public void addDisplays(Scenario3DPortrayal scenarioPortrayal) {
		HashMap<String, Display3D> displayList = scenarioPortrayal.getDisplayList();
        
        Display3D failureDisplay = new Display3D(400, 400, this);
        failureDisplay.scale(0.005);
        failureDisplay.setBackdrop(Color.white);
        displayList.put(ScenarioDefinitions.FAILURE_DISPLAY_ID, failureDisplay);
    
		
	}

}
