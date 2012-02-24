package es.upm.dit.gsi.shanks.model.ftth.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.SplitterToONT;

public class ONTFailure extends Failure {

	public ONTFailure() {
		super(ONTFailure.class.getName(), 0.01);
		
	}

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(ONT.class, DeviceDefinitions.NOK_STATUS);
        this.addPossibleAffectedElements(SplitterToONT.class, LinkDefinitions.BROKEN_STATUS);
		
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

}
