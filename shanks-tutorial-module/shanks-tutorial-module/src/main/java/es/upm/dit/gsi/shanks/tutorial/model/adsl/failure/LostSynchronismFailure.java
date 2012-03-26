package es.upm.dit.gsi.shanks.tutorial.model.adsl.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.ModemADSL;


/**
 * @author a.carrera
 *
 */
public class LostSynchronismFailure extends Failure {

	public LostSynchronismFailure() {
		super(LostSynchronismFailure.class.getName(), 0.005);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(ModemADSL.class, ModemADSL.STATUS_NO_ADSL_CONNECTION);
	}

	@Override
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		boolean resolved = false;
		for (NetworkElement element : elements) {
			if (element instanceof ModemADSL) {
				if (element.getCurrentStatus().equals(ModemADSL.STATUS_OK)) {
					resolved = true;
				} else {
					resolved = false;
					break;
				}
			}
		}
		return resolved;
	}

}
