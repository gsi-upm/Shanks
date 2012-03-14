/**
 * 
 */
package es.upm.dit.gsi.shanks.model.test.steppable;

import java.util.Collection;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.JasonShanksAgent;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.test.MyJasonShanksAgent;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class FailuresChartPainter implements Steppable {


    public static final String RESOLVED_FAILURES_PER_AGENT_CHART_ID = "Resolved failures per agent";


    public Logger logger = Logger.getLogger(FailuresChartPainter.class.getName());
    
    /**
     * 
     */
    private static final long serialVersionUID = 2669002521740395423L;

    @Override
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
                    if (agent instanceof MyJasonShanksAgent) {
                        int failures = ((MyJasonShanksAgent) agent)
                                .getNumberOfResolvedFailures();
                        scenarioPortrayal
                                .addDataToDataSerieInTimeChart(
                                        RESOLVED_FAILURES_PER_AGENT_CHART_ID,
                                        agent.getID(), steps,
                                        (double) failures);

                    }
                }
            } else {
                for (ShanksAgent agent : simulation.getAgents()) {
                    if (agent instanceof JasonShanksAgent) {
                        logger.info("Total failures resolved by Agent: "
                                + agent.getID()
                                + ": "
                                + ((MyJasonShanksAgent) agent)
                                        .getNumberOfResolvedFailures());
                    }
                }
            }
        } catch (DuplicatedPortrayalIDException e) {
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            e.printStackTrace();
        }
    }

}
