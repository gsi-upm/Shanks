package es.upm.dit.gsi.shanks.model.element.device.portrayal.test;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Sphere;

import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;

public class MyDevice3DPortrayal extends Device3DPortrayal {

    /**
     * 
     */
    private static final long serialVersionUID = -7701586034482637653L;

    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {
        MyDevice device = (MyDevice) object;
        model = new TransformGroup();

        Color3f colour;
        Sphere s = new Sphere(50);
        String status = device.getCurrentStatus();
        if (status.equals(MyDevice.OK_STATUS)) {
            colour = new Color3f(Color.green);
        } else if (status.equals(MyDevice.NOK_STATUS)) {
            colour = new Color3f(Color.red);
        } else if (status.equals(MyDevice.UNKOWN_STATUS)) {
            colour = new Color3f(Color.gray);
        } else {
            colour = new Color3f(Color.black);
        }

        model.removeAllChildren();
        Appearance appearance = new Appearance();
        appearance.setColoringAttributes(new ColoringAttributes(colour,
                ColoringAttributes.SHADE_GOURAUD));
        Material m = new Material();
        m.setAmbientColor(colour);
        m.setEmissiveColor(0f, 0f, 0f);
        m.setDiffuseColor(colour);
        m.setSpecularColor(1f, 1f, 1f);
        m.setShininess(128f);
        appearance.setMaterial(m);
        s.setAppearance(appearance);
        model.addChild(s);
        model.setName(device.getID());
        clearPickableFlags(model);

        return model;
    }

}
