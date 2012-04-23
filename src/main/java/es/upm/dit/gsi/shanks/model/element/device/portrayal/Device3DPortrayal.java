package es.upm.dit.gsi.shanks.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Logger;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.OrientedShape3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import sim.portrayal3d.SimplePortrayal3D;

import com.sun.j3d.utils.geometry.Sphere;

import es.upm.dit.gsi.shanks.model.element.device.Device;


@SuppressWarnings("restriction")
public abstract class Device3DPortrayal extends SimplePortrayal3D {


    private int diameter = 50;
    /**
     * 
     */
    private static final long serialVersionUID = 6319038986129405512L;
    public Logger log = Logger.getLogger(Device3DPortrayal.class.getName());

    public String getLabel(Device device) {
        return device.getID();
    }

    public TransformGroup getModel(Object object, TransformGroup model) {

        Device device = (Device) object;
        model = new TransformGroup();

        Color3f colour;
        Sphere s = new Sphere(diameter);
//        Cone s = new Cone(diameter, diameter);
//        ColorCube s = new ColorCube(diameter);
        
        colour = new Color3f(this.getDeviceColor(device));

        int FONT_SIZE = 48;
        Font labelFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);
        double labelScale = 1.0;
        double SCALING_MODIFIER = 1.0 / 5.0; 

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
        model.setPickable(true);
        
        


        // draw the device labels if the user wants
        Transform3D offset = new Transform3D();
        offset.setTranslation(new Vector3d(5+diameter/2,5+diameter/2,5+diameter/2));
        Transform3D trans = offset;
        String str = device.getID();
        com.sun.j3d.utils.geometry.Text2D text = new com.sun.j3d.utils.geometry.Text2D(
                str, new Color3f(this.getLabelColor(device)), labelFont.getFamily(),
                labelFont.getSize(), labelFont.getStyle());
        text.setRectangleScaleFactor((float) (labelScale * SCALING_MODIFIER));

        // text = new Shape3D(new Text3D(labelFont3D, ""));

        OrientedShape3D o3d = new OrientedShape3D(text.getGeometry(),
                text.getAppearance(), OrientedShape3D.ROTATE_ABOUT_POINT,
                new Point3f(0, 0, 0));
        o3d.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE); // may need to change
                                                           // the appearance
                                                           // (see below)
        o3d.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE); // may need to change
                                                         // the geometry (see
                                                         // below)
        o3d.clearCapabilityIsFrequent(Shape3D.ALLOW_APPEARANCE_WRITE);
        o3d.clearCapabilityIsFrequent(Shape3D.ALLOW_GEOMETRY_WRITE);

        // make the offset TransformGroup
        TransformGroup o = new TransformGroup();
        o.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        o.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        o.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        o.clearCapabilityIsFrequent(TransformGroup.ALLOW_CHILDREN_READ);
        o.setTransform(trans);
        o.setUserData(str);

        // the label shouldn't be pickable -- we'll turn this off in the
        // TransformGroup
        clearPickableFlags(o);
        o.addChild(o3d); // Add label to the offset TransformGroup
        model.addChild(o);

        return model;
    }

    abstract public Color getDeviceColor(Device device);

    abstract public Color getLabelColor(Device device);

}