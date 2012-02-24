package es.upm.dit.gsi.shanks.model.ftth.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.OLTtoSplitter;

public class OLTReceivedLaserFailure extends Failure{

	public OLTReceivedLaserFailure() {
		super(OLTReceivedLaserFailure.class.getName(), 0.1);
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(OLT.class, OLT.LOW_REC_LASER_POWER);
	}

	
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		for(NetworkElement element : elements){
			Integer rlp = (Integer) element.getProperty(OLT.receivedLaserPower);
			if(rlp >= 40){
				return true;
			}
		}

		
		return false;
	}
}
