/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/**
 * 
 */
package es.upm.dit.gsi.shanks.notification.util.test;

import java.util.logging.Logger;

import jason.asSemantics.Message;
import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

/**
 * @author darofar
 * 
 */
public class TestAgent extends TestAction implements ShanksAgent {

    private Location currentLocation;
    private NotifiedDouble speed = null;
    private NotifiedString agentState = null;
    private NotifiedBoolean hasBeenNearToSomething = null;
    
    public static final String TEST_AGENT_STATUS_OK = "OK";
    public static final String TEST_AGENT_STATUS_NOK = "NOK";

    public TestAgent(String id, double speed, Steppable launcher) {
        super(id, launcher);
        this.speed = new NotifiedDouble(TestDefinitions.SPEED_ID, this);
        this.agentState= new NotifiedString(TestDefinitions.AGENT_STATE_ID, this);
        this.hasBeenNearToSomething = new NotifiedBoolean(TestDefinitions.BOOLEAN_ID, this);
        this.setCurrentLocation(new Location());

        this.setSpeed(speed);
        this.hasBeenNearToSomething.set(false);
        this.setAgentState(TEST_AGENT_STATUS_OK);
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

    @Override
    public void step(SimState arg0) {
        try {
            this.launchEvent();
        } catch (UnsupportedNetworkElementFieldException e) {
            e.printStackTrace();
        } catch (UnsupportedScenarioStatusException e) {
            e.printStackTrace();
        } catch (ShanksException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() {
    }
    
    @Override
    public void putMessegaInInbox(Message message) {
    }
    @Override
    public void sendMsg(Message message) {
    }
    @Override
    public String getID() {
        return null;
    }
    @Override
    public void checkMail() {
    }
    private static final long serialVersionUID = -7213543937419402679L;

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return Logger.getLogger(TestAgent.class.getName());
    }
}
