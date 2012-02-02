package es.upm.dit.gsi.shanks.model.test;

import java.awt.Color;
import java.util.HashMap;

import sim.display3d.Display3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
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
        HashMap<String, Display3D> displayList = scenarioPortrayal.getDisplayList();
        
        Display3D failureDisplay = new Display3D(400, 400, this);
        failureDisplay.scale(0.005);
        failureDisplay.setBackdrop(Color.white);
        displayList.put(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID, failureDisplay);
    }

}
