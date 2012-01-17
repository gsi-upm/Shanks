package es.upm.dit.gsi.shanks.model.scenario.test;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public class MyScenario extends Scenario {
    
    public static final String CLOUDY = "CLOUDY";
    public static final String SUNNY = "SUNNY";

    public MyScenario(String id, String initialState) throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException {
        super(id, initialState);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
     */
    @Override
    public void addNetworkElements() throws UnsupportedNetworkElementStatusException, TooManyConnectionException {
        Device d1 = new MyDevice("D1", MyDevice.OK, false);
        Device d2 = new MyDevice("D2", MyDevice.OK, false);
        Device d3 = new MyDevice("D3", MyDevice.OK, false);
        Device d4 = new MyDevice("D4", MyDevice.OK, false);
        Device d5 = new MyDevice("D5", MyDevice.OK, true);
        Link l1 = new MyLink("L1", MyLink.OK, 3);
        Link l2 = new MyLink("L2", MyLink.OK, 2);
        Link l3 = new MyLink("L1", MyLink.OK, 2);
        
        d1.connectToLink(l1);
        d2.connectToLink(l1);
        d3.connectToLink(l1);
        
        l2.connectDevices(d1, d4);
        
        l3.connectDevices(d3, d5);

    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
     */
    @Override
    public void addPossibleFailures() {
        this.addPossibleFailure(MyFailure.class);

    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
     */
    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(MyScenario.CLOUDY);
        this.addPossibleStatus(MyScenario.SUNNY);        
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#getPenaltiesInStatus(java.lang.String)
     */
    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {
        
        if (status.equals(MyScenario.CLOUDY)) {
            return this.getCloudyPenalties();
        } else if (status.equals(MyScenario.SUNNY)) {
            return this.getSunnyPenalties();
        } else {
            throw new UnsupportedScenarioStatusException();
        }
                
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
        
        penalties.put(MyFailure.class, 1.0);
        
        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getCloudyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();
        
        penalties.put(MyFailure.class, 3.0);
        
        return penalties;
    }

}
