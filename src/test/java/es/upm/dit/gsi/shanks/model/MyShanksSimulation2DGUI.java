package es.upm.dit.gsi.shanks.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;

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

}
