package es.upm.dit.gsi.shanks.magneto.agent;

import java.util.ArrayList;
import java.util.List;

import sim.util.Double2D;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.test.MyShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.movement.MobileShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.ShanksAgentMovementCapability;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.agent.action.UniversalFixAction;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public class FixAgent extends SimpleShanksAgent implements MobileShanksAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8882475458980283987L;
	private double prob_fix = 1;
	private Location currentLocation;
	private Location targetLocation;
	private Double speed;
	private Double perceptionRange;
	public Location location;

	public FixAgent(String id) {
		super(id);
		this.location = new Location(new Double2D(80, 80));
		this.setCurrentLocation(location);
	}

	public void checkMail() {
		
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
			ShanksAgentMovementCapability.updateLocation(simulation,
					this, location);
			double random = Math.random();
			UniversalFixAction act = new UniversalFixAction("Fix", this);
			List <NetworkElement> ne = new ArrayList<NetworkElement>();
			for(String s : simulation.getScenario().getCurrentElements().keySet()){
				ne.add(simulation.getScenario().getCurrentElements().get(s));
			}
      
			if(!simulation.getScenario().getCurrentFailures().isEmpty()){
				try {
					double newprob = random * simulation.getScenario().getCurrentFailures().size();
					if(newprob > prob_fix){
						act.executeAction(simulation, this, ne);
		    
					}
				} catch (UnsupportedNetworkElementFieldException e) {
					e.printStackTrace();
				} catch (UnsupportedScenarioStatusException e) {
					e.printStackTrace();
				} catch (ShanksException e) {
					e.printStackTrace();
				}
		
			}
	
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void stopMovement() {
		
	}

	public void startMovement() {
		
	}

	public boolean isAllowedToMove() {
		return true;
	}

	public Location getCurrentLocation() {
		return this.currentLocation;
	}

	public Location getTargetLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCurrentLocation(Location location) {
		this.currentLocation = location;
	}

	public void setTargetLocation(Location location) {
		this.targetLocation= location;		
	}

}
