package es.upm.dit.gsi.shanks.model.ftth.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;

public class OLTFailure extends Failure{

	public OLTFailure() {
		super(OLTFailure.class.getName(), 0.1);
		
	}
	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(OLT.class, DeviceDefinitions.NOK_STATUS);
		this.addPossibleAffectedElements(Splitter.class, DeviceDefinitions.NOK_STATUS);
        //this.addPossibleAffectedElements(OLTtoSplitter.class, LinkDefinitions.BROKEN_STATUS);
		
	}

}
