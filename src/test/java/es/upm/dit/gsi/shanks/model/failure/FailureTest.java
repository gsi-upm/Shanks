package es.upm.dit.gsi.shanks.model.failure;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
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
        f.addAffectedElement(d, MyDevice.NOK);
        Assert.assertEquals("MyFailure", f.getID());
        Assert.assertEquals(MyDevice.OK, d.getCurrentStatus());
        Assert.assertEquals(false, f.isActive());
        Assert.assertTrue(f.getAffectedElements().size()==1);
        Assert.assertTrue(f.getCurrentAffectedElements().size()==0);
    }

}
