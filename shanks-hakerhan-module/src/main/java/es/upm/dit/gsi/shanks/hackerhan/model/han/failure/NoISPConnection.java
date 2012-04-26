package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;

public class NoISPConnection extends Failure {

	public NoISPConnection(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
		// TODO Auto-generated constructor stub
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
