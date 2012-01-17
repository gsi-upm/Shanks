package es.upm.dit.gsi.shanks.model.failure;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.exception.UnsupportedElementInFailureException;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;

public class FailureTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createFailureAndNoActiveIt() {
        Failure f = new MyFailure("MyFailure", 0.01);
        Device d = new MyDevice("MyDevice", MyDevice.OK, false);
        try {
            f.addAffectedElement(d, MyDevice.NOK);
            Assert.assertEquals("MyFailure", f.getID());
            Assert.assertEquals(MyDevice.OK, d.getCurrentStatus());
            Assert.assertEquals(false, f.isActive());
            Assert.assertTrue(f.getAffectedElements().size()==1);
            Assert.assertTrue(f.getCurrentAffectedElements().size()==0);
        } catch (UnsupportedElementInFailureException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void createFailureAndActiveIt() {
        Failure f = new MyFailure("MyFailure", 0.01);
        Device d = new MyDevice("MyDevice", MyDevice.OK, false);
        try {
            f.addAffectedElement(d, MyDevice.NOK);
            f.activateFailure();
            Assert.assertEquals("MyFailure", f.getID());
            Assert.assertEquals(MyDevice.NOK, d.getCurrentStatus());
            Assert.assertEquals(true, f.isActive());
            Assert.assertTrue(f.getAffectedElements().size()==1);
            Assert.assertTrue(f.getCurrentAffectedElements().size()==1);
        } catch (UnsupportedElementInFailureException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void createFailureAndDeactiveIt() {
        Failure f = new MyFailure("MyFailure", 0.01);
        Device d = new MyDevice("MyDevice", MyDevice.OK, false);
        try {
            f.addAffectedElement(d, MyDevice.NOK);
            f.activateFailure();
            f.deactivateFailure();
            Assert.assertEquals("MyFailure", f.getID());
            Assert.assertEquals(MyDevice.OK, d.getCurrentStatus());
            Assert.assertEquals(false, f.isActive());
            Assert.assertTrue(f.getAffectedElements().size()==1);
            Assert.assertTrue(f.getCurrentAffectedElements().size()==0);
        } catch (UnsupportedElementInFailureException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void createFailureAndAddNonPossibleElement() {
        Failure f = new MyFailure("MyFailure", 0.01);
        Link l = new MyLink("L1", MyLink.OK, 3);
        f.removePossibleAffectedElements(MyLink.class);
        boolean catched = false;
        try {
            f.addAffectedElement(l, MyLink.BROKEN);
        } catch (UnsupportedElementInFailureException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void createFailureAndResolveIt() {
        Failure f = new MyFailure("MyFailure", 0.01);
        Link l = new MyLink("L1", MyLink.OK, 3);
        try {
            f.addAffectedElement(l, MyLink.BROKEN);
            f.activateFailure();
            l.setCurrentStatus(MyLink.OK);
            Assert.assertTrue(f.isResolved());
        } catch (UnsupportedElementInFailureException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnsupportedNetworkElementStatusException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    

}
