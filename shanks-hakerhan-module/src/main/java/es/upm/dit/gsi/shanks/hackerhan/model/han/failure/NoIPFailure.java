/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * 
 * @author darofar
 *
 */
public class NoIPFailure extends Failure {

	public NoIPFailure(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
	}
	
	public NoIPFailure(String id) {
		super(id, Values.NO_IP_FAILURE_PROB);
	}
	
	public NoIPFailure() {
		super(NoIPFailure.class.getName()+System.currentTimeMillis(),
				Values.NO_IP_FAILURE_PROB);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {	
		this.addPossibleAffectedElements(WifiRouterADSL.class, WifiRouterADSL.STATUS_NOISP_SERVICE, true);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_ETHERNET_CONNECTION, Values.DISCONNECTED);
		this.addPossibleAffectedProperties(WirelessDevice.class, WirelessDevice.PROPERTY_CONNECTION, Values.DISCONNECTED);
	}

	@Override
	public boolean isResolved() {
		List<NetworkElement> affected = this.getAffectedElements();
		for (NetworkElement e : affected){
			HashMap<String, Boolean> state = e.getStatus();
			if (state.containsKey(WifiRouterADSL.STATUS_NOISP_SERVICE)){
				if(state.get(WifiRouterADSL.STATUS_NOISP_SERVICE)){
					return false;
				}
			} else if(state.containsKey(Computer.STATUS_DISCONNECTED)) {
				if(state.get(Computer.STATUS_DISCONNECTED)){
					return false;
				}
			} else if(state.containsKey(WirelessDevice.STATUS_DISCONNECTED)) {
				if(state.get(WirelessDevice.STATUS_DISCONNECTED)){
					return false;
				}
			}
		}
		return true;
	}
}