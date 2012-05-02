/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * Random failures on wireless devices. 
 * @author Danny
 *
 */
public class WirelessDeviceFailure extends Failure{
	
	public WirelessDeviceFailure(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
	}

	public WirelessDeviceFailure(String id) {
		super(id, Values.WIRELESSD_FAILURE_PROB);
	}

	public WirelessDeviceFailure() {
		super(WirelessDeviceFailure.class.getName()+System.currentTimeMillis()
				, Values.WIRELESSD_FAILURE_PROB);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(WirelessDevice.class, WirelessDevice.STATUS_NOK, true);
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
	 */
	@Override
	public boolean isResolved() {
		List<NetworkElement> affected = this.getAffectedElements();
		for (NetworkElement e : affected){
			HashMap<String, Boolean> state = e.getStatus();
			if (state.containsKey(WirelessDevice.STATUS_NOK)){
				if(state.get(WirelessDevice.STATUS_NOK)){
					return false;
				}
			}
		}
		return true;
	}
}