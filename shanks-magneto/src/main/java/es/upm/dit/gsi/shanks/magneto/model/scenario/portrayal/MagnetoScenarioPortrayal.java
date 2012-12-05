package es.upm.dit.gsi.shanks.magneto.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Computer;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ServerGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal.ComputerPortrayal;
import es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal.GatewayPortrayal;
import es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal.ServerPortrayal;
import es.upm.dit.gsi.shanks.magneto.model.element.link.portrayal.MagnetoLinkPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;

public class MagnetoScenarioPortrayal extends Scenario2DPortrayal{

	public MagnetoScenarioPortrayal(Scenario scenario, int width, int height)
			throws ShanksException {
		super(scenario, width, height);
		
	}

	@Override
	public void addPortrayals() {
		
	}

	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("User Computer"), 15, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("User Gateway"), 30, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ISP Gateway"), 75, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Server Gateway"), 120, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Server"), 135, 30);
	
		this.drawLink((Link)this.getScenario().getNetworkElement("L1"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L2"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L3"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L4"));
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);

		devicePortrayal.setPortrayalForClass(Computer.class, new ComputerPortrayal());
		devicePortrayal.setPortrayalForClass(UserGateway.class, new GatewayPortrayal());
		devicePortrayal.setPortrayalForClass(ISPGateway.class, new GatewayPortrayal());
		devicePortrayal.setPortrayalForClass(ServerGateway.class, new GatewayPortrayal());
		devicePortrayal.setPortrayalForClass(Server.class, new ServerPortrayal());
		
		networkPortrayal.setPortrayalForAll(new MagnetoLinkPortrayal());
		
	}

}
