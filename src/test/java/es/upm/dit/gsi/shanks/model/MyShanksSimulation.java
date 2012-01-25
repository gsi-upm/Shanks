package es.upm.dit.gsi.shanks.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
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
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MyShanksSimulation extends ShanksSimulation {

    /**
     * 
     */
    private static final long serialVersionUID = 1778288778609950190L;
    private List<Steppable> stepabbles;
    private Properties configuration;
    
    public static final String CONFIGURATION = "Configuration";

    /**
     * @param seed
     * @throws DuplicatedIDException 
     * @throws UnsupportedScenarioStatusException 
     * @throws TooManyConnectionException 
     * @throws UnsupportedNetworkElementStatusException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws NoSuchMethodException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws DuplicatedPortrayalID 
     * @throws ScenarioNotFoundException 
     */
    public MyShanksSimulation(long seed, Class<? extends Scenario> scenarioClass, String scenarioID, String initialState, Properties properties, Properties configPropertiesMyShanksSimulation) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException, DuplicatedPortrayalID, ScenarioNotFoundException {
        super(seed, scenarioClass, scenarioID, initialState, properties);
        this.stepabbles = new ArrayList<Steppable>();
        this.config(configPropertiesMyShanksSimulation);
    }

    private void config(Properties configProp) {
        this.configuration = configProp;
        int conf = new Integer(this.configuration.getProperty(MyShanksSimulation.CONFIGURATION));
        switch (conf) {
        case 0:
            break;
        case 1:
            this.config1(configProp);
            break;
        default:
            logger.info("No configuration for MyShanksSimulation. Configuration 0 loaded -> default");
        }
    }

    private void config1(Properties props) {
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
                                element.setCurrentStatus(MyDevice.OK_STATUS);
                            } catch (UnsupportedNetworkElementStatusException e) {
                                e.printStackTrace();
                            }
                        } else if (c.equals(MyLink.class)) {
                            try {
                                element.setCurrentStatus(MyLink.OK_STATUS);
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
//
//    /**
//     * 
//     */
//    private static final long serialVersionUID = -3889593103393654950L;
//
//    @Override
//    public ScenarioManager createScenarioManager(String dimensions)
//            throws UnsupportedNetworkElementStatusException,
//            TooManyConnectionException, UnsupportedScenarioStatusException,
//            DuplicatedIDException {
//        Scenario s = new MyScenario("MyScenario", MyScenario.CLOUDY, 50);
//        logger.warning("Scenario created");
//        ScenarioPortrayal sp = s.createScenarioPortrayal(dimensions);
//        if (sp==null) {
//            logger.warning("ScenarioPortrayals is null");
//        }
//        ScenarioManager sm = new ScenarioManager(s, sp);
//        return sm;
//    }

    @Override
    public void addSteppables() {
        int conf = new Integer(this.configuration.getProperty(MyShanksSimulation.CONFIGURATION));
        switch (conf) {
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

}
