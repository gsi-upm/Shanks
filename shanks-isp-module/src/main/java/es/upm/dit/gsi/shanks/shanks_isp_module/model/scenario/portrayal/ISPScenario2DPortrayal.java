package es.upm.dit.gsi.shanks.shanks_isp_module.model.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.DCRouter;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WifiRouterADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.WirelessDevice;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.portrayal.Smartphone2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.element.device.portrayal.WifiRouterADSL2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
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
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Router2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Server2DPortrayal;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.element.portrayal.ISPGateway2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.EthernetLink2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Printer2DPortrayal;

public class ISPScenario2DPortrayal extends ComplexScenario2DPortrayal{

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
            this.addPortrayal(FAILURE_DISPLAY_ID, FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        }
	}

	@Override
	public void placeElements() {
		ComplexScenario cs = (ComplexScenario) this.getScenario();       
        this.situateDevice((Device)cs.getNetworkElement("ISP Gateway"), 150, 55);        
        this.drawLink((Link)cs.getNetworkElement("LINK1"));
        this.drawLink((Link)cs.getNetworkElement("LINK2"));
        this.drawLink((Link)cs.getNetworkElement("LINK3"));
        this.drawLink((Link)cs.getNetworkElement("LINK4"));
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);  
		NetworkPortrayal2D networkPortrayalLink = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
		SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(FAILURE_DISPLAY_ID).get(FAILURE_PORTRAYAL_ID);
        
		devicesPortrayal.setPortrayalForClass(Computer.class,
				new Computer2DPortrayal());
		devicesPortrayal.setPortrayalForClass(DCRouter.class,
				new Router2DPortrayal());
		devicesPortrayal.setPortrayalForClass(es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server.class,
				new Server2DPortrayal());
		devicesPortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicesPortrayal.setPortrayalForClass(WifiRouterADSL.class, new WifiRouterADSL2DPortrayal());
        devicesPortrayal.setPortrayalForClass(WirelessDevice.class, new Smartphone2DPortrayal());
        devicesPortrayal.setPortrayalForClass(es.upm.dit.gsi.shanks.workerroom.model.element.device.Computer.class, new es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Computer2DPortrayal());
        devicesPortrayal.setPortrayalForClass(Printer.class, new Printer2DPortrayal());
        devicesPortrayal.setPortrayalForClass(es.upm.dit.gsi.shanks.workerroom.model.element.device.LANRouter.class, new es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Router2DPortrayal());
        devicesPortrayal.setPortrayalForClass(ISPGateway.class, new ISPGateway2DPortrayal());
        
		networkPortrayalLink.setPortrayalForAll(new EthernetLink2DPortrayal());
        failuresPortrayal.setPortrayalForAll(new Failure2DPortrayal());
	}

	@Override
	public void placeScenarios() throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("Enterprise"), new Double2D(0,-5), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("Hacker HAN 1"), new Double2D(170,-5), ShanksMath.ANGLE_180, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("Hacker HAN 2"), new Double2D(170,125), ShanksMath.ANGLE_180, ShanksMath.ANGLE_0, ShanksMath.ANGLE_180);
        this.situateScenario(cs.getScenario("Hacker HAN 3"), new Double2D(230,55), ShanksMath.ANGLE_0, ShanksMath.ANGLE_180, ShanksMath.ANGLE_180);
	}

}
