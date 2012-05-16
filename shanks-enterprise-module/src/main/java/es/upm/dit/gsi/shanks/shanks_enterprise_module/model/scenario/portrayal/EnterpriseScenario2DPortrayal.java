package es.upm.dit.gsi.shanks.shanks_enterprise_module.model.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.datacenter.model.scenario.DataCenterScenario;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
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
import es.upm.dit.gsi.shanks.networkattacks.util.failures.WireBroken;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Router2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Server2DPortrayal;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.Values;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.element.CompanyRouter;
import es.upm.dit.gsi.shanks.shanks_enterprise_module.model.element.portrayal.CompanyRouter2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.LANRouter;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.EthernetLink2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Printer2DPortrayal;

public class EnterpriseScenario2DPortrayal extends ComplexScenario2DPortrayal{

	public static final String FAILURE_DISPLAY_ID = "Current Failures";
    public static final String FAILURE_PORTRAYAL_ID = "Failures";
	
	public EnterpriseScenario2DPortrayal(Scenario scenario, int width,
			int height) throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		super(scenario, width, height);
	}

	@Override
	public void placeScenarios() throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		ComplexScenario cs = (ComplexScenario) this.getScenario();
		int positionX = 0;
		int positionY = 40;
		int workerrooms = 0;
		for (Scenario subScenario : cs.getScenarios()) {
			if(subScenario instanceof DataCenterScenario){
				this.situateScenario(subScenario, new Double2D(0, 0), 
						ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
			} else {
				this.situateScenario(subScenario, new Double2D(
						positionX, positionY), ShanksMath.ANGLE_0, 
						ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
				workerrooms++;
				if(workerrooms>3){
					workerrooms = 0;
					positionX = 0;
					positionY = positionY+20;
				} else {
					positionX = positionX+20;
				}
				
			}
		
		}        
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
        this.situateDevice((Device)cs.getNetworkElement(Values.ENTERPRISE_GATEWAY_ID), 60, 35);
		for (String key : cs.getCurrentElements().keySet()) {
			NetworkElement ne = cs.getCurrentElements().get(key);
			if (ne instanceof Link) {
				this.drawLink((Link) ne);
			}
		}     
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(FAILURE_DISPLAY_ID).get(FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(CompanyRouter.class, new CompanyRouter2DPortrayal());
        devicePortrayal.setPortrayalForClass(Computer.class, new es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Printer.class, new Printer2DPortrayal());
        devicePortrayal.setPortrayalForClass(RouterDNS.class, new Router2DPortrayal());
        devicePortrayal.setPortrayalForClass(LANRouter.class, new Router2DPortrayal());
        devicePortrayal.setPortrayalForClass(Server.class, new Server2DPortrayal());
        devicePortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(es.upm.dit.gsi.shanks.datacenter.model.element.device.DCRouter.class, new Router2DPortrayal());
        networkPortrayal.setPortrayalForAll(new EthernetLink2DPortrayal());
        failuresPortrayal.setPortrayalForClass(WireBroken.class, new Failure2DPortrayal());
	}

}
