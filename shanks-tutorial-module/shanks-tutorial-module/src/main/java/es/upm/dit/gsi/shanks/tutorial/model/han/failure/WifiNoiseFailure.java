/**
 * 
 */
package es.upm.dit.gsi.shanks.tutorial.model.han.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.WifiConnection;

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
		this.addPossibleAffectedElements(WifiConnection.class, WifiConnection.STATUS_INTERFEARENCES);
	}

}
