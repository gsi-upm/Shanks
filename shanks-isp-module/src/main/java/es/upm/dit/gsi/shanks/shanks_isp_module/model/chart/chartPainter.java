package es.upm.dit.gsi.shanks.shanks_isp_module.model.chart;

import java.util.Collection;

import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;

public class chartPainter implements Steppable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5816221433013368456L;

	@Override
	public void step(SimState sim) {
		ShanksSimulation simulation = (ShanksSimulation) sim;
		try{
			ScenarioPortrayal scenarioPortrayal = simulation.getScenarioPortrayal();
			Collection<ShanksAgent> agents = simulation.getAgents();
			for(ShanksAgent agent : agents){
				if(agent instanceof Hacker){
					Hacker hacker = (Hacker) agent;
					scenarioPortrayal.addDataToDataSerieInTimeChart(Values.DDOS_NUMBER, hacker.getID(), simulation.schedule.getSteps(), Hacker.getNumberOfDDoSAttacks());
					scenarioPortrayal.addDataToDataSerieInTimeChart(Values.ROOT_NUMBER, hacker.getID(), simulation.schedule.getSteps(), Hacker.getNumberOfRootAttacks());
					scenarioPortrayal.addDataToDataSerieInTimeChart(Values.SQL_NUMBER, hacker.getID(), simulation.schedule.getSteps(), Hacker.getNumberOfSQLAttacks());
				
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
