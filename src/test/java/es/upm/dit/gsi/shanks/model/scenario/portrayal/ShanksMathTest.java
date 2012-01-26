package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import junit.framework.Assert;

import org.junit.Test;

import sim.util.Double2D;
import sim.util.Double3D;

public class ShanksMathTest {

    @Test
    public void ShanksMathAdd3D() {
        Double3D orig = new Double3D(1, 0, 1);
        Double3D offset = new Double3D(0, 1, 1);
        Double3D result = ShanksMath.add(orig, offset);
        Assert.assertEquals(1.0, result.x);
        Assert.assertEquals(1.0, result.y);
        Assert.assertEquals(2.0, result.z);
    }

    @Test
    public void ShanksMathAdd2D() {
        Double2D orig = new Double2D(1, 3);
        Double2D offset = new Double2D(-4, 1);
        Double2D result = ShanksMath.add(orig, offset);
        Assert.assertEquals(-3.0, result.x);
        Assert.assertEquals(4.0, result.y);
    }

    @Test
    public void ShanksMathRotate2D() {
        Double2D orig = new Double2D(1, 0);
        Double2D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90);
        Assert.assertEquals(0.0, result.x);
        Assert.assertEquals(1.0, result.y);
    }

    @Test
    public void ShanksMathRotate2D2() {
        Double2D orig = new Double2D(1, 0);
        Double2D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_90);
        Assert.assertEquals(0.0, result.x);
        Assert.assertEquals(0.0, result.y);
    }

    @Test
    public void ShanksMathRotate2D3() {
        Double2D orig = new Double2D(1, 0);
        Double2D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_180);
        Assert.assertEquals(0.0, result.x);
        Assert.assertEquals(-1.0, result.y);
    }

    @Test
    public void ShanksMathRotate3D() {
        Double3D orig = new Double3D(1, 0, 0);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        Assert.assertEquals(0.0, result.x);
        Assert.assertEquals(1.0, result.y);
        Assert.assertEquals(0.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D2() {
        Double3D orig = new Double3D(1, 0, 1);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        Assert.assertEquals(0.0, result.x);
        Assert.assertEquals(1.0, result.y);
        Assert.assertEquals(1.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D3() {
        Double3D orig = new Double3D(1, 2, 1);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_180, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        Assert.assertEquals(-1.0, result.x);
        Assert.assertEquals(-2.0, result.y);
        Assert.assertEquals(1.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D4() {
        Double3D orig = new Double3D(1, 2, 1);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        Assert.assertEquals(-2.0, result.x);
        Assert.assertEquals(1.0, result.y);
        Assert.assertEquals(1.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D5() {
        Double3D orig = new Double3D(1, 0, 0);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_90, ShanksMath.ANGLE_0);
        Assert.assertEquals(0.0, result.x);
        Assert.assertEquals(1.0, result.y);
        Assert.assertEquals(0.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D6() {
        Double3D orig = new Double3D(1, 0, 1);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_90, ShanksMath.ANGLE_270);
        Assert.assertEquals(-1.0, result.x);
        Assert.assertEquals(0.0, result.y);
        Assert.assertEquals(-1.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D7() {
        Double3D orig = new Double3D(1, 2, 1);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_180, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        Assert.assertEquals(-1.0, result.x);
        Assert.assertEquals(-2.0, result.y);
        Assert.assertEquals(1.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D8() {
        Double3D orig = new Double3D(1, 2, 1);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        Assert.assertEquals(-2.0, result.x);
        Assert.assertEquals(1.0, result.y);
        Assert.assertEquals(1.0, result.z);
    }

    @Test
    public void ShanksMathRotate3D9() {
        Double3D orig = new Double3D(1, 0, 1);
        Double3D result = ShanksMath.rotate(orig, ShanksMath.ANGLE_90, ShanksMath.ANGLE_0, ShanksMath.ANGLE_270);
        Assert.assertEquals(0.0, result.x);
        Assert.assertEquals(1.0, result.y);
        Assert.assertEquals(-1.0, result.z);
    }
}
