package es.upm.dit.gsi.shanks.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;

public class MyShanksSimulation2DGUI extends ShanksSimulation2DGUI {

    public MyShanksSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
    }

    public static String getName() {
        return "MyShanksSimulation2DGUI! :)";
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), args);
        new MyShanksSimulation2DGUI(sim);
    }

}