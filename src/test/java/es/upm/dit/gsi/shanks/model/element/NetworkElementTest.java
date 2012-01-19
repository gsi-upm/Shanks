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
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

public class NetworkElementTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));
        
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createDevice() throws UnsupportedNetworkElementStatusException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(MyDevice.OK_STATUS, d.getCurrentStatus());
        Assert.assertEquals(false, d.isGateway());
    }

    @Test
    public void createDeviceAndCheckInitialStatus() throws UnsupportedNetworkElementStatusException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(MyDevice.OK_STATUS, d.getCurrentStatus());
        Assert.assertEquals(30, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());
    }

    @Test
    public void createDeviceAndCheckChangedStatus() throws UnsupportedNetworkElementStatusException {
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(MyDevice.OK_STATUS, d.getCurrentStatus());
        Assert.assertEquals(30, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());
        
        d.setCurrentStatus(MyDevice.NOK_STATUS);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(MyDevice.NOK_STATUS, d.getCurrentStatus());
        Assert.assertEquals(90, d.getProperty(MyDevice.TEMPERATURE_PROPERTY));
        Assert.assertEquals("Windows", d.getProperty(MyDevice.OS_PROPERTY));
        Assert.assertEquals(false, d.isGateway());
        
    }

    @Test
    public void createGatewayDevice()
            throws UnsupportedNetworkElementStatusException {
        Device d = new MyDevice("MyDevice", MyDevice.NOK_STATUS, true);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(MyDevice.NOK_STATUS, d.getCurrentStatus());
        Assert.assertEquals(true, d.isGateway());
    }

    @Test
    public void createDeviceChangeStatus()
            throws UnsupportedNetworkElementStatusException {
        Device d = new MyDevice("MyDevice", MyDevice.NOK_STATUS, true);
        try {
            d.setCurrentStatus(MyDevice.OK_STATUS);
        } catch (UnsupportedNetworkElementStatusException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertEquals(MyDevice.OK_STATUS, d.getCurrentStatus());
    }

    @Test
    public void createDeviceChangeToImpossibleStatus()
            throws UnsupportedNetworkElementStatusException {
        Device d = new MyDevice("MyDevice", MyDevice.NOK_STATUS, true);
        boolean catched = false;
        try {
            d.setCurrentStatus("WrongStatus");
        } catch (UnsupportedNetworkElementStatusException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void createDeviceWithImpossibleStatus()
            throws UnsupportedNetworkElementStatusException {
        boolean catched = false;
        try {
            new MyDevice("MyDevice", "WrongStatus", true);
        } catch (UnsupportedNetworkElementStatusException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void PorpertiesNetworkElement()
            throws UnsupportedNetworkElementStatusException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, true);
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, true);
        d1.addProperty("friendDevice", d2);
        Assert.assertEquals(d2, d1.getProperty("friendDevice"));
    }

    @Test
    public void FullPorpertiesNetworkElement()
            throws UnsupportedNetworkElementStatusException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, true);
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, true);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("friend", d2);
        d1.setProperties(properties);
        d1.addProperty("Size", "20cm");
        Assert.assertEquals(d2, d1.getProperty("friend"));
        Assert.assertEquals("20cm", d1.getProperty("Size"));
    }

    @Test
    public void connect2DevicesToLink()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, true);
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, true);
        Link l1 = new MyLink("L1", MyLink.OK_STATUS, 2);
        List<Device> linkList = l1.getLinkedDevices();
        List<Link> d1list = d1.getLinks();
        List<Link> d2list = d2.getLinks();

        d1.connectToLink(l1);
        d2.connectToLink(l1);

        Assert.assertTrue(linkList.size() == 2);
        Assert.assertTrue(linkList.contains(d1));
        Assert.assertTrue(linkList.contains(d2));
        Assert.assertTrue(d1list.size() == 1);
        Assert.assertTrue(d1list.contains(l1));
        Assert.assertTrue(d2list.size() == 1);
        Assert.assertTrue(d2list.contains(l1));
    }

    @Test
    public void disconnectDeviceFromLink()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, true);
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, true);
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

    @Test
    public void disconnectLinkFromDevice()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, true);
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, true);
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

    @Test
    public void disconnectLinkFromDeviceAndViceversa()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, true);
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, true);
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

    @Test
    public void connectDeviceToFullLink()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException {
        Device d1 = new MyDevice("D1", MyDevice.OK_STATUS, true);
        Device d2 = new MyDevice("D2", MyDevice.OK_STATUS, true);
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
