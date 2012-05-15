package es.upm.dit.gsi.shanks.datacenter.model.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

public class Router extends RouterDNS{

	public Router(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id);
	}

	
}
