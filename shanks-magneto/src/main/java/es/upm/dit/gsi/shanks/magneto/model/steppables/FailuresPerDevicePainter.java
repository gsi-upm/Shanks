package es.upm.dit.gsi.shanks.magneto.model.steppables;

import java.awt.Color;
import java.util.Collection;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgent;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import sim.engine.SimState;
import sim.engine.Steppable;

public class FailuresPerDevicePainter implements Steppable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3623528773176926561L;
	
	public double[][] failures;
	public int posicion;
	public boolean flag;
	
	public static final String PLOT_ID = "Location of agent";
	
	
	public FailuresPerDevicePainter(){
		this.failures = new double[2][1000];
//		for(int i = 0; i < 1000; i++){
//			failures[0][i] = 175;
//		}
//		for(int j = 0; j < 1000; j++){
//			failures[1][j] = 175;
//		}
		this.posicion = 0;
		this.flag = true;
	}

	public void step(SimState sim) {
		ShanksSimulation simulation = (ShanksSimulation) sim;
		
			try {
				ScenarioPortrayal scenarioPortrayal = simulation.getScenarioPortrayal();
				Collection<ShanksAgent> agents = simulation
                        .getAgents();
//				if(!flag){
//					scenarioPortrayal.removeScatterPlot(PLOT_ID);
//				}
				for(ShanksAgent ag : agents){
					if(ag instanceof FixAgent){
						Location loc = ((FixAgent) ag).getCurrentLocation();
						failures[0][posicion] = loc.getLocation2D().getX();
						failures[1][posicion] = loc.getLocation2D().getY();
						System.out.println("X----->" + loc.getLocation2D().getX());
						System.out.println("Y----->" + loc.getLocation2D().getY());
						posicion++;
					}
				}
				scenarioPortrayal.addDataSerieToScatterPlot(PLOT_ID, failures);
//				this.flag = false;
				scenarioPortrayal.getScatterPlot(PLOT_ID).setBackground(Color.black);
			
			
			
			} catch (ShanksException e) {
				e.printStackTrace();
			}
		
	}

}
