package es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals;

import es.upm.dit.gsi.shanks.networkattacks.util.Values;


public class Server2DPortrayal extends Computer2DPortrayal {

	//TODO ¿make an animation for attack in process? 
	public Server2DPortrayal() {
		super();
		this.width = Values.Computer2DSide;
		this.height = Values.Server2DHeight;
	}
	private static final long serialVersionUID = -1444823260313179619L;
}
