package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.display3d.Display3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MyComplexScenario3DPortrayal extends Scenario3DPortrayal {

    private boolean scaled;

    public MyComplexScenario3DPortrayal(Scenario scenario, long width,
            long height, long length) throws DuplicatedPortrayalID {
        super(scenario, width, height, length);
        this.scaled = false; 
    }

    @Override
    public void placeElements() {
        
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        
        Scenario s1 = cs.getScenario("Scenario1");
        
        this.situateDevice((Device)s1.getNetworkElement("D1"), 100.0, 500.0, 100.0);
        this.situateDevice((Device)s1.getNetworkElement("D2"), 500.0, 500.0, 500.0);
        this.situateDevice((Device)s1.getNetworkElement("D3"), 300.0, 300.0, 300.0);
        this.situateDevice((Device)s1.getNetworkElement("D4"), 100.0, 100.0, 100.0);
        this.situateDevice((Device)s1.getNetworkElement("D5"), 500.0, 100.0, 500.0);
        
        this.drawLink((Link)s1.getNetworkElement("L1"));
        this.drawLink((Link)s1.getNetworkElement("L2"));
        this.drawLink((Link)s1.getNetworkElement("L3"));
        
        Scenario s2 = cs.getScenario("Scenario2");
        this.situateDevice((Device)s2.getNetworkElement("D1"), 700, 500, 200);
        this.situateDevice((Device)s2.getNetworkElement("D2"), 700, 300, 200);
        this.situateDevice((Device)s2.getNetworkElement("D3"), 600, 100, 200);
        this.situateDevice((Device)s2.getNetworkElement("D4"), 800, 500, 200);
        this.situateDevice((Device)s2.getNetworkElement("D5"), 600, 300, 200);
        
        this.drawLink((Link)s2.getNetworkElement("L1"));
        this.drawLink((Link)s2.getNetworkElement("L2"));
        this.drawLink((Link)s2.getNetworkElement("L3"));
        
        this.drawLink((Link)cs.getNetworkElement("EL1"));
    }

    @Override
    public void setupPortrayals() {
        
        //Global
        ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice3DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink3DPortrayal());
        
        this.rescaleMainDisplay(1.5);
//        Display3D mainDisplay = this.getDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID);
//        MouseListener[] mls = mainDisplay.getMouseListeners();
//        for (MouseListener l : mls) {
//            mainDisplay.removeMouseListener(l);
//        }
//        MouseMotionListener[] mmls = mainDisplay.getMouseMotionListeners();
//        for (MouseMotionListener l : mmls) {
//            mainDisplay.removeMouseMotionListener(l);
//        }
//        MouseWheelListener[] mwls = mainDisplay.getMouseWheelListeners();
//        for (MouseWheelListener l : mwls) {
//            mainDisplay.removeMouseWheelListener(l);
//        }
        
//        ComplexScenario cs = (ComplexScenario) this.getScenario();
//        //Scenario1
//        ScenarioPortrayal s1p = new MyScenario3DPortrayal(cs.getScenario("Scenario1"), 100, 100, 100);
//        HashMap<String,Portrayal> p1 = s1p.getPortrayals();
//        for (String s : p1.keySet()) {
//            Portrayal p = p1.get(s);
//            this.addPortrayal("Scenario1-"+s, p);
//        }

        //TODO echarle un ojo a esto para que pueda pintarse cada link como se quiera

    }

    private void rescaleMainDisplay(double d) {
        if (!scaled) {
            Display3D mainDisplay = this.getDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID);
            mainDisplay.scale(d);
            this.scaled = true;
        }
    }

}
