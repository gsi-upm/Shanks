package es.upm.dit.gsi.shanks.model.element;

import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

public class NetworkElementTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createDevice() {
        Device d = new MyDevice("MyDevice", MyDevice.OK, false);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(MyDevice.OK, d.getCurrentStatus());
        Assert.assertEquals(false, d.isGateway());
    }

    @Test
    public void createGatewayDevice() {
        Device d = new MyDevice("MyDevice", MyDevice.NOK, true);
        Assert.assertEquals("MyDevice", d.getID());
        Assert.assertEquals(MyDevice.NOK, d.getCurrentStatus());
        Assert.assertEquals(true, d.isGateway());
    }

    @Test
    public void PorpertiesNetworkElement() {
        Device d1 = new MyDevice("D1", MyDevice.OK, true);
        Device d2 = new MyDevice("D2", MyDevice.OK, true);
        d1.addProperty("friendDevice", d2);
        Assert.assertEquals(d2, d1.getProperty("friendDevice"));
    }

    @Test
    public void FullPorpertiesNetworkElement() {
        Device d1 = new MyDevice("D1", MyDevice.OK, true);
        Device d2 = new MyDevice("D2", MyDevice.OK, true);
        HashMap<String, Object> properties = new HashMap<String,Object>();
        properties.put("friend", d2);
        d1.setProperties(properties);
        d1.addProperty("Size", "20cm");
        Assert.assertEquals(d2, d1.getProperty("friend"));
        Assert.assertEquals("20cm", d1.getProperty("Size"));
    }

    @Test
    public void connect2DevicesToLink() {
        Device d1 = new MyDevice("D1", MyDevice.OK, true);
        Device d2 = new MyDevice("D2", MyDevice.OK, true);
        Link l1 = new MyLink("L1", MyLink.OK, 2);
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
    public void disconnectDeviceFromLink() {
        Device d1 = new MyDevice("D1", MyDevice.OK, true);
        Device d2 = new MyDevice("D2", MyDevice.OK, true);
        Link l1 = new MyLink("L1", MyLink.OK, 2);
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
    public void disconnectLinkFromDevice() {
        Device d1 = new MyDevice("D1", MyDevice.OK, true);
        Device d2 = new MyDevice("D2", MyDevice.OK, true);
        Link l1 = new MyLink("L1", MyLink.OK, 2);
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
    public void disconnectLinkFromDeviceAndViceversa() {
        Device d1 = new MyDevice("D1", MyDevice.OK, true);
        Device d2 = new MyDevice("D2", MyDevice.OK, true);
        Link l1 = new MyLink("L1", MyLink.OK, 2);
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

}
