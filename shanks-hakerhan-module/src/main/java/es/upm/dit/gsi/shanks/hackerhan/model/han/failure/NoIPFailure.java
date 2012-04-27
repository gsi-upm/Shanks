/**
 * 
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * @author a.carrera
 *
 */
public class NoIPFailure extends Failure {

	public NoIPFailure() {
		super(NoIPFailure.class.getName(), 0.01);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(WifiRouterADSL.class, WifiRouterADSL.STATUS_NOISP_SERVICE, true);
		this.addPossibleAffectedElements(Computer.class, Computer.STATUS_DISCONNECTED, true);
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

}
