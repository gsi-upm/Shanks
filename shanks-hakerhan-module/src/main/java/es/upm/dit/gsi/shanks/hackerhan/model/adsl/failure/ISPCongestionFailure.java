package es.upm.dit.gsi.shanks.hackerhan.model.adsl.failure;

import es.upm.dit.gsi.shanks.hackerhan.model.adsl.element.device.DSLAM;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class ISPCongestionFailure extends Failure {

	public ISPCongestionFailure() {
		super(ISPCongestionFailure.class.getName(), 0.05);
	}

	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(DSLAM.class, DSLAM.STATUS_CONGESTED, true);
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

}
