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
package es.upm.dit.gsi.shanks.model.failure.util.test;

import java.util.logging.Logger;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.event.failure.Failure;

public class TestDeviceFailure extends Failure {

    /**
     * Mark the complexity of the ScenarioFailure in order to test properly the
     * Failure class.
     * 
     * 0: Default: The failure change the property FOUNDATION_PROPERTY of
     * FailureTestDevice to NOK. 1:
     */
    private int configuration;
    public static final int DEFAULT_CONF = 0;

    public TestDeviceFailure(String id, Steppable generator, double prob, Logger logger) {
        super(id, generator, prob, logger);
        this.configuration = 0;
    }

    public TestDeviceFailure(String id, Steppable generator, double prob,
            int configuration, Logger logger) {
        super(id, generator, prob, logger);
        this.configuration = configuration;
    }

    @Override
    public void changeOtherFields()
            throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addPossibleAffected() {
        switch (this.configuration) {
        default:
            this.addPossibleAffectedElementProperty(FailureTestDevice.class,
                    FailureTestDevice.FUNDATIONS_PROPERTY,
                    FailureTestDefinitions.NOK);
        }

    }

    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isResolved() {
        // TODO Auto-generated method stub
        return false;
    }

}
