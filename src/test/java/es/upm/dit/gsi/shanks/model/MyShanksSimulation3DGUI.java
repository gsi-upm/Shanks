package es.upm.dit.gsi.shanks.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;

public class MyShanksSimulation3DGUI extends ShanksSimulation3DGUI {

    public MyShanksSimulation3DGUI(ShanksSimulation sim) {
        super(sim);
    }

    public static String getName() {
        return "MyShanksSimulation3DGUI! :)";
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), args);
        new MyShanksSimulation3DGUI(sim);
    }

}
