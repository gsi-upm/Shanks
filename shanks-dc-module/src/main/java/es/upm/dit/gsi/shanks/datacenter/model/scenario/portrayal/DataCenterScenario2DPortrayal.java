/**
 * es.upm.dit.gsi
 * 01/05/2012
 */
package es.upm.dit.gsi.shanks.datacenter.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.EthernetLink;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.EthernetCable2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Router2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Server2DPortrayal;

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

		this.situateDevice((Device)this.getScenario()
				.getNetworkElement(Values.BBDD_SERVER_ID), 5, 5);
		this.situateDevice((Device) this.getScenario()
				.getNetworkElement(Values.BBDD_REPLICA_ID), 5, 25);
		this.situateDevice((Device) this.getScenario()
				.getNetworkElement(Values.LDAP_SERVER_ID), 25, 5);
		this.situateDevice((Device) this.getScenario()
				.getNetworkElement(Values.EXTERNAL_SERVICES_SERVER_ID), 50, 5);
		this.situateDevice((Device) this.getScenario().getNetworkElement(
						Values.DATA_CENTER_ROUTER_ID), 25, 25);
		this.situateDevice((Device) this.getScenario().getNetworkElement(
				Values.WEB_PROXY_ID), 75, 25);
		for (int i = 0; i < Values.NUMBER_OF_SYSADMINS; i++) {
			this.situateDevice((Device) this.getScenario()
					.getNetworkElement(Values.SA_COMPUTER_ID + i), (25*i)+5, 30);
		}

		for (String key: this.getScenario().getCurrentElements().keySet()) {
			NetworkElement e = this.getScenario().getCurrentElements().get(key);
			if(e instanceof Link){
				this.drawLink((Link)e);
			}
		}
		
//		this.situateDevice((Device) this.getScenario().getNetworkElement(Values.WEB_PROXY_ID), 25, 25);
//		this.situateDevice((Device) this.getScenario().getNetworkElement(Values.EXTERNAL_SERVICES_SERVER_ID), 35, 5);
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
		devicesPortrayal.setPortrayalForClass(RouterDNS.class,
				new Router2DPortrayal());
		devicesPortrayal.setPortrayalForClass(Server.class,
				new Server2DPortrayal());
		linksPortrayal.setPortrayalForClass(EthernetLink.class,
				new EthernetCable2DPortrayal());
	}

}
