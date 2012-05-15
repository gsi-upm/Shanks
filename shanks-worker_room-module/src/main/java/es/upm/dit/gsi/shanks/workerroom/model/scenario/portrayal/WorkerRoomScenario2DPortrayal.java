package es.upm.dit.gsi.shanks.workerroom.model.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Computer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals.Router2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.LANRouter;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.EthernetLink2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Printer2DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.failure.portrayal.FailuresPortrayal;

public class WorkerRoomScenario2DPortrayal extends Scenario2DPortrayal{

	public static final String FAILURE_DISPLAY_ID = "Current Failures";
    public static final String FAILURE_PORTRAYAL_ID = "Failures";
	
	public WorkerRoomScenario2DPortrayal(Scenario scenario, int width,
			int height) throws DuplicatedPortrayalIDException {
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
		int positionY=20;
		for(String key: this.getScenario().getCurrentElements().keySet()){
			NetworkElement ne = this.getScenario().getCurrentElements().get(key);
			if(ne instanceof Computer) {
				this.situateDevice((Device) ne, 10, positionY);
				positionY = positionY+10;
			} else if(ne instanceof Printer) {
				this.situateDevice((Device) ne, 50, 20);
			} else if(ne instanceof LANRouter){
				this.situateDevice((Device) ne, 50, 50);
			} else if(ne instanceof Link){
				this.drawLink((Link) ne);
			}
		}
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(FAILURE_DISPLAY_ID).get(FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Printer.class, new Printer2DPortrayal());
        devicePortrayal.setPortrayalForClass(LANRouter.class, new Router2DPortrayal());
        networkPortrayal.setPortrayalForAll(new EthernetLink2DPortrayal());
        failuresPortrayal.setPortrayalForAll(new FailuresPortrayal());
	}

}
