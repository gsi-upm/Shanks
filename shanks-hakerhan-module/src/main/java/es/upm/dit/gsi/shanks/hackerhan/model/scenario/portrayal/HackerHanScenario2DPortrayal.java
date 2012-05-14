/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.hackerhan.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.portrayal.Smartphone2DPortrayal;
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

/**
 * 
 * @author Danny
 *
 */
public class HackerHanScenario2DPortrayal extends Scenario2DPortrayal{

	/**
	 * 
	 * @param scenario
	 * @param width
	 * @param height
	 * @throws DuplicatedPortrayalIDException
	 */
	public HackerHanScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException {
		super(scenario, width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#addPortrayals()
	 */
	@Override
	public void addPortrayals() {
		// Not necessary. Last leave of the tree. 
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
	 */
	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.COMPUTER_ID+"@"+this.getScenario().getID()), 0, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.WIFI_ROUTER_ID), 40, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.ANDROID_ID+"@"+this.getScenario().getID()), 0, 40);
		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.TABLET_ID+"@"+this.getScenario().getID()), 0, 20);
		
		this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID+"@"+this.getScenario().getID()));
	}

	@Override
	public void setupPortrayals() {
		
		ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D linksPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
                
        devicesPortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicesPortrayal.setPortrayalForClass(WifiRouterADSL.class, new WifiRouterADSL2DPortrayal());
        devicesPortrayal.setPortrayalForClass(WirelessDevice.class, new Smartphone2DPortrayal());
                
        linksPortrayal.setPortrayalForClass(EthernetLink.class, new EthernetCable2DPortrayal());
	}

}
