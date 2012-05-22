package es.upm.dit.gsi.shanks.model.element;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

/**
 * @author a.carrera
 * 
 */
public class NetworkElementTest {

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));

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
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void createDevice() throws UnsupportedNetworkElementFieldException {

        
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(false, d.isGateway());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.HIGH_TEMP_STATUS));
    
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void createDeviceAndCheckInitialStatus()
            throws UnsupportedNetworkElementFieldException {
        
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
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
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void createDeviceAndCheckChangedStatus()
            throws UnsupportedNetworkElementFieldException {
        
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
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
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void createDeviceAndCheckChangedStatusAndChangedProperty()
            throws UnsupportedNetworkElementFieldException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
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
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void createGatewayDevice()
            throws UnsupportedNetworkElementFieldException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, true);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.OK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.NOK_STATUS));
        Assert.assertTrue(d.getStatus().keySet().contains(MyDevice.UNKOWN_STATUS));
        Assert.assertEquals(true, d.isGateway());
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void createDeviceChangeStatus()
            throws UnsupportedNetworkElementFieldException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        try {
            d.setCurrentStatus(MyDevice.NOK_STATUS, false);
            d.setCurrentStatus(MyDevice.OK_STATUS, true);
        } catch (UnsupportedNetworkElementFieldException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertTrue(d.getStatus().get(MyDevice.OK_STATUS));
        Assert.assertFalse(d.getStatus().get(MyDevice.NOK_STATUS));
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void createDeviceChangeToImpossibleStatus()
            throws UnsupportedNetworkElementFieldException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        boolean catched = false;
        try {
            d.updateStatusTo("WrongStatus", true);
        } catch (UnsupportedNetworkElementFieldException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void PorpertiesNetworkElement()
            throws UnsupportedNetworkElementFieldException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        d.addProperty("friendDevice", d2);
        Assert.assertEquals(d2, d.getProperty("friendDevice"));
    }

    /**
     * @throws UnsupportedNetworkElementFieldException
     */
    @Test
    public void FullPorpertiesNetworkElement()
            throws UnsupportedNetworkElementFieldException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("friend", d2);
        d.setProperties(properties);
        d.addProperty("Size", "20cm");
        Assert.assertEquals(d2, d.getProperty("friend"));
        Assert.assertEquals("20cm", d.getProperty("Size"));
    }

    @Test
    public void updateProperties() 
            throws UnsupportedNetworkElementFieldException{
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
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
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     */
    @Test
    public void connect2DevicesToLink()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2);
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
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     */
    @Test
    public void connect2DevicesToLinkInOneMethod()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2);
        
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
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     */
    @Test
    public void connect2DevicesToLinkInOneMethodInFullLink()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2);
        
        List<Device> linkList = l1.getLinkedDevices();

        try {
            d1.connectToDeviceWithLink(d2, l1);   
        } catch (TooManyConnectionException e) {
            Assert.assertTrue(linkList.size() == 0);
        }
    }
    
    /**
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     */
    @Test
    public void disconnectDeviceFromLink()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2);
        
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
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     */
    @Test
    public void disconnectLinkFromDevice()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2);
        
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
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     */
    @Test
    public void disconnectLinkFromDeviceAndViceversa()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2);
        
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
     * @throws UnsupportedNetworkElementFieldException
     * @throws TooManyConnectionException
     */
    @Test
    public void connectDeviceToFullLink()
            throws UnsupportedNetworkElementFieldException,
            TooManyConnectionException {
        Device d1 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Device d2 = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 1);

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
