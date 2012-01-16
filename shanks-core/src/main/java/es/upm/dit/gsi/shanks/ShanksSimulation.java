package es.upm.dit.gsi.shanks;

import java.util.logging.Logger;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import es.upm.dit.gsi.shanks.model.ScenarioManager;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

/**
 * Model class
 * 
 * This class represents the model which manage all the simulation
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public abstract class ShanksSimulation extends SimState {

    private static final long serialVersionUID = -2238530527253654867L;

    public Logger logger = Logger.getLogger(ShanksSimulation.class.getName());

    public static SparseGrid2D problems;

    private ScenarioManager scenarioManager;
//    private ScenarioPortrayal scenarioPortrayal;
//    public Continuous3D elements3d;
//    public Continuous3D legend;

    public int numOfResolvedFailures;

    /**
     * @param seed
     */
    public ShanksSimulation(long seed) {
        super(seed);
        this.scenarioManager = new ScenarioManager(this.getScenario());
//        this.setScenarioPortrayal(this.getScenarioPortrayal());
        logger.fine("Simulation constructor ->");
    }

    /**
     * This method will set all required information about Scenario
     */
    abstract public Scenario getScenario();

//    /**
//     * @return The portrayal of the scenario
//     */
//    abstract public ScenarioPortrayal getScenarioPortrayal();
//
//
//    /**
//     * @param scenarioPortrayal the scenarioPortrayal to set
//     */
//    public void setScenarioPortrayal(ScenarioPortrayal scenarioPortrayal) {
//        this.scenarioPortrayal = scenarioPortrayal;
//    }

    /* (non-Javadoc)
     * @see sim.engine.SimState#start()
     */
    @Override
    public void start() {
        super.start();
        logger.finer("-> start method");
        startSimulation();
    }

//    public void createLegend() {
//        GatewayRouter g = new GatewayRouter("Gateway", 0, 45, ScenarioDefinitions.GATEWAY);
//        Splitter sp1 = new Splitter("Splitter 1", 0, 45, ScenarioDefinitions.SPLITTER1);
//        Splitter sp2 = new Splitter("Splitter 2", 0, 45, ScenarioDefinitions.SPLITTER2);
//        OLT olt = new OLT("OLT", 0, 45, ScenarioDefinitions.OLT);
//        ONT ont = new ONT("ONT", 0, 45, ScenarioDefinitions.ONT);
//        legend.setObjectLocation(g, new Double3D(-300, 300, 0));
//        legend.setObjectLocation(sp1, new Double3D(-100, 300, 0));
//        legend.setObjectLocation(sp2, new Double3D(100, 300, 0));
//        legend.setObjectLocation(olt, new Double3D(300, 300, 0));
//        legend.setObjectLocation(ont, new Double3D(-300, 0, 0));
//    }

    // 
    /**
     * The initial configuration of the scenario
     */
    public void startSimulation() {
//        devicesnetwork.clear();
//        elements3d = new Continuous3D(5, gridWidth, gridHeight, gridHeight);
//        links1 = new Network();
//        problems = new SparseGrid2D(365, 50);
//        legend = new Continuous3D(5, gridWidth, gridHeight, gridHeight);
//        createLegend();
//        ScenarioManager.totalproblems = 0;
//        switch (selectScenario()) {
//        case 0:
//            createFTTH();
//            m = new ScenarioManager(scenario);
//            System.out.println("SELECTED SCENARIO " + scenario.getName());
//            break;
//        case 1:
//            createPPP();
//            m = new ScenarioManager(scenario);
//            System.out.println("SELECTED SCENARIO " + scenario.getName());
//            break;
//        }
//        Failure.createDeviceErrors();
//        Agent a = new Agent();
        schedule.scheduleRepeating(Schedule.EPOCH, 0, this.scenarioManager, 2);
//        schedule.scheduleRepeating(Schedule.EPOCH + 1, 2, a, 2);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        doLoop(ShanksSimulation.class, args);
        System.exit(0);
    }

}
