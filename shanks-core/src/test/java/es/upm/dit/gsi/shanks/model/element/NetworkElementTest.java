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
package es.upm.dit.gsi.shanks.model.element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

/**
 * @author a.carrera
 * 
 */
public class NetworkElementTest {
    
    Logger logger = Logger.getLogger(NetworkElementTest.class.getName());

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws ShanksException {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        try {
            lm.readConfiguration(new FileInputStream(configFile));
        } catch (SecurityException e) {
            throw new ShanksException(e); 
        } catch (FileNotFoundException e) {
            throw new ShanksException(e);
        } catch (IOException e) {
            throw new ShanksException(e);
        }

    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * @throws ShanksException
     */
    @Test
    public void createDevice() throws ShanksException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(false, d.isGateway());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.HIGH_TEMP_STATUS));
    }

    /**
     * @throws ShanksException
     */
    @Test
    public void createDeviceAndCheckInitialStatus()
            throws ShanksException {
        
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.HIGH_TEMP_STATUS));
        Assert.assertTrue(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertEquals(15.5, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());
    }

    /**
     * @throws ShanksException
     */
    @Test
    public void createDeviceAndCheckChangedStatus()
            throws ShanksException {
        
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.HIGH_TEMP_STATUS));
        Assert.assertEquals(15.5, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());

        d.updateStatusTo(MyDevice.OK_STATUS, false);
        d.updateStatusTo(MyDevice.NOK_STATUS, true);
        d.checkProperties();
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertTrue(d.getStatus().get(MyDevice.NOK_STATUS));
        Assert.assertEquals(90, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());

    }

    /**
     * @throws ShanksException
     */
    @Test
    public void createDeviceAndCheckChangedStatusAndChangedProperty()
            throws ShanksException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.HIGH_TEMP_STATUS));
        Assert.assertEquals(15.5, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());

        d.setCurrentStatus(MyDevice.OK_STATUS, false);
        d.setCurrentStatus(MyDevice.NOK_STATUS, true);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertTrue(d.getStatus().get(MyDevice.NOK_STATUS));
        Assert.assertEquals(90, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());

        d.changeProperty(MyDevice.TEMPERATURE_PROPERTY, 60);
        Assert.assertTrue(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(d.getStatus().get(MyDevice.NOK_STATUS));
        Assert.assertFalse(d.getStatus().get(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().get(MyDevice.HIGH_TEMP_STATUS));

    }

    /**
     * @throws ShanksException
     */
    @Test
    public void createGatewayDevice()
            throws ShanksException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, true, logger);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertEquals(true, d.isGateway());
    }

    /**
     * @throws ShanksException
     */
    @Test
    public void createDeviceChangeStatus()
            throws ShanksException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        try {
            d.setCurrentStatus(MyDevice.NOK_STATUS, false);
            d.setCurrentStatus(MyDevice.OK_STATUS, true);
        } catch (ShanksException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertTrue(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(d.getStatus().get(MyDevice.NOK_STATUS));
    }

    /**
     * @throws ShanksException
     */
    @Test
    public void createDeviceChangeToImpossibleStatus()
            throws ShanksException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        boolean catched = false;
        try {
            d.updateStatusTo("WrongStatus", true);
        } catch (ShanksException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    /**
     * @throws ShanksException
     */
    @Test
    public void PorpertiesNetworkElement()
            throws ShanksException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        d.addProperty("friendDevice", d2);
        Assert.assertEquals(d2, d.getProperty("friendDevice"));
    }

    /**
     * @throws ShanksException
     */
    @Test
    public void FullPorpertiesNetworkElement()
            throws ShanksException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("friend", d2);
        d.setProperties(properties);
        d.addProperty("Size", "20cm");
        Assert.assertEquals(d2, d.getProperty("friend"));
        Assert.assertEquals("20cm", d.getProperty("Size"));
    }

    @Test
    public void updateProperties() 
            throws ShanksException{
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Assert.assertEquals(15.5, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        d.updatePropertyTo(MyDevice.TEMPERATURE_PROPERTY, 70);
        Assert.assertEquals(70, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        d.checkStatus();
        Assert.assertTrue(d.getStatus().get(MyDevice.HIGH_TEMP_STATUS));
        d.updatePropertyTo(MyDevice.TEMPERATURE_PROPERTY, 150);
        d.checkStatus();
        Assert.assertTrue(d.getStatus().get(MyDevice.HIGH_TEMP_STATUS));
        Assert.assertTrue(!d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().get(MyDevice.NOK_STATUS));
        
    }
    
    /**
     * @throws ShanksException
     * @throws TooManyConnectionException
     */
    @Test
    public void connect2DevicesToLink()
            throws ShanksException,
            TooManyConnectionException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2, logger);
        List<Device> linkList = l1.getLinkedDevices();
        List<Link> d1list = d.getLinks();
        List<Link> d2list = d2.getLinks();

        d.connectToLink(l1);
        d2.connectToLink(l1);

        Assert.assertTrue(linkList.size() == 2);
        Assert.assertTrue(linkList.contains(d));
        Assert.assertTrue(linkList.contains(d2));
        Assert.assertTrue(d1list.size() == 1);
        Assert.assertTrue(d1list.contains(l1));
        Assert.assertTrue(d2list.size() == 1);
        Assert.assertTrue(d2list.contains(l1));
    }

    /**
     * @throws ShanksException
     * @throws TooManyConnectionException
     */
    @Test
    public void connect2DevicesToLinkInOneMethod()
            throws ShanksException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2, logger);
        
        List<Device> linkList = l1.getLinkedDevices();
        List<Link> d1list = d1.getLinks();
        List<Link> d2list = d2.getLinks();

        d1.connectToDeviceWithLink(d2, l1);

        Assert.assertTrue(linkList.size() == 2);
        Assert.assertTrue(linkList.contains(d1));
        Assert.assertTrue(linkList.contains(d2));
        Assert.assertTrue(d1list.size() == 1);
        Assert.assertTrue(d1list.contains(l1));
        Assert.assertTrue(d2list.size() == 1);
        Assert.assertTrue(d2list.contains(l1));
    }

    /**
     * @throws ShanksException
     * @throws TooManyConnectionException
     */
    @Test
    public void connect2DevicesToLinkInOneMethodInFullLink()
            throws ShanksException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2, logger);
        
        List<Device> linkList = l1.getLinkedDevices();

        try {
            d1.connectToDeviceWithLink(d2, l1);   
        } catch (TooManyConnectionException e) {
            Assert.assertTrue(linkList.size() == 0);
        }
    }
    
    /**
     * @throws ShanksException
     * @throws TooManyConnectionException
     */
    @Test
    public void disconnectDeviceFromLink()
            throws ShanksException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2, logger);
        
        List<Device> linkList = l1.getLinkedDevices();
        List<Link> d1list = d1.getLinks();
        List<Link> d2list = d2.getLinks();

        d1.connectToLink(l1);
        d2.connectToLink(l1);

        l1.disconnectDevice(d1);
        Assert.assertTrue(linkList.size() == 1);
        Assert.assertTrue(!linkList.contains(d1));
        Assert.assertTrue(linkList.contains(d2));
        Assert.assertTrue(d1list.size() == 0);
        Assert.assertTrue(!d1list.contains(l1));
        Assert.assertTrue(d2list.size() == 1);
        Assert.assertTrue(d2list.contains(l1));
    }

    /**
     * @throws ShanksException
     * @throws TooManyConnectionException
     */
    @Test
    public void disconnectLinkFromDevice()
            throws ShanksException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2, logger);
        
        List<Device> linkList = l1.getLinkedDevices();
        List<Link> d1list = d1.getLinks();
        List<Link> d2list = d2.getLinks();

        d1.connectToLink(l1);
        d2.connectToLink(l1);

        d1.disconnectFromLink(l1);
        Assert.assertTrue(linkList.size() == 1);
        Assert.assertTrue(!linkList.contains(d1));
        Assert.assertTrue(linkList.contains(d2));
        Assert.assertTrue(d1list.size() == 0);
        Assert.assertTrue(!d1list.contains(l1));
        Assert.assertTrue(d2list.size() == 1);
        Assert.assertTrue(d2list.contains(l1));
    }

    /**
     * @throws ShanksException
     * @throws TooManyConnectionException
     */
    @Test
    public void disconnectLinkFromDeviceAndViceversa()
            throws ShanksException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2, logger);
        
        List<Device> linkList = l1.getLinkedDevices();
        List<Link> d1list = d1.getLinks();
        List<Link> d2list = d2.getLinks();

        d1.connectToLink(l1);
        d2.connectToLink(l1);

        d1.disconnectFromLink(l1);
        l1.disconnectDevice(d2);
        Assert.assertTrue(linkList.size() == 0);
        Assert.assertTrue(!linkList.contains(d1));
        Assert.assertTrue(!linkList.contains(d2));
        Assert.assertTrue(d1list.size() == 0);
        Assert.assertTrue(!d1list.contains(l1));
        Assert.assertTrue(d2list.size() == 0);
        Assert.assertTrue(!d2list.contains(l1));
    }

    /**
     * @throws ShanksException
     * @throws TooManyConnectionException
     */
    @Test
    public void connectDeviceToFullLink()
            throws ShanksException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false, logger);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 1, logger);

        d1.connectToLink(l1);
        boolean catched = false;
        try {
            d2.connectToLink(l1);
        } catch (TooManyConnectionException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

}
