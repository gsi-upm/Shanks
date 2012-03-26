/**
 * 
 */
package es.upm.dit.gsi.shanks.tutorial.model.han.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 *
 */
public class InternalBus extends Link {

	
	public static final String STATUS_OK = "OK";
	public static final String STATUS_DAMAGED = "DAMAGED";

	public static final String PROPERTY_LENGTH = "Length"; // In meters
	public static final String PROPERTY_PACKETLOSSRATIO = "Packet loss ratio";
	
	private static final double LOSS_RATIO = 0.00001;

	/**
	 * Constructor ethernet cable
	 * 
	 * @param id
	 * @param length
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public InternalBus(String id, double length)
			throws UnsupportedNetworkElementStatusException {
		super(id, InternalBus.STATUS_OK, 3);
		this.changeProperty(InternalBus.PROPERTY_LENGTH, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		String status = this.getCurrentStatus();
		if (status.equals(InternalBus.STATUS_OK)) {
			this.changeProperty(InternalBus.PROPERTY_PACKETLOSSRATIO, LOSS_RATIO);
		}  else if (status.equals(InternalBus.STATUS_DAMAGED)) {
			this.changeProperty(InternalBus.PROPERTY_PACKETLOSSRATIO, 0.3);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		double ratio = (Double) this.getProperty(InternalBus.PROPERTY_PACKETLOSSRATIO);
		if (ratio < 0.01) {
			this.updateStatusTo(InternalBus.STATUS_OK);
		} else {
			this.updateStatusTo(InternalBus.STATUS_DAMAGED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(InternalBus.PROPERTY_LENGTH, 0.1);
		this.addProperty(InternalBus.PROPERTY_PACKETLOSSRATIO, LOSS_RATIO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(InternalBus.STATUS_OK);
		this.addPossibleStatus(InternalBus.STATUS_DAMAGED);
	}

}
