package es.upm.dit.gsi.shanks.datacenter.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
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

import com.sun.j3d.utils.geometry.Sphere;

import es.upm.dit.gsi.shanks.datacenter.model.element.device.Router;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal;

@SuppressWarnings("restriction")
public class Router3DPortrayal extends Device3DPortrayal {

	//TODO Make the animation for stealing connection.
    public Logger log = Logger.getLogger(Device3DPortrayal.class.getName());

    @Override
	public String getLabel(Device device) {
        return device.getID();
    }

    @Override
	public TransformGroup getModel(Object object, TransformGroup model) {

        Device device = (Device) object;
        model = new TransformGroup();

        int diameter = 3;
        
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
	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getDeviceColor(es.upm.dit.gsi.shanks.model.element.device.Device)
     */
    @Override
	public Color getDeviceColor(Device device) {
        HashMap<String, Boolean> status = device.getStatus();
        if (status.get(Router.STATUS_OK)) {
            return Color.green;
        } else if (status.get(Router.STATUS_NOISP_SERVICE)||status.get(Router.STATUS_DISCONNECTED)) {
            return Color.red;
        } else if (status.get(Router.STATUS_CONGESTED)) {
            return Color.yellow;
        } else if (status.get(Router.STATUS_OFF)) {
            return Color.gray;
        } else {
        	return Color.black;
        }
    }
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal#getLabelColor(es.upm.dit.gsi.shanks.model.element.device.Device)
     */
    @Override
	public Color getLabelColor(Device device) {
        HashMap<String, Boolean> status = device.getStatus();
        if (status.get(Router.STATUS_OK)) {
            return Color.green;
        } else if (status.get(Router.STATUS_NOISP_SERVICE)||status.get(Router.STATUS_DISCONNECTED)) {
            return Color.red;
        } else if (status.get(Router.STATUS_CONGESTED)) {
            return Color.yellow;
        } else if (status.get(Router.STATUS_OFF)) {
            return Color.gray;
        } else {
        	return Color.black;
        }
    }
    private static final long serialVersionUID = 6319038986129405512L;
}
