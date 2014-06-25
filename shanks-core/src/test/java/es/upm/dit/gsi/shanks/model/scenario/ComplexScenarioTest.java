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
/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.scenario;

import java.util.Properties;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.test.MyComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;
import es.upm.dit.gsi.shanks.model.scenario.test.MySuperComplexScenario;

public class ComplexScenarioTest {

    static Logger logger = Logger.getLogger(ComplexScenarioTest.class.getName());
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createComplexScenario()
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MyComplexScenario("MyComplexScenario", MyComplexScenario.SUNNY, scenarioProperties, logger);
        Assert.assertEquals("MyComplexScenario", s.getID());
        Assert.assertEquals(MyComplexScenario.SUNNY, s.getCurrentStatus());
    }
    


    @Test
    public void createComplexScenarioAndGetScenarios()
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        ComplexScenario s = new MyComplexScenario("MyComplexScenario", MyComplexScenario.SUNNY, scenarioProperties, logger);
        Assert.assertEquals("MyComplexScenario", s.getID());
        Assert.assertEquals(MyComplexScenario.SUNNY, s.getCurrentStatus());
        Scenario s1 = s.getScenario("Scenario1");
        Scenario s2 = s.getScenario("Scenario2");
        Assert.assertEquals("Scenario1", s1.getID());
        Assert.assertEquals("Scenario2", s2.getID());
    }

    @Test
    public void createSuperComplexScenario()
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        Scenario s = new MySuperComplexScenario("MySuperComplexScenario", MySuperComplexScenario.SUNNY, scenarioProperties, logger);
        Assert.assertEquals("MySuperComplexScenario", s.getID());
        Assert.assertEquals(MySuperComplexScenario.SUNNY, s.getCurrentStatus());
    }
    


    @Test
    public void createSuperComplexScenarioAndGetScenarios()
            throws ShanksException {
        Properties scenarioProperties = new Properties();
        scenarioProperties.put(MyScenario.CLOUDY_PROB, "50");
        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
        ComplexScenario s = new MySuperComplexScenario("MySuperComplexScenario", MySuperComplexScenario.SUNNY, scenarioProperties, logger);
        Assert.assertEquals("MySuperComplexScenario", s.getID());
        Assert.assertEquals(MySuperComplexScenario.SUNNY, s.getCurrentStatus());
        Scenario s1 = s.getScenario("ComplexScenario1");
        Scenario s2 = s.getScenario("ComplexScenario2");
        Assert.assertEquals("ComplexScenario1", s1.getID());
        Assert.assertEquals("ComplexScenario2", s2.getID());
    }
}