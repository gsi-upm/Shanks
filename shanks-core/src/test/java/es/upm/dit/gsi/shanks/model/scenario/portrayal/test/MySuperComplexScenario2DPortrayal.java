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

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink2DPortrayal;
import es.upm.dit.gsi.shanks.model.event.failiure.portrayal.Failure2DPortrayal;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTest;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class MySuperComplexScenario2DPortrayal extends
        ComplexScenario2DPortrayal {

    /**
     * @param scenario
     * @param width
     * @param height
     * @throws DuplicatedPortrayalIDException
     * @throws ShanksException
     */
    public MySuperComplexScenario2DPortrayal(Scenario scenario, int width,
            int height)
            throws ShanksException {
        super(scenario, width, height);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario2DPortrayal#placeScenarios()
     */
    @Override
    public void placeScenarios() throws DuplicatedPortrayalIDException, ShanksException {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("ComplexScenario1"), new Double2D(0,0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("ComplexScenario2"), new Double2D(0,160), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_180);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
     */
    @Override
    public void placeElements() {       
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateDevice((Device)cs.getNetworkElement("SED1"), 100, 80);
        this.drawLink((Link)cs.getNetworkElement("SEL1"));
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
     */
    @Override
    public void setupPortrayals() {
        ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID).get(MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice2DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink2DPortrayal());
        failuresPortrayal.setPortrayalForClass(FailureTest.class, new Failure2DPortrayal());
    }

    @Override
    public void addPortrayals() {
        SparseGrid2D failuresGrid = new SparseGrid2D(100, 100);
        SparseGridPortrayal2D failuresPortrayal = new SparseGridPortrayal2D();
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
