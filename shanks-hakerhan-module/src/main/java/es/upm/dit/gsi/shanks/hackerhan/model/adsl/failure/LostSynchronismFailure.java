package es.upm.dit.gsi.shanks.hackerhan.model.adsl.failure;

import java.util.HashMap;
import java.util.Set;

import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.ModemADSL;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;


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
		this.addPossibleAffectedElements(ModemADSL.class, ModemADSL.STATUS_NO_ADSL_CONNECTION, true);
	}

	@Override
	public boolean isResolved() {
		//TODO check the new version of affected elements. 
		Set<NetworkElement> elements = (Set<NetworkElement>) this.getAffectedElements();
		boolean resolved = false;
		for (NetworkElement element : elements) {
			if (element instanceof ModemADSL) {
		        // TODO Adapt the hole thing to hasMapa String/Boolean.
				if (element.getStatus().equals(ModemADSL.STATUS_OK)) {
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
