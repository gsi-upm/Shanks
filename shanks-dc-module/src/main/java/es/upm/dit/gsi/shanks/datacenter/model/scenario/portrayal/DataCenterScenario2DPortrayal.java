/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.datacenter.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Computer;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Router;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Server;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.portrayal.Router2DPortrayal;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.portrayal.Server2DPortrayal;
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
public class DataCenterScenario2DPortrayal extends Scenario2DPortrayal {

	/**
	 * 
	 * @param scenario
	 * @param width
	 * @param height
	 * @throws DuplicatedPortrayalIDException
	 */
	public DataCenterScenario2DPortrayal(Scenario scenario, int width,
			int height) throws DuplicatedPortrayalIDException {
		super(scenario, width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#
	 * addPortrayals()
	 */
	@Override
	public void addPortrayals() {
		// Not necessary. Last leave of the tree.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#
	 * placeElements()
	 */
	@Override
	public void placeElements() {

		this.situateDevice(
				(Device) this.getScenario().getNetworkElement(
						Values.BBDD_REPLICA_ID), 5, 60);
		this.situateDevice(
				(Device) this.getScenario().getNetworkElement(
						Values.BBDD_SERVER_ID), 5, 5);
		this.situateDevice(
				(Device) this.getScenario().getNetworkElement(
						Values.LDAP_SERVER_ID), 35, 60);
		this.situateDevice(
				(Device) this.getScenario().getNetworkElement(
						Values.EXTERNAL_SERVER_ID), 65, 5);
		for (int i = 0; i < Values.NUMBER_OF_ITCROW; i++) {
			this.situateDevice(
					(Device) this.getScenario().getNetworkElement(
							Values.COMPUTER_ID + i), 5 + 30 * i, 115);
		}
		this.situateDevice(
				(Device) this.getScenario().getNetworkElement(
						Values.DATA_CENTER_ROUTER_ID), 50, 5);

		for (int i = 0; i < Values.NUMBER_OF_ITCROW; i++) {
			this.drawLink((Link) this.getScenario().getNetworkElement(
					Values.ETHERNET_ID + i+10));
		}
		
		for(int i = 0; i < 5; i++){
			this.drawLink((Link) this.getScenario().getNetworkElement(
					Values.ETHERNET_ID + i));
		}
		this.situateDevice((Device) this.getScenario().getNetworkElement(Values.WEB_PROXY_ID), 50, 60);
		this.situateDevice((Device) this.getScenario().getNetworkElement(Values.WEB_APP_ID), 35, 5);
		// this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID
		// +"1"));
		// this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID
		// +"2"));
		// this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID
		// +"4"));
		// this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID
		// +"5"));
		// this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID
		// +"6"));
		// this.drawLink((Link)this.getScenario().getNetworkElement(Values.ETHERNET_ID
		// +"7"));

	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this
				.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
				.get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		NetworkPortrayal2D linksPortrayal = (NetworkPortrayal2D) this
				.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
				.get(ScenarioPortrayal.LINKS_PORTRAYAL);
		devicesPortrayal.setPortrayalForClass(Computer.class,
				new Computer2DPortrayal());
		devicesPortrayal.setPortrayalForClass(Router.class,
				new Router2DPortrayal());
		devicesPortrayal.setPortrayalForClass(Server.class,
				new Server2DPortrayal());
		linksPortrayal.setPortrayalForClass(EthernetCable.class,
				new EthernetCable2DPortrayal());
	}

}
