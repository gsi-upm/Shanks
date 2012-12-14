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
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.perception.ShanksAgentPerceptionCapability;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.agent.action.UniversalFixAction;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public class FixAgent extends SimpleShanksAgent implements MobileShanksAgent, PercipientShanksAgent{

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
		this.location = new Location(new Double2D(0, 0));
		this.setCurrentLocation(location);
		this.speed = 10.0;
	}

	public void checkMail() {
		
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
			ShanksAgentMovementCapability.updateLocation(simulation,
					this, location);
			
			UniversalFixAction act = new UniversalFixAction("Fix", this);
			List <NetworkElement> ne = new ArrayList<NetworkElement>();
			for(String s : simulation.getScenario().getCurrentElements().keySet()){
				ne.add(simulation.getScenario().getCurrentElements().get(s));
			}
      
			if(!simulation.getScenario().getCurrentFailures().isEmpty()){
				try {
					for(NetworkElement n : ne){
						if(!n.getStatus().get(UserGateway.STATUS_OK)){
							Location newLoc = ShanksAgentPerceptionCapability.getObjectLocation(simulation, this, n);
							ShanksAgentMovementCapability.goTo(simulation, this, location, newLoc, speed);
								double random = Math.random();
								double newprob = random * simulation.getScenario().getCurrentFailures().size();
							if(newprob > prob_fix){
								act.executeAction(simulation, this, ne);
				    
							}
							
						}
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
		return this.targetLocation;
	}

	public void setCurrentLocation(Location location) {
		this.currentLocation = location;
	}

	public void setTargetLocation(Location location) {
		this.targetLocation= location;		
	}

	public double getPerceptionRange() {
		return this.perceptionRange;
	}

}
