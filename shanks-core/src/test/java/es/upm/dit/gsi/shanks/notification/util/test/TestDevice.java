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

import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;

public class TestDevice extends Device {

    public TestDevice(String id, String initialState, boolean isGateway, Logger logger)
            throws ShanksException {
        super(id, initialState, isGateway, logger);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void fillIntialProperties() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void checkProperties()
            throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void checkStatus() throws UnsupportedNetworkElementFieldException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPossibleStates() {
        // TODO Auto-generated method stub
        
    }

}
