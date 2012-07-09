package es.upm.dit.gsi.shanks.tutorial.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Router;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.portrayal.Router2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.element.links.portrayal.EthernetLink2DPortrayal;

/**
 * 
 * @author Daniel Lara
 * 
 * This class represents graphically the scenario
 *
 */

public class LANScenario2DPortrayal extends Scenario2DPortrayal{

	public LANScenario2DPortrayal(Scenario scenario, int width, int height)
			throws ShanksException {
		super(scenario, width, height);
		
	}

	@Override
	public void addPortrayals() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeElements() {
		//Be carefull with the names, if you write wrong SHANKS will launch an exception
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router"), 50, 50);
		
		this.situateDevice((Device)this.getScenario().getNetworkElement("Computer 1"), 30, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Computer 2"), 70, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Computer 3"), 20, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Computer 4"), 30, 70);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Computer 5"), 70, 70);

		this.drawLink((Link)this.getScenario().getNetworkElement("L1"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L2"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L3"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L4"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L5"));
		
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Router.class, new Router2DPortrayal());
        networkPortrayal.setPortrayalForAll(new EthernetLink2DPortrayal());
	}

}
