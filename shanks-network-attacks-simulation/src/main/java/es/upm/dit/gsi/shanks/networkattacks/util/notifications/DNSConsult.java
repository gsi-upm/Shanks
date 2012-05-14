/**
 * es.upm.dit.gsi.shanks
 * 14/05/2012
 */
package es.upm.dit.gsi.shanks.networkattacks.util.notifications;

import java.util.List;

import es.upm.dit.gsi.shanks.networkattacks.util.Values;
import es.upm.dit.gsi.shanks.notification.InteractionNotification;

/**
 * 
 * @author darofar
 *
 */
public class DNSConsult extends InteractionNotification {

	/**
	 * 
	 */
	private static int ConsultNumber = 0;
	
	/**
	 * 
	 * @param source
	 * @param target
	 */
	public DNSConsult(Object source, List<Object> target) {
		super(Values.DNSConsultID+DNSConsult.ConsultNumber, 
				Values.sim.getSchedule().getSteps(), 
				source, DNSConsult.class.getName(), target);
		DNSConsult.ConsultNumber = ConsultNumber+1;
	}

	/**
	 * @return the consultNumber
	 */
	public static int getConsultNumber() {
		return ConsultNumber;
	}

}
