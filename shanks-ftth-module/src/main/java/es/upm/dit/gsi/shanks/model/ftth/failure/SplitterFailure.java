package es.upm.dit.gsi.shanks.model.ftth.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.SplitterToONT;

public class SplitterFailure extends Failure{

	public SplitterFailure(String id, double occurrenceProbability) {
		super(SplitterFailure.class.getName(), 0.05);
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(Splitter.class, DeviceDefinitions.NOK_STATUS);
        //this.addPossibleAffectedElements(SplitterToONT.class,LinkDefinitions.BROKEN_STATUS);
	}

}
