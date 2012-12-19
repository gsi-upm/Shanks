package es.upm.dit.gsi.shanks.magneto.model.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgent;
import es.upm.dit.gsi.shanks.magneto.agent.FixAgentBecario;
import es.upm.dit.gsi.shanks.magneto.agent.portrayal.FixAgent2DPortrayal;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Computer;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ServerGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal.ComputerPortrayal;
import es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal.GatewayPortrayal;
import es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal.ServerPortrayal;
import es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal.UserGatewayPortrayal;
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
		this.situateDevice((Device)this.getScenario().getNetworkElement("User Computer"), 15, 140);
		this.situateDevice((Device)this.getScenario().getNetworkElement("User Gateway"), 15, 110);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ISP Gateway"), 60, 80);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Server Gateway"), 140, 110);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Server"), 140, 140);
		this.situateDevice((Device)this.getScenario().getNetworkElement("OSS"), 110, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("OSS Gateway"), 80, 40);

		
		
		this.drawLink((Link)this.getScenario().getNetworkElement("L1"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L2"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L3"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L4"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L5"));
		this.drawLink((Link)this.getScenario().getNetworkElement("L6"));

	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);

		devicePortrayal.setPortrayalForClass(Computer.class, new ComputerPortrayal());
		devicePortrayal.setPortrayalForClass(UserGateway.class, new UserGatewayPortrayal());
		devicePortrayal.setPortrayalForClass(ISPGateway.class, new GatewayPortrayal());
		devicePortrayal.setPortrayalForClass(ServerGateway.class, new UserGatewayPortrayal());
		devicePortrayal.setPortrayalForClass(Server.class, new ServerPortrayal());
		devicePortrayal.setPortrayalForClass(FixAgent.class, new FixAgent2DPortrayal());
		devicePortrayal.setPortrayalForClass(FixAgentBecario.class, new FixAgent2DPortrayal());

		
		networkPortrayal.setPortrayalForAll(new MagnetoLinkPortrayal());
		
	}

}
