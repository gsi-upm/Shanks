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
package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.field.continuous.Continuous3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink3DPortrayal;
import es.upm.dit.gsi.shanks.model.event.failiure.portrayal.Failure3DPortrayal;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTest;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class MyScenario3DPortrayal extends Scenario3DPortrayal {
    
    /**
     * @param scenario
     * @param width
     * @param height
     * @param length
     * @throws DuplicatedPortrayalIDException
     */
    public MyScenario3DPortrayal(Scenario scenario, int width, int height, int length) throws ShanksException {
        super(scenario, width, height, length);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal#placeElements()
     */
    @Override
    public void placeElements() {
        
        this.situateDevice((Device)this.getScenario().getNetworkElement("D1"), 100.0, 500.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D2"), 500.0, 500.0, 500.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D3"), 300.0, 300.0, 300.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D4"), 100.0, 100.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D5"), 500.0, 100.0, 500.0);
        
        this.drawLink((Link)this.getScenario().getNetworkElement("L1"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L2"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L3"));
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
     */
    @Override
    public void setupPortrayals() {
        
        //Global
        ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        ContinuousPortrayal3D failuresPortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID).get(MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice3DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink3DPortrayal());
        failuresPortrayal.setPortrayalForClass(FailureTest.class, new Failure3DPortrayal());
        
        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
        this.scaleDisplay(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID, 1.5);
      this.getDisplay(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID).setShowsAxes(true);
        
    }

    @Override
    public void addPortrayals() {
        Continuous3D failuresGrid = new Continuous3D(5, 100, 100, 100);
        ContinuousPortrayal3D failuresPortrayal = new ContinuousPortrayal3D();
        failuresPortrayal.setField(failuresGrid);
        try {
            this.addPortrayal(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID, MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        } catch (ShanksException e) {
            e.printStackTrace();
        }
    }

}
