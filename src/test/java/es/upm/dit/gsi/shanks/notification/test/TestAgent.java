/**
 * 
 */
package es.upm.dit.gsi.shanks.notification.test;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;

/**
 * @author darofar
 * 
 */
public class TestAgent extends SimpleShanksAgent {

    private Location currentLocation;
    private NotifiedDouble speed = null;
    private NotifiedString agentState = null;
    private NotifiedBoolean hasBeenNearToSomething = null;
    
    public static final String TEST_AGENT_STATUS_OK = "OK";
    public static final String TEST_AGENT_STATUS_NOK = "NOK";

    public TestAgent(String id) {
        super(id);
    }
    public TestAgent(String id, double speed) {
        super(id);
        this.speed = new NotifiedDouble(TestDefinitions.SPEED_ID, this);
        this.agentState= new NotifiedString(TestDefinitions.AGENT_STATE_ID, this);
        this.hasBeenNearToSomething = new NotifiedBoolean(TestDefinitions.BOOLEAN_ID, this);
        this.setCurrentLocation(new Location());

        this.setSpeed(speed);
        this.hasBeenNearToSomething.set(false);
        this.setAgentState(TEST_AGENT_STATUS_OK);
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#checkMail()
     */
    @Override
    public void checkMail() {
        this.getInbox();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.agent.SimpleShanksAgent#executeReasoningCycle(es
     * .upm.dit.gsi.shanks.ShanksSimulation)
     */
    @Override
    public void executeReasoningCycle(ShanksSimulation simulation) {
    }

    /**
     * @param agentState the agentState to set
     */
    public void setAgentState(String agentState) {
        this.agentState.set(agentState);
    }


    /**
     * @param currentLocation the currentLocation to set
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }


    /**
     * @param speed the speed to set
     */
    public void setSpeed(Double speed) {
        this.speed.set(speed);
    }


    /**
     * @return the agentState
     */
    public String getAgentState() {
        return agentState.get();
    }


    /**
     * @return the currentLocation
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }


    /**
     * @return the speed
     */
    public Double getSpeed() {
        return this.speed.get();
    }

    public void somethingChange() {
        this.hasBeenNearToSomething.set(!this.hasBeenNearToSomething());
    }

    public boolean hasBeenNearToSomething() {
        return this.hasBeenNearToSomething.get();
    }


    private static final long serialVersionUID = -7213543937419402679L;
}
