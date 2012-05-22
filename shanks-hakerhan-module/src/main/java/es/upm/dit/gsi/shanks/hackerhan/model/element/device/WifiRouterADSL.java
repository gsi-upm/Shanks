/**
 * es.upm.dit.gsi
 * 30/04/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

/**
 * The access point for HANs to the ISP network. this device can be blocked to prevent 
 * Hackers making attacks.  
 * 
 * @author darofar
 *
 */
public class WifiRouterADSL extends RouterDNS {

	public WifiRouterADSL(String id)
			throws UnsupportedNetworkElementFieldException {
		super(id);
	}
}