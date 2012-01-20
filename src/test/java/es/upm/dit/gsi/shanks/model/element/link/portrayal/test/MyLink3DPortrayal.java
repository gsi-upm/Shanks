package es.upm.dit.gsi.shanks.model.element.link.portrayal.test;

import java.awt.Color;

import javax.media.j3d.TransformGroup;

import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;

public class MyLink3DPortrayal extends Link3DPortrayal {

    /**
     * 
     */
    private static final long serialVersionUID = -7761302141125482551L;

    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {

        return super.getModel(object, model);
    }

    //
    // Double3D firstPoint;
    // Double3D secondPoint;
    // SpatialNetwork3D field;
    // LocationWrapper wrapper;
    // Transform3D trans = null;
    // double[] startPoint = new double[3];
    // double[] endPoint = new double[3];
    // double[] middlePoint = new double[3];
    // double labelScale = 1.0;
    // double SCALING_MODIFIER = 1.0 / 5.0;
    //
    // int FONT_SIZE = 48;
    // Font labelFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);
    //
    // wrapper = (LocationWrapper) object;
    // Edge edge = (Edge) (wrapper.getLocation());
    // MyLink link = (MyLink)wrapper.getObject();
    // field = (SpatialNetwork3D) wrapper.fieldPortrayal.getField();
    //
    // Color color = this.getTargetColor(link);
    // Color labelColor = this.getLabelColor(link);
    //
    // secondPoint = field.getObjectLocation(edge.to());
    // firstPoint = field.getObjectLocation(edge.from());
    //
    // startPoint[0] = firstPoint.x;
    // startPoint[1] = firstPoint.y;
    // startPoint[2] = firstPoint.z;
    // endPoint[0] = secondPoint.x;
    // endPoint[1] = secondPoint.y;
    // endPoint[2] = secondPoint.z;
    //
    // middlePoint[0] = (secondPoint.x + firstPoint.x) / 2;
    // middlePoint[1] = (secondPoint.y + firstPoint.y) / 2;
    // middlePoint[2] = (secondPoint.z + firstPoint.z) / 2;
    //
    // Transform3D offset = new Transform3D();
    // offset.setTranslation(new Vector3d(middlePoint[0], middlePoint[1],
    // middlePoint[2]));
    // trans = offset;
    // // build the whole model from scratch
    // model = new TransformGroup();
    // model.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
    //
    // LineArray lineGeometry1 = new LineArray(2, GeometryArray.COORDINATES);
    // lineGeometry1.setCoordinate(0, startPoint);
    // lineGeometry1.setCoordinate(1, middlePoint);
    // lineGeometry1.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
    // Shape3D lineShape1 = new Shape3D(lineGeometry1,
    // SimplePortrayal3D.appearanceForColor(color));
    // lineShape1.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    // setPickableFlags(lineShape1);
    // lineShape1.setUserData(wrapper);
    // model.addChild(lineShape1);
    //
    // LineArray lineGeometry2 = new LineArray(2, GeometryArray.COORDINATES);
    // lineGeometry2.setCoordinate(0, middlePoint);
    // lineGeometry2.setCoordinate(1, endPoint);
    // lineGeometry2.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
    // Shape3D lineShape2 = new Shape3D(lineGeometry2,
    // SimplePortrayal3D.appearanceForColor(color));
    // lineShape2.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    // setPickableFlags(lineShape2);
    // lineShape2.setUserData(wrapper);
    // model.addChild(lineShape2);
    //
    // // draw the edge labels if the user wants
    // String str = link.getID();
    // com.sun.j3d.utils.geometry.Text2D text = new
    // com.sun.j3d.utils.geometry.Text2D(
    // str, new Color3f(labelColor), labelFont.getFamily(),
    // labelFont.getSize(), labelFont.getStyle());
    // text.setRectangleScaleFactor((float) (labelScale * SCALING_MODIFIER));
    //
    // // text = new Shape3D(new Text3D(labelFont3D, ""));
    //
    // OrientedShape3D o3d = new OrientedShape3D(text.getGeometry(),
    // text.getAppearance(), OrientedShape3D.ROTATE_ABOUT_POINT,
    // new Point3f(0, 0, 0));
    // o3d.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE); // may need to change
    // // the appearance
    // // (see below)
    // o3d.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE); // may need to change
    // // the geometry (see
    // // below)
    // o3d.clearCapabilityIsFrequent(Shape3D.ALLOW_APPEARANCE_WRITE);
    // o3d.clearCapabilityIsFrequent(Shape3D.ALLOW_GEOMETRY_WRITE);
    //
    // // make the offset TransformGroup
    // TransformGroup o = new TransformGroup();
    // o.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
    // o.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    // o.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    // o.clearCapabilityIsFrequent(TransformGroup.ALLOW_CHILDREN_READ);
    // o.setTransform(trans);
    // o.setUserData(str);
    //
    // // the label shouldn't be pickable -- we'll turn this off in the
    // // TransformGroup
    // clearPickableFlags(o);
    // o.addChild(o3d); // Add label to the offset TransformGroup
    // model.addChild(o);
    //
    // return model;
    // }
    //
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getLabelColor(es.upm.dit.gsi.shanks.model.element.link.Link)
     */
    public Color getLabelColor(Link link) {
        String status = link.getCurrentStatus();
        if (status.equals(MyLink.OK_STATUS)) {
            return Color.green;
        } else if (status.equals(MyLink.BROKEN_STATUS)) {
            return Color.red;
        } else {
            return Color.gray;
        }
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link3DPortrayal#getLinkColor(es.upm.dit.gsi.shanks.model.element.link.Link)
     */
    public Color getLinkColor(Link link) {
        String status = link.getCurrentStatus();
        if (status.equals(MyLink.OK_STATUS)) {
            return Color.blue;
        } else if (status.equals(MyLink.BROKEN_STATUS)) {
            return Color.red;
        } else {
            return Color.green;
        }
    }

}
