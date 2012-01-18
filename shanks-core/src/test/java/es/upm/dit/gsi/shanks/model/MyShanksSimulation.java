package es.upm.dit.gsi.shanks.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

public class MyShanksSimulation extends ShanksSimulation {

    private List<Steppable> stepabbles;
    private int configuration;

    /**
     * @param seed
     */
    public MyShanksSimulation(long seed, String[] args) {
        super(seed);
        this.stepabbles = new ArrayList<Steppable>();
        this.config(new Integer(args[0]), args);
    }

    private void config(int configuration, String[] args) {
        this.configuration = configuration;
        switch (configuration) {
        case 0:
            break;
        case 1:
            this.config1(args);
            break;
        default:
            logger.info("No configuration for MyShanksSimulation. Configuration 0 loaded -> default");
        }
    }

    private void config1(String[] args) {
        Steppable resolver = new Steppable() {
            private static final long serialVersionUID = 3065478365749906260L;

            @Override
            public void step(SimState paramSimState) {
                ShanksSimulation sim = (ShanksSimulation) paramSimState;
                Set<Failure> failures = sim.getScenario().getCurrentFailures();
                for (Failure f : failures) {
                    HashMap<NetworkElement, String> elements = f.getAffectedElements();
                    for (NetworkElement element : elements.keySet()) {
                        Class<? extends NetworkElement> c = element.getClass();
                        if (c.equals(MyDevice.class)) {
                            try {
                                element.setCurrentStatus(MyDevice.OK);
                            } catch (UnsupportedNetworkElementStatusException e) {
                                e.printStackTrace();
                            }
                        } else if (c.equals(MyLink.class)) {
                            try {
                                element.setCurrentStatus(MyLink.OK);
                            } catch (UnsupportedNetworkElementStatusException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // Resolve only 1 failure
                    break;
                }
            }
        };
        this.stepabbles.add(resolver);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -3889593103393654950L;

    @Override
    public ScenarioManager createScenarioManager()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException {
        Scenario s = new MyScenario("MyScenario", MyScenario.CLOUDY, 50);
        logger.warning("Scenario created");
        ScenarioPortrayal sp = s.createScenarioPortrayal();
        if (sp==null) {
            logger.warning("ScenarioPortrayals is null");
        }
        ScenarioManager sm = new ScenarioManager(s, sp);
        return sm;
    }

    @Override
    public void addSteppables() {
        switch (this.configuration) {
        case 0:
            logger.fine("Nothing todo here... No more steppables");
            break;
        case 1:
            for (int i = 0; i<this.stepabbles.size(); i++) {
                Steppable steppable = this.stepabbles.get(i);
                schedule.scheduleRepeating(Schedule.EPOCH, i+1, steppable, 5);
            }
            break;
        default:
            logger.info("No configuration for MyShanksSimulation. Configuration 0 loaded -> default");
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyShanksSimulation state = new MyShanksSimulation(
                System.currentTimeMillis(), args);
        state.start();
        do
            if (!state.schedule.step(state))
                break;
        while (state.schedule.getSteps() < 2001);
        state.finish();
    }

}
