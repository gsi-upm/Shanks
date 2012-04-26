/**
 * 
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import es.upm.dit.gsi.shanks.hackerhan.model.han.element.link.WifiConnection;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * @author a.carrera
 *
 */
public class WifiNoiseFailure extends Failure {

	public WifiNoiseFailure() {
		super(WifiNoiseFailure.class.getName(), 0.01);
	}

	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(WifiConnection.class, WifiConnection.STATUS_INTERFEARENCES, false);
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

}
