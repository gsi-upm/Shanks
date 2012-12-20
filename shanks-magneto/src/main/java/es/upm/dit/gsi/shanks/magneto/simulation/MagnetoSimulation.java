package es.upm.dit.gsi.shanks.magneto.simulation;

import java.util.Properties;

import sim.engine.Schedule;
import sim.engine.Steppable;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgent;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgentBecario;
import es.upm.dit.gsi.shanks.magneto.model.steppables.ErrorsInDevicesPainter;
import es.upm.dit.gsi.shanks.magneto.model.steppables.FailuresPerDevicePainter;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.steppable.FailureLog;
import es.upm.dit.gsi.shanks.model.test.steppable.FailuresChartPainter;
import es.upm.dit.gsi.shanks.model.test.steppable.FailuresGUI;

public class MagnetoSimulation extends ShanksSimulation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700007277544949836L;
	
	private Properties configuration;
    public static final String CONFIGURATION = "Configuration";
    public int counter;


	public MagnetoSimulation(long seed, Class<? extends Scenario> scenarioClass,
            String scenarioID, String initialState, Properties properties,
            Properties configPropertiesLANSimulation) throws ShanksException {
		
		super(seed, scenarioClass, scenarioID, initialState, properties);
		this.configuration = configPropertiesLANSimulation;
		this.counter = 0;
	}
	
	 /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.ShanksSimulation#addSteppables()
     */
    @Override
    public void addSteppables() {
        int conf = new Integer(
                this.configuration
                        .getProperty(MyShanksSimulation.CONFIGURATION));
        switch (conf) {
        case 0:
            logger.fine("Nothing to do here... No more steppables");
            break;
        case 1:
            Steppable steppable = new FailureLog();
            schedule.scheduleRepeating(Schedule.EPOCH, 3, steppable, 500);
            break;
        case 2:
            Steppable failuresgui = new FailuresGUI();
            schedule.scheduleRepeating(Schedule.EPOCH, 4, failuresgui, 1);
            break;
        case 3:
            Steppable chart = new es.upm.dit.gsi.shanks.magneto.model.steppables.FailuresChartPainter();
            schedule.scheduleRepeating(Schedule.EPOCH, 3, chart, 50);
            Steppable histogram = new ErrorsInDevicesPainter();
            schedule.scheduleRepeating(Schedule.EPOCH, 3, histogram, 50);
            Steppable plot = new FailuresPerDevicePainter();
            schedule.scheduleRepeating(Schedule.EPOCH, 3, plot, 50);


            break;
        default:
            logger.info("No configuration for Magneto Simulation. Configuration 0 loaded -> default");
        }

    }

	
	public void registerShanksAgents() throws ShanksException {
		FixAgent agent = new FixAgent("FixAgent");
		this.registerShanksAgent(agent);
		
		FixAgentBecario agentBecario = new FixAgentBecario("Becario");
		this.registerShanksAgent(agentBecario);
	}

}
