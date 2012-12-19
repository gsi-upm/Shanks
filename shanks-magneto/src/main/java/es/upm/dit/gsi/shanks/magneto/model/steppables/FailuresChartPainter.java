package es.upm.dit.gsi.shanks.magneto.model.steppables;

import java.util.Collection;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgent;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgentBecario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import sim.engine.SimState;
import sim.engine.Steppable;


public class FailuresChartPainter implements Steppable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1686326514151242655L;
	public static final String FAILURES_RESOLVED_ID = "Resolved Failures";
    public Logger logger = Logger.getLogger(FailuresChartPainter.class.getName());


	public void step(SimState sim) {
        ShanksSimulation simulation = (ShanksSimulation) sim;
        try {
            ScenarioPortrayal scenarioPortrayal = simulation
                    .getScenarioPortrayal();
            if (scenarioPortrayal != null) {
                double steps = simulation.schedule.getSteps();
                Collection<ShanksAgent> agents = simulation
                        .getAgents();
                for (ShanksAgent agent : agents) {
                    if (agent instanceof FixAgent) {
                        int failures = ((FixAgent) agent)
                                .getResolvedFailures();
                        scenarioPortrayal
                                .addDataToDataSerieInTimeChart(
                                        FAILURES_RESOLVED_ID,
                                        agent.getID(), steps,
                                         failures);
                    }
                    if (agent instanceof FixAgentBecario) {
                        int failures = ((FixAgentBecario) agent)
                                .getResolvedFailures();
                        scenarioPortrayal
                                .addDataToDataSerieInTimeChart(
                                        FAILURES_RESOLVED_ID,
                                        agent.getID(), steps,
                                         failures);
                    }
                   
                }
            } else {
                for (ShanksAgent agent : simulation.getAgents()) {
                    if (agent instanceof FixAgent) {
                        logger.info("Total failures resolved by Agent: "
                                + agent.getID()
                                + ": "
                                + ((FixAgent) agent)
                                        .getResolvedFailures());
                    }
                    if (agent instanceof FixAgentBecario) {
                    	logger.info("Total failures resolved by Agent: "
                                + agent.getID()
                                + ": "
                                + ((FixAgent) agent)
                                        .getResolvedFailures());
                    }
                }
            }
        } catch (DuplicatedPortrayalIDException e) {
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            e.printStackTrace();
        } catch (ShanksException e) {
            e.printStackTrace();
        }
    }


}
