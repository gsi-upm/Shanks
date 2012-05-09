package es.upm.dit.gsi.shanks.model.workerroom.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Computer;

public class ComputerPruebaFailure extends Failure{

	public ComputerPruebaFailure() {
		super("JAJAS", 0.1);
	}

	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_CPUFREQ, 1000000);
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

}
