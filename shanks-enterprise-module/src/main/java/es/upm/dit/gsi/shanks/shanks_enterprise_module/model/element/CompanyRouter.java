package es.upm.dit.gsi.shanks.shanks_enterprise_module.model.element;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

public class CompanyRouter extends RouterDNS{

	
	public CompanyRouter(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id);
	}
}
