package es.upm.dit.gsi.shanks.tutorial.model.adsl.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.tutorial.model.adsl.element.device.DSLAM;

public class ISPCongestionFailure extends Failure {

	public ISPCongestionFailure() {
		super(ISPCongestionFailure.class.getName(), 0.05);
	}

	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(DSLAM.class, DSLAM.STATUS_CONGESTED);
	}

}
