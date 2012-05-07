/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.datacenter.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Computer;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.datacenter.model.element.link.EthernetCable;
import es.upm.dit.gsi.shanks.datacenter.model.element.link.portrayal.EthernetCable2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * 
 * @author Danny
 *
 */
public class DataCenterScenario2DPortrayal extends Scenario2DPortrayal{

	/**
	 * 
	 * @param scenario
	 * @param width
	 * @param height
	 * @throws DuplicatedPortrayalIDException
	 */
	public DataCenterScenario2DPortrayal(Scenario scenario, int width, int height)
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
		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.COMPUTER_ID+"@"+this.getScenario().getID()), 5, 20);
//		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.WIFI_ROUTER_ID+"@"+this.getScenario().getID()), 30, 30);
//		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.ANDROID_ID+"@"+this.getScenario().getID()), 25, 5);
//		this.situateDevice((Device)this.getScenario().getNetworkElement(Values.TABLET_ID+"@"+this.getScenario().getID()), 15, 5);
		
		this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID+"@"+this.getScenario().getID()));
	}

	@Override
	public void setupPortrayals() {
		
		ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D linksPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
                
        devicesPortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
//        devicesPortrayal.setPortrayalForClass(WifiRouterADSL.class, new WifiRouterADSL2DPortrayal());
//        devicesPortrayal.setPortrayalForClass(WirelessDevice.class, new Smartphone2DPortrayal());
//                
        linksPortrayal.setPortrayalForClass(EthernetCable.class, new EthernetCable2DPortrayal());
	}

}
