package es.upm.dit.gsi.shanks.model.ftth.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;

public class ONTReceivedLaserFailure extends Failure{

	public ONTReceivedLaserFailure() {
		super(ONTReceivedLaserFailure.class.getName(), 0.1);
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(ONT.class, ONT.LOW_REC_LASER_POWER);
	}


	@Override
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		for (NetworkElement element : elements) {
			Integer rlp = (Integer) element.getProperty(ONT.receivedLaserPower);
			if(rlp >= 40){
				return true;
			}
		}
		return false;
	}
	

}
