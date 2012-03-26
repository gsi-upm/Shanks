/**
 * 
 */
package es.upm.dit.gsi.shanks.tutorial.model.han.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.EthernetRouter;

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
		this.addPossibleAffectedElements(EthernetRouter.class, EthernetRouter.STATUS_NOISP_SERVICE);
		this.addPossibleAffectedElements(Computer.class, Computer.STATUS_DISCONNECTED);
	}

}
