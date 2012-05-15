package es.upm.dit.gsi.shanks.workerroom.model.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;



/**
 * Class that represent a router
 * 
 * @author dlara
 *
 */
public class LANRouter extends RouterDNS {

	public LANRouter(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id);
	}
}