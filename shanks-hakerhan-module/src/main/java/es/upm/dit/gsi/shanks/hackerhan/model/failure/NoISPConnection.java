package es.upm.dit.gsi.shanks.hackerhan.model.failure;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.Computer;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class NoISPConnection extends Failure {

	public NoISPConnection(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
	}

	public NoISPConnection(String id) {
		super(id, Values.NO_ISP_FAILURE_PROB);
	}
	
	public NoISPConnection() {
		super(NoISPConnection.class.getName()+System.currentTimeMillis(),
				Values.NO_ISP_FAILURE_PROB);
	}
	
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedProperties(WifiRouterADSL.class, WifiRouterADSL.PROPERTY_CONNECTION, Values.DISCONNECTED);
		this.addPossibleAffectedProperties(Computer.class, Computer.PROPERTY_ETHERNET_CONNECTION, Values.DISCONNECTED);
		this.addPossibleAffectedProperties(WirelessDevice.class, WirelessDevice.PROPERTY_CONNECTION, Values.DISCONNECTED);
	}

	@Override
	public boolean isResolved() {
		List<NetworkElement> affected = this.getAffectedElements();
		for (NetworkElement e : affected){
			HashMap<String, Boolean> state = e.getStatus();
			if (state.containsKey(WifiRouterADSL.STATUS_DISCONNECTED)){
				if(state.get(WifiRouterADSL.STATUS_DISCONNECTED)){
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