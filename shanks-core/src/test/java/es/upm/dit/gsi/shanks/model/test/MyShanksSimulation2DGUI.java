package es.upm.dit.gsi.shanks.model.test;

import java.util.HashMap;

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
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#addDisplays(es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal)
     */
    public void addDisplays(Scenario2DPortrayal scenarioPortrayal) {
        HashMap<String, Display2D> displayList = scenarioPortrayal.getDisplayList();
        
        Display2D failureDisplay = new Display2D(600, 100, this);
        displayList.put(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID, failureDisplay);
    }

}
