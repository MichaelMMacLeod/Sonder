import java.awt.geom.Point2D;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.geom.Area;

import java.util.Arrays;

/**
 * Holds a polygon defined by a series of x and y vertices.
 *
 * The polygon has its center at (0, 0), a scale of 1, no translation, 
 * and no rotation.
 *
 * When asked for the polygon, Drawn returns it with the proper 
 * transformations (scaling, translation, rotation).
 *
 * Also tracks the color of the polygon.
 */
public class Drawn {

    /**
     * Signifies if the polygon should be filled in or outlined.
     */

    private boolean fill;

    public boolean fillPolygon() {
        return fill;
    }

    /**
     * X and y coordinates of the shape in space.
     */

    private Point2D.Double location;

    protected Point2D.Double getPoint() {
        double[] tx = Arrays.copyOf(xVertices, vertices);
        double[] ty = Arrays.copyOf(yVertices, vertices);

        transform(tx, ty, vertices, rotation, location.x, location.y, size);

        Point2D.Double center = new Point2D.Double();

        for (int i = 0; i < vertices; i++) {
            center.x += tx[i];
            center.y += ty[i];
        }

        center.x /= vertices;
        center.y /= vertices;

        return center;
    }

    protected void setLocation(double x, double y) {
        location.x = x;
        location.y = y;
    }

    protected void translate(double dx, double dy) {
        location.x += dx;
        location.y += dy;
    }

    /**
     * Value which scales the vertices to a new size.
     */

    private double size;

    protected void setSize(double size) {
        this.size = size;
    }

    /**
     * X and y values defining the vertices of a polygon. There must be the
     * same number of x and y vertices.
     *
     * These values represent the polygon with no rotation or translation and
     * a scale of 1.
     */

    private double[] xVertices, yVertices;

    /**
     * Returns transformed vertices.
     */
    protected double[][] getVertices() {
        double[] tx = Arrays.copyOf(xVertices, vertices);
        double[] ty = Arrays.copyOf(yVertices, vertices);

        transform(tx, ty, vertices, rotation, location.x, location.y, size);

        double[][] verts = {tx, ty};

        return verts;
    }

    /**
     * Number of vertices.
     */

    private int vertices;

    /**
     * Rotation of the shape.
     */

    private double rotation;

    protected double getRotation() {
        return rotation;
    }

    protected void rotate(double rotation) {
        this.rotation += rotation;
    }

    protected void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * Color of the shape.
     */

    private Color color;

    protected Color getColor() {
        return color;
    }

    protected void setColor(Color color) {
        this.color = color;
    }

    /**
     * Vertices of certain shapes
     */

    // TODO: find a better way to store shapes. Maybe try reading from a file.
    protected static final double[][] TRIANGLE =
            {
                    {-1, 1, -1},
                    {-1, 0, 1}
            };

    protected static final double[][] SQUARE =
            {
                    {-1, 1, 1, -1},
                    {-1, -1, 1, 1}
            };

    /**
     * Creates a shape defined by a series of vertices
     *
     * @param shape    is the set of vertices defining the shape. X vertices
     *                 are stored in shape[0]. Y vertices are stored in
     *                 shape[1]. There must be an equal number of x and y
     *                 vertices.
     * @param location is the (x, y) coordinate of the shape.
     * @param size     is the size of the shape.
     * @param rotation is the angle of rotation in radians.
     * @param color    is the color of the shape.
     * @param fill     is true if the polygon should be drawn filled in, or
     *                 false if it should be drawn outlined.
     */
    public Drawn(
            double[][] shape,
            Point2D.Double location,
            double size,
            double rotation,
            Color color,
            boolean fill) {

        setShape(shape);

        this.location = new Point2D.Double(location.x, location.y);
        this.size = size;
        this.rotation = rotation;
        this.color = color;
        this.fill = fill;
    }

    /**
     * Stores a shape in xVertices and yVertices
     *
     * @param shape is the set of vertices defining the shape. X vertices
     *              are stored in shape[0]. Y vertices are stored in
     *              shape[1]. There must be an equal number of x and y
     *              vertices.
     */
    public void setShape(double[][] shape) {

        // Get vertices

        if (shape[0].length < shape[1].length)
            vertices = shape[0].length;
        else
            vertices = shape[1].length;

        double[] srcXVerticies = Arrays.copyOf(shape[0], vertices);
        double[] srcYVerticies = Arrays.copyOf(shape[1], vertices);

        // Move the center of the vertices to (0,0)

        double cx = 0, cy = 0;

        for (int i = 0; i < vertices; i++) {
            cx += srcXVerticies[i];
            cy += srcYVerticies[i];
        }
        cx /= vertices;
        cy /= vertices;

        for (int i = 0; i < vertices; i++) {
            srcXVerticies[i] -= cx;
            srcYVerticies[i] -= cy;
        }

        // Store vertices

        xVertices = srcXVerticies;
        yVertices = srcYVerticies;
    }

    /**
     * Applies a transformation to a series of points.
     *
     * @param xpoints      are the x coordinates to be transformed.
     * @param ypoints      are the y coordinates to be transformed.
     * @param npoints      is the number of points.
     * @param angle        is the angle of rotation in radians. Assumes that
     *                     the centroid of the vertices is located at (0, 0).
     * @param xtranslation is the translation in the x dimension.
     * @param ytranslation is the translation in the y dimension.
     * @param scale        is the scalar value.
     */
    private static void transform(
            double[] xpoints,
            double[] ypoints,
            int npoints,
            double angle,
            double xtranslation,
            double ytranslation,
            double scale) {

        double sin = Math.sin(angle), cos = Math.cos(angle);

        double[] xpointsNew = new double[npoints];
        double[] ypointsNew = new double[npoints];

        for (int i = 0; i < npoints; i++) {
            xpointsNew[i] = xpoints[i] * cos - ypoints[i] * sin;
            ypointsNew[i] = xpoints[i] * sin + ypoints[i] * cos;
        }

        for (int i = 0; i < npoints; i++) {
            xpoints[i] = xpointsNew[i] * scale + xtranslation;
            ypoints[i] = ypointsNew[i] * scale + ytranslation;
        }
    }

    /**
     * Checks if the shape contains a polygon.
     *
     * @param otherShape is the other polygon
     */
    protected boolean contains(Drawn otherShape) {
        double[] tx = getVertices()[0];
        double[] ty = getVertices()[1];

        double[] otherx = otherShape.getVertices()[0];
        double[] othery = otherShape.getVertices()[1];

        int otherVertices = 0;

        if (otherx.length < othery.length)
            otherVertices = otherx.length;
        else
            otherVertices = othery.length;

        int[] iotherx = new int[otherVertices];
        int[] iothery = new int[otherVertices];

        int[] itx = new int[vertices];
        int[] ity = new int[vertices];

        for (int i = 0; i < otherVertices; i++) {
            iotherx[i] = (int) otherx[i];
            iothery[i] = (int) othery[i];
        }

        for (int i = 0; i < vertices; i++) {
            itx[i] = (int) tx[i];
            ity[i] = (int) ty[i];
        }

        Polygon polygon = new Polygon(itx, ity, vertices);
        Polygon otherPolygon = new Polygon(iotherx, iothery, otherVertices);

        Area polygonArea = new Area(polygon);

        polygonArea.intersect(new Area(otherPolygon));

        return !polygonArea.isEmpty();
    }
}