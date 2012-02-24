package es.upm.dit.gsi.shanks.model.ftth.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;

public class ONTEmitedLaserFailure extends Failure{

	public ONTEmitedLaserFailure() {
		super(ONTEmitedLaserFailure.class.getName(), 0.1);
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(ONT.class, ONT.LOW_EMI_LASER_POWER);		
	}


	@Override
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		for (NetworkElement element : elements) {
			Integer elp = (Integer) element.getProperty(ONT.emitedLaserPower);
			if(elp >= 40){
				return true;
			}
		}
		return false;
	}
	

}
