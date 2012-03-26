package es.upm.dit.gsi.shanks.tutorial.model.han.element.link.protrayal;

import java.awt.Color;
import java.awt.Font;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.OrientedShape3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import sim.field.network.Edge;
import sim.portrayal.LocationWrapper;
import sim.portrayal3d.SimplePortrayal3D;
import sim.portrayal3d.network.SpatialNetwork3D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.EthernetCable;

/**
 * @author a.carrera
 *
 */
public class EthernetCable3DPortrayal extends Link3DPortrayal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2475894301234684469L;

	@Override
    public TransformGroup getModel(Object object, TransformGroup model) {

        Double3D firstPoint;
        Double3D secondPoint;
        SpatialNetwork3D field;
        LocationWrapper wrapper;
        Transform3D trans = null;
        double[] startPoint = new double[3];
        double[] endPoint = new double[3];
        double[] middlePoint = new double[3];
        double labelScale = 1.0;
        double SCALING_MODIFIER = 1.0 / 5.0;

        int FONT_SIZE = 48;
        Font labelFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);

        wrapper = (LocationWrapper) object;
        Edge edge = (Edge) (wrapper.getLocation());
        Link link = (Link) wrapper.getObject();
        field = (SpatialNetwork3D) wrapper.fieldPortrayal.getField();

        Color color = this.getLinkColor(link);
        Color labelColor = this.getLabelColor(link);

        secondPoint = field.getObjectLocation(edge.to());
        firstPoint = field.getObjectLocation(edge.from());

        startPoint[0] = firstPoint.x;
        startPoint[1] = firstPoint.y;
        startPoint[2] = firstPoint.z;
        endPoint[0] = secondPoint.x;
        endPoint[1] = secondPoint.y;
        endPoint[2] = secondPoint.z;

        middlePoint[0] = (secondPoint.x + firstPoint.x) / 2;
        middlePoint[1] = (secondPoint.y + firstPoint.y) / 2;
        middlePoint[2] = (secondPoint.z + firstPoint.z) / 2;

        Transform3D offset = new Transform3D();
        offset.setTranslation(new Vector3d(middlePoint[0], middlePoint[1],
                middlePoint[2]));
        trans = offset;
        // build the whole model from scratch
        model = new TransformGroup();
        model.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

        LineArray lineGeometry1 = new LineArray(2, GeometryArray.COORDINATES);
        lineGeometry1.setCoordinate(0, startPoint);
        lineGeometry1.setCoordinate(1, middlePoint);
        lineGeometry1.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
        Shape3D lineShape1 = new Shape3D(lineGeometry1,
                SimplePortrayal3D.appearanceForColor(color));
        lineShape1.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
        setPickableFlags(lineShape1);
        lineShape1.setUserData(wrapper);
        model.addChild(lineShape1);

        LineArray lineGeometry2 = new LineArray(2, GeometryArray.COORDINATES);
        lineGeometry2.setCoordinate(0, middlePoint);
        lineGeometry2.setCoordinate(1, endPoint);
        lineGeometry2.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
        Shape3D lineShape2 = new Shape3D(lineGeometry2,
                SimplePortrayal3D.appearanceForColor(color));
        lineShape2.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
        setPickableFlags(lineShape2);
        lineShape2.setUserData(wrapper);
        model.addChild(lineShape2);

        // draw the edge labels if the user wants
        String str = link.getID();
        com.sun.j3d.utils.geometry.Text2D text = new com.sun.j3d.utils.geometry.Text2D(
                str, new Color3f(labelColor), labelFont.getFamily(),
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
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getLabelColor(es.upm.dit.gsi.shanks.model.element.link.Link)
     */
    public Color getLabelColor(Link link) {
        String status = link.getCurrentStatus();
        if (status.equals(EthernetCable.STATUS_OK)) {
            return Color.green;
        } else if (status.equals(EthernetCable.STATUS_DAMAGED)) {
            return Color.red;
        } else {
            return Color.black;
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getLinkColor(es.upm.dit.gsi.shanks.model.element.link.Link)
     */
    public Color getLinkColor(Link link) {
        String status = link.getCurrentStatus();
        if (status.equals(EthernetCable.STATUS_OK)) {
            return Color.blue;
        } else if (status.equals(EthernetCable.STATUS_DAMAGED)) {
            return Color.red;
        } else {
            return Color.black;
        }
    }

}
