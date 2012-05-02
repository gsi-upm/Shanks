/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * Random failure on computer device.
 * 
 * @author Danny
 * 
 */
public class ComputerFailure extends Failure {

	public ComputerFailure(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
	}

	public ComputerFailure(String id) {
		super(id, Values.COMPUTER_FAILURE_PROB);
	}

	public ComputerFailure() {
		super(ComputerFailure.class.getName()+System.currentTimeMillis(),
				Values.COMPUTER_FAILURE_PROB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(Computer.class, Computer.STATUS_NOK,
				true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
	 */
	@Override
	public boolean isResolved() {
		List<NetworkElement> affected = this.getAffectedElements();
		for (NetworkElement e : affected) {
			HashMap<String, Boolean> state = e.getStatus();
			if (state.containsKey(Computer.STATUS_NOK)) {
				if (state.get(Computer.STATUS_NOK)) {
					return false;
				}
			}
		}
		return true;
	}
}