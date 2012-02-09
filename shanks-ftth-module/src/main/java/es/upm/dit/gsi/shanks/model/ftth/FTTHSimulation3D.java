package es.upm.dit.gsi.shanks.model.ftth;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;

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
	public void addDisplays(Scenario3DPortrayal arg0) {
		// TODO Auto-generated method stub
		
	}

}
