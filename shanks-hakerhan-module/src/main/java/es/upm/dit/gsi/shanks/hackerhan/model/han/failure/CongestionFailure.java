package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;

public class CongestionFailure extends Failure {

	public CongestionFailure(double occurrenceProbability) {
		super(CongestionFailure.class.getName(), occurrenceProbability);
	}

	public CongestionFailure(String id, double occurrenceProbability) {
		super(CongestionFailure.class.getName(), occurrenceProbability);
	}
	
	public CongestionFailure() {
		super(CongestionFailure.class.getName(), 0.05);
	}
	
	@Override
	public void addPossibleAffectedElements() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

}
