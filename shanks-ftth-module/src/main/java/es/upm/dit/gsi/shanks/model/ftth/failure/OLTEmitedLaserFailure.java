package es.upm.dit.gsi.shanks.model.ftth.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.OLTtoSplitter;

/**
 * @author dlara
 *
 */
public class OLTEmitedLaserFailure extends Failure{

	public OLTEmitedLaserFailure() {
		super(OLTEmitedLaserFailure.class.getName(), 0.1);
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
     */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(OLT.class, OLT.LOW_EMI_LASER_POWER);
	}
	
	
	
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		for (NetworkElement element : elements) {
			Integer elp = (Integer) element.getProperty(OLT.emitedLaserPower);
			if(elp >= 40){
				return true;
			}			
		}
		return false;
		
	}
	

}
