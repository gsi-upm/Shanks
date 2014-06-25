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
package es.upm.dit.gsi.shanks.model.test.steppable;

import java.util.Set;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous3D;
import sim.field.grid.SparseGrid2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;

/**
 * @author a.carrera
 *
 */
public class FailuresGUI implements Steppable {

    @Override
    public void step(SimState sim) {
        ShanksSimulation simulation = (ShanksSimulation) sim;
        Set<Failure> failures = simulation.getScenario()
                .getCurrentFailures();

        if (simulation.getScenario()
                .getProperty(Scenario.SIMULATION_GUI)
                .equals(Scenario.SIMULATION_2D)) {
            try {
                SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) simulation
                        .getScenarioPortrayal()
                        .getPortrayals()
                        .get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID)
                        .get(MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID);
                SparseGrid2D grid = (SparseGrid2D) failuresPortrayal
                        .getField();
                grid.clear();
                int pos = 20;
                for (Failure f : failures) {
                    grid.setObjectLocation(f, 10, pos);
                    pos += 10;
                }
            } catch (DuplicatedPortrayalIDException e) {
                e.printStackTrace();
            } catch (ScenarioNotFoundException e) {
                e.printStackTrace();
            } catch (ShanksException e) {
                e.printStackTrace();
            }
        } else if (simulation.getScenario()
                .getProperty(Scenario.SIMULATION_GUI)
                .equals(Scenario.SIMULATION_3D)) {
            try {
                ContinuousPortrayal3D failuresPortrayal = (ContinuousPortrayal3D) simulation
                        .getScenarioPortrayal()
                        .getPortrayals()
                        .get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID)
                        .get(MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID);
                Continuous3D grid = (Continuous3D) failuresPortrayal
                        .getField();
                grid.clear();
                int pos = 20;
                for (Failure f : failures) {
                    grid.setObjectLocation(f, new Double3D(-110,
                            100 - pos, 0));
                    pos += 10;
                }
            } catch (DuplicatedPortrayalIDException e) {
                e.printStackTrace();
            } catch (ScenarioNotFoundException e) {
                e.printStackTrace();
            } catch (ShanksException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static final long serialVersionUID = -929835696282793943L;
}
