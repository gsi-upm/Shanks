package es.upm.dit.gsi.shanks.model.failure;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.exception.UnsupportedElementInFailureException;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;

/**
 * @author a.carrera
 *
 */
public class FailureTest {

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
     * @throws UnsupportedNetworkElementStatusException
     */
    @Test
    public void createFailureAndNoActiveIt() throws UnsupportedNetworkElementStatusException {
        Failure f = new MyFailure();
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        try {
            f.addAffectedElement(d, MyDevice.NOK_STATUS);
            Assert.assertEquals(MyDevice.OK_STATUS, d.getCurrentStatus());
            Assert.assertEquals(false, f.isActive());
            Assert.assertTrue(f.getAffectedElements().size()==1);
            Assert.assertTrue(f.getCurrentAffectedElements().size()==0);
        } catch (UnsupportedElementInFailureException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * @throws UnsupportedNetworkElementStatusException
     */
    @Test
    public void createFailureAndActiveIt() throws UnsupportedNetworkElementStatusException {
        Failure f = new MyFailure();
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        try {
            f.addAffectedElement(d, MyDevice.NOK_STATUS);
            f.activateFailure();
            Assert.assertEquals(MyDevice.NOK_STATUS, d.getCurrentStatus());
            Assert.assertEquals(true, f.isActive());
            Assert.assertTrue(f.getAffectedElements().size()==1);
            Assert.assertTrue(f.getCurrentAffectedElements().size()==1);
        } catch (UnsupportedElementInFailureException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * @throws UnsupportedNetworkElementStatusException
     */
    @Test
    public void createFailureAndDeactiveIt() throws UnsupportedNetworkElementStatusException {
        Failure f = new MyFailure();
        Device d = new MyDevice("MyDevice", MyDevice.OK_STATUS, false);
        try {
            f.addAffectedElement(d, MyDevice.NOK_STATUS);
            f.activateFailure();
            f.deactivateFailure();
            Assert.assertEquals(MyDevice.OK_STATUS, d.getCurrentStatus());
            Assert.assertEquals(false, f.isActive());
            Assert.assertTrue(f.getAffectedElements().size()==1);
            Assert.assertTrue(f.getCurrentAffectedElements().size()==0);
        } catch (UnsupportedElementInFailureException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * @throws UnsupportedNetworkElementStatusException
     */
    @Test
    public void createFailureAndAddNonPossibleElement() throws UnsupportedNetworkElementStatusException {
        Failure f = new MyFailure();
        Link l = new MyLink("L1", MyLink.OK_STATUS, 3);
        f.removePossibleAffectedElements(MyLink.class);
        boolean catched = false;
        try {
            f.addAffectedElement(l, MyLink.BROKEN_STATUS);
        } catch (UnsupportedElementInFailureException e) {
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    /**
     * @throws UnsupportedNetworkElementStatusException
     */
    @Test
    public void createFailureAndResolveIt() throws UnsupportedNetworkElementStatusException {
        Failure f = new MyFailure();
        Link l = new MyLink("L1", MyLink.OK_STATUS, 3);
        try {
            f.addAffectedElement(l, MyLink.BROKEN_STATUS);
            f.activateFailure();
            l.setCurrentStatus(MyLink.OK_STATUS);
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
