package es.upm.dit.gsi.shanks.datacenter.model.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

public class DCRouter extends RouterDNS{

	public DCRouter(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id);
	}

	
}
