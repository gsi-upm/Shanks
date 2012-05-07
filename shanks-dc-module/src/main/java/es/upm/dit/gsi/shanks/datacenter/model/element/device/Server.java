package es.upm.dit.gsi.shanks.datacenter.model.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class Server extends Computer {
	
	public Server(String id) throws UnsupportedNetworkElementStatusException {
		super(id);
	}

}