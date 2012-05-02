/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.han.failure;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * Random failure on Wifi-Router. 
 * 
 * @author Danny
 *
 */
public class RouterFailure extends Failure{

	public RouterFailure(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
	}

	public RouterFailure(String id) {
		super(id, Values.ROUTER_FAILURE_PROB);
	}

	public RouterFailure() {
		super(RouterFailure.class.getName()+System.currentTimeMillis(),
				Values.ROUTER_FAILURE_PROB);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedElements(WifiRouterADSL.class, WifiRouterADSL.STATUS_CONGESTED, true);
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
			if (state.containsKey(WifiRouterADSL.STATUS_CONGESTED)){
				if(state.get(WifiRouterADSL.STATUS_CONGESTED)){
					return false;
				}
			}
		}
		return true;
	}
}