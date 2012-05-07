package es.upm.dit.gsi.shanks.datacenter.model.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class Gateway extends Router{

	public Gateway(String id) throws UnsupportedNetworkElementStatusException {
		super(id);
	}
}
