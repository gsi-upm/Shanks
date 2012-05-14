package es.upm.dit.gsi.shanks.hackerhan.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.portrayal.WifiRouterADSL2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.EthernetLink;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.EthernetCable2DPortrayal;

public class HANScenario2DPortrayal extends Scenario2DPortrayal {
	

	/**
	 * @param scenario
	 * @param width
	 * @param height
	 * @throws DuplicatedPortrayalIDException
	 */
	public HANScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException {
		super(scenario, width, height);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
	 */
	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC"), 5, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router"), 30, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Modem"), 40, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("WifiAccessPoint"), 35, 25);
		this.situateDevice((Device)this.getScenario().getNetworkElement("iPhone"), 15, 5);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Android"), 25, 5);
		
		this.drawLink((Link)this.getScenario().getNetworkElement("Ethernet: PC-Router"));
		this.drawLink((Link)this.getScenario().getNetworkElement("InternalBus_MRW"));
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
	 */
	@Override
	public void setupPortrayals() {
        ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D linksPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
                
        devicesPortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicesPortrayal.setPortrayalForClass(WifiRouterADSL.class, new WifiRouterADSL2DPortrayal());
//        devicesPortrayal.setPortrayalForClass(ModemADSL.class, new ModemADSL2DPortrayal());
//        devicesPortrayal.setPortrayalForClass(WifiAccessPoint.class, new WifiAccessPoint2DPortrayal());
//        devicesPortrayal.setPortrayalForClass(WirelessDevice.class, new Smartphone2DPortrayal());
                
        linksPortrayal.setPortrayalForClass(EthernetLink.class, new EthernetCable2DPortrayal());
//        linksPortrayal.setPortrayalForClass(InternalBus.class, new InternalBus2DPortrayal());
	}

	@Override
	public void addPortrayals() {
		// Not necessary. Last leave of the tree.  
	}
}
