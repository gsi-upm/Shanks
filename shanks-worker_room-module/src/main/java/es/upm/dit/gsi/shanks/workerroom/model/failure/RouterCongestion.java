package es.upm.dit.gsi.shanks.workerroom.model.failure;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Router;

public class RouterCongestion extends ProbabilisticNetworkElementEvent{

	
	public RouterCongestion(Steppable generator) {
		super(RouterCongestion.class.getName(), generator, 0.95);
	}

	@Override
	public void interactWithNE() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPossibleAffected() {
		this.addPossibleAffectedProperties(Router.class, Router.PROPERTY_CONGESTION,100.0);
	}

}
