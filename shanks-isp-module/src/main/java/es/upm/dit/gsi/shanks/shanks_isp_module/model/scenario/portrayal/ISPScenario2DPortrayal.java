package es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.portrayal.Smartphone2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.portrayal.Failure2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Router2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Server2DPortrayal;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.EnterpriseScenario;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.Values;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.EthernetLink2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Printer2DPortrayal;

public class ISPScenario2DPortrayal extends ComplexScenario2DPortrayal {

	public static final String FAILURE_DISPLAY_ID = "Current Failures";
	public static final String FAILURE_PORTRAYAL_ID = "Failures";

	public ISPScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		super(scenario, width, height);
	}

	@Override
	public void addPortrayals() {
		SparseGrid2D failuresGrid = new SparseGrid2D(100, 100);
		SparseGridPortrayal2D failuresPortrayal = new SparseGridPortrayal2D();
		failuresPortrayal.setField(failuresGrid);
		try {
			this.addPortrayal(FAILURE_DISPLAY_ID, FAILURE_PORTRAYAL_ID,
					failuresPortrayal);
		} catch (DuplicatedPortrayalIDException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void placeElements() {
		ComplexScenario cs = (ComplexScenario) this.getScenario();
		this.situateDevice((Device) cs.getNetworkElement(Values.ISP_GATEWAY_ID), 100, 100);
		for (String key : cs.getCurrentElements().keySet()) {
			NetworkElement ne = cs.getCurrentElements().get(key);
			if (ne instanceof Link) {
				this.drawLink((Link) ne);
			}
		}
		try {
			ScenarioID scenarioID = new ScenarioID("Network's Attack Simulation","", false);
			this.situateDevice(scenarioID, 2, 2);
		} catch (UnsupportedNetworkElementStatusException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this
				.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
				.get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		NetworkPortrayal2D networkPortrayalLink = (NetworkPortrayal2D) this
				.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID)
				.get(ScenarioPortrayal.LINKS_PORTRAYAL);
		SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this
				.getPortrayals().get(FAILURE_DISPLAY_ID)
				.get(FAILURE_PORTRAYAL_ID);
		devicesPortrayal.setPortrayalForClass(Computer.class,
				new Computer2DPortrayal());
		devicesPortrayal.setPortrayalForClass(RouterDNS.class,
				new Router2DPortrayal());
		devicesPortrayal.setPortrayalForClass(WirelessDevice.class,
				new Smartphone2DPortrayal());
		devicesPortrayal.setPortrayalForClass(Server.class,
						new Server2DPortrayal());
		devicesPortrayal.setPortrayalForClass(Printer.class,
				new Printer2DPortrayal());
		devicesPortrayal.setPortrayalForClass(ScenarioID.class, new ScenarioIDPortrayal());
		
//		devicesPortrayal.setPortrayalForClass(DCRouter.class,
//				new Router2DPortrayal());
//		devicesPortrayal.setPortrayalForClass(WifiRouterADSL.class,
//				new WifiRouterADSL2DPortrayal());
//		devicesPortrayal.setPortrayalForClass(LANRouter.class,
//				new Router2DPortrayal());
//		devicesPortrayal.setPortrayalForClass(ISPGateway.class,
//				new ISPGateway2DPortrayal());

		networkPortrayalLink.setPortrayalForAll(new EthernetLink2DPortrayal());
		failuresPortrayal.setPortrayalForAll(new Failure2DPortrayal());
	}

	@Override
	public void placeScenarios() throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		ComplexScenario cs = (ComplexScenario) this.getScenario();
		int positionX = 120;
		int positionY = 20;
		int hans = 0;
		for (Scenario subScenario : cs.getScenarios()) {
			if(subScenario instanceof EnterpriseScenario){
				this.situateScenario(subScenario, new Double2D(0,
						0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0,
						ShanksMath.ANGLE_0);
			} else {
				this.situateScenario(subScenario, new Double2D(positionX,
						positionY), ShanksMath.ANGLE_180, ShanksMath.ANGLE_0,
						ShanksMath.ANGLE_0);
				hans++;
				if(hans>4){
					hans = 0;
					positionX = positionX+20;
					positionY = 20;
				} else {
					positionY = positionY + Values.deltaY;
				}
			}
			
		}
	}

	private class ScenarioIDPortrayal extends Device2DPortrayal implements
			Portrayal {
		private static final long serialVersionUID = 2668583147001204200L;

		@Override
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

			Device device = (Device) object;
			graphics.setColor(Color.black);
			graphics.drawString(device.getID(), 50, 20);
		}
	}

	private class ScenarioID extends Device {
		public ScenarioID(String id, String initialState, boolean isGateway)
				throws UnsupportedNetworkElementStatusException {
			super(id, initialState, isGateway);
		}

		@Override
		public void fillIntialProperties() {
		}

		@Override
		public void checkProperties()
				throws UnsupportedNetworkElementStatusException {
		}

		@Override
		public void checkStatus()
				throws UnsupportedNetworkElementStatusException {
		}

		@Override
		public void setPossibleStates() {
		}
	}
}
