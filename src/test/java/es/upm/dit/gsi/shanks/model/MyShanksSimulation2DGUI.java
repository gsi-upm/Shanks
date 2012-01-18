package es.upm.dit.gsi.shanks.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyScenario2DPortrayal;

public class MyShanksSimulation2DGUI extends ShanksSimulation2DGUI {

    public MyShanksSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
        sim.getScenarioManager().setPortrayal(new MyScenario2DPortrayal(sim.getScenario(), 100, 100));
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        MyShanksSimulation sim = new MyShanksSimulation(
                System.currentTimeMillis(), args);
        MyShanksSimulation2DGUI gui = new MyShanksSimulation2DGUI(sim);
      gui.start();
      do
          if (!gui.getSimulation().schedule.step(sim))
              break;
      while (gui.getSimulation().schedule.getSteps() < 2001);
      gui.finish();
    }

}
