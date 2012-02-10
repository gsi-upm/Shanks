package es.upm.dit.gsi.shanks.model.ftth.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.OLTtoSplitter;
import es.upm.dit.gsi.shanks.model.ftth.element.link.SplitterToONT;

public class LinkFailure extends Failure{
	
	public LinkFailure() {
		super(LinkFailure.class.getName(), 0.05);
		
	}
	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(SplitterToONT.class, LinkDefinitions.BROKEN_STATUS);
        this.addPossibleAffectedElements(OLTtoSplitter.class, LinkDefinitions.BROKEN_STATUS);
		
	}

}
