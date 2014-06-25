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

public class FailureTestDefinitions {
    
    //Failures
    public static final String FAILURE_ID = "TestFailure";
    public static final Double FAILURE_PROB = 0.5;
    public static final Double FAILURE_HIGH_PROB = 0.9;
    public static final Double FAILURE_LOW_PROB = 0.1;
    
    //Devices
    public static final String DEVICE_ID = "FailureTestDevice#";
    
    //Scenario
    public static final String SCENARIO_ID = "FailureTestScenario#";

    // Generic values. 
    public static final String NOK = "NOK";
    public static final Object OK = "OK";
    public static final long IN_ITERATIONS = 500;
}
