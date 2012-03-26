/**
 * 
 */
package es.upm.dit.gsi.shanks.tutorial.model.han.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.Computer;

/**
 * @author a.carrera
 *
 */
public class BrokenFanFailure extends Failure {

	public BrokenFanFailure() {
		super(BrokenFanFailure.class.getName(),0.001);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(Computer.class, Computer.STATUS_HIGHTEMP);
	}

}
