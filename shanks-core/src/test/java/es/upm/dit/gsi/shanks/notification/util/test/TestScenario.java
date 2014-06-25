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
package es.upm.dit.gsi.shanks.notification.util.test;

import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class TestScenario extends Scenario{

    public static final String TEST_STATE = "sunny";

    public TestScenario(String id, String initialState, Properties properties, Logger logger)
            throws ShanksException {
        super(id, initialState, properties, logger);
    }

    @Override
    public Scenario2DPortrayal createScenario2DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return null;
    }

    @Override
    public Scenario3DPortrayal createScenario3DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return null;
    }

    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(TestScenario.TEST_STATE);
    }

    @Override
    public void addNetworkElements()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException, DuplicatedIDException {
    }

    @Override
    public void addPossibleFailures() {
    }

    @Override
    public void addPossibleEvents() {
        try {
            
            TestDevice neperd = new TestDevice(TestDefinitions.DEVICE_ID+"NEPerE", null, false, this.getLogger());
            this.addPossibleEventsOfNE(TestPeriodicNetworkElementEvent.class, neperd);
            
            TestDevice neprod = new TestDevice(TestDefinitions.DEVICE_ID+"NEProE", null, false, this.getLogger());
            this.addPossibleEventsOfNE(TestProbabilisticNetworkElementEvent.class, neprod);
            
            this.addPossibleEventsOfScenario(TestProbabilisticScenarioEvent.class, this);
            this.addPossibleEventsOfScenario(TestPeriodicScenarioEvent.class, this);
            
        } catch (UnsupportedNetworkElementFieldException e1) {
            e1.printStackTrace();
        } catch (ShanksException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {
        return null;
    }
}
