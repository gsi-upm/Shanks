package es.upm.dit.gsi.shanks.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;

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

}
