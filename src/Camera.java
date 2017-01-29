import java.awt.geom.Point2D;
import java.awt.Graphics;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Displays Drawn objects using Graphics
 *
 * Contains two lists of Drawn shapes: focuses and nonFocuses. Focuses will 
 * always stay within the width and height of the screen, while non-focuses 
 * may be off screen.
 */
public class Camera {

    /**
     * Lists of shapes which are displayed on screen by the Draw method.
     */

    // These shapes will always be kept on screen.
    private ArrayList<Drawn> focusesList;

    public void removeFocus(Drawn focus) {
        focusesList.remove(focus);
    }

    public void removeNonFocus(Drawn nonFocus) {
        nonFocusList.remove(nonFocus);
    }

    // These shapes can be off screen.
    private ArrayList<Drawn> nonFocusList;

    /**
     * Adds an object to focuses or nonFocuses.
     *
     * @param object  is the item to add.
     * @param isFocus determines which list of shapes to add the object to.
     */
    public void add(Drawn object, boolean isFocus) {
        if (isFocus)
            focusesList.add(object);
        else
            nonFocusList.add(object);
    }

    /**
     * Creates a camera object.
     */
    public Camera() {
        focusesList = new ArrayList<Drawn>();
        nonFocusList = new ArrayList<Drawn>();
    }

    /**
     * Draws shapes from focusesList and nonFocusList on a Graphics object.
     *
     * @param g               is the graphics object that the shapes are drawn
     *                        on.
     * @param width           is the width of the screen in pixels.
     * @param height          is the height of the screen in pixels.
     * @param focusCircleSize is the size of the circles that are drawn
     *                        surrounding shapes in the focusList. These
     *                        circles let the user(s) know the location and
     *                        rotation of focuses. This is important when the
     *                        shapes move too far away from each other to be
     *                        seen accurately.
     */
    public void draw(
            Graphics g,
            double width,
            double height,
            double focusCircleSize) {

        // Create copies of the ArrayLists so we don't have weird concurrency
        // errors.

        Drawn[] nonFocuses = nonFocusList.toArray(new Drawn[0]);
        Drawn[] focuses = focusesList.toArray(new Drawn[0]);

        // Calculate the center of zoom, store it in a Point2D.Double point.

        Point2D.Double centerOfZoom = new Point2D.Double();

        int vertices = focuses.length;

        for (int i = 0; i < vertices; i++) {
            centerOfZoom.x += focuses[i].getPoint().x;
            centerOfZoom.y += focuses[i].getPoint().y;
        }

        centerOfZoom.x /= vertices;
        centerOfZoom.y /= vertices;

        // Calculate zoom scalar value.

        double furthest = 0;

        // Find furthest focus from the center of zoom.
        for (Drawn f : focuses) {

            // Use the Pythagorean theorem.
            double a = f.getPoint().x - centerOfZoom.x;
            double b = f.getPoint().y - centerOfZoom.y;
            double c = Math.sqrt(a * a + b * b);

            if (c > furthest)
                furthest = c;
        }

        // Use 2.5 instead of 2 so that shapes aren't directly on the edge of
        // the screen.
        double radius = width < height ? width / 2.5 : height / 2.5;

        double zoom = radius / furthest;

        // Make the zoom factor smoothly approach maximum zoom.
        if (zoom > 1) {
            zoom = -1 / zoom + 2;
        }

        // Drawn each focus.

        for (Drawn focus : focuses) {

            // Apply color.

            g.setColor(focus.getColor());

            // Transform vertices based on current zoom center and scalar.

            double[] xVerts = focus.getVertices()[0];
            double[] yVerts = focus.getVertices()[1];

            if (xVerts.length < yVerts.length)
                vertices = xVerts.length;
            else
                vertices = yVerts.length;

            for (int i = 0; i < vertices; i++) {
                xVerts[i] = (xVerts[i] - centerOfZoom.x) * zoom + width / 2;
                yVerts[i] = (yVerts[i] - centerOfZoom.y) * zoom + height / 2;
            }

            // Calculate center of shape.

            double rotation = focus.getRotation();

            Point2D.Double shapeCenter = new Point2D.Double();

            for (int i = 0; i < vertices; i++) {
                shapeCenter.x += xVerts[i];
                shapeCenter.y += yVerts[i];
            }

            shapeCenter.x /= vertices;
            shapeCenter.y /= vertices;

            // Drawn identification circles around focuses.

            // Draw the circle.
            g.drawOval(
                    (int) (shapeCenter.x - focusCircleSize / 2),
                    (int) (shapeCenter.y - focusCircleSize / 2),
                    (int) focusCircleSize,
                    (int) focusCircleSize);

            // Draw the line of rotation.
            g.drawLine(
                    (int) shapeCenter.x,
                    (int) shapeCenter.y,
                    (int) (focusCircleSize / 2 * Math.cos(rotation)
                            + shapeCenter.x),
                    (int) (focusCircleSize / 2 * Math.sin(rotation)
                            + shapeCenter.y));

            // Convert double arrays into integer arrays.

            int[] xVertsInt = new int[xVerts.length];
            int[] yVertsInt = new int[yVerts.length];
            for (int i = 0; i < xVerts.length; i++) {
                xVertsInt[i] = (int) xVerts[i];
                yVertsInt[i] = (int) yVerts[i];
            }

            // Drawn the polygon.

            if (focus.fillPolygon())
                g.fillPolygon(xVertsInt, yVertsInt, xVerts.length);
            else
                g.drawPolygon(xVertsInt, yVertsInt, xVertsInt.length);
        }

        // Drawn each non-focus.

        for (Drawn nonFocus : nonFocuses) {

            // Apply color.

            g.setColor(nonFocus.getColor());

            // Transform vertices based on current zoom center and scalar.

            double[] xVerts = nonFocus.getVertices()[0];
            double[] yVerts = nonFocus.getVertices()[1];

            if (xVerts.length < yVerts.length)
                vertices = xVerts.length;
            else
                vertices = yVerts.length;

            for (int i = 0; i < vertices; i++) {
                xVerts[i] = (xVerts[i] - centerOfZoom.x) * zoom + width / 2;
                yVerts[i] = (yVerts[i] - centerOfZoom.y) * zoom + height / 2;
            }

            // Calculate center of shape.

            double rotation = nonFocus.getRotation();

            Point2D.Double shapeCenter = new Point2D.Double();

            for (int i = 0; i < vertices; i++) {
                shapeCenter.x += xVerts[i];
                shapeCenter.y += yVerts[i];
            }

            shapeCenter.x /= vertices;
            shapeCenter.y /= vertices;

            // Convert double arrays into integer arrays.

            int[] xVertsInt = new int[xVerts.length];
            int[] yVertsInt = new int[yVerts.length];
            for (int i = 0; i < xVerts.length; i++) {
                xVertsInt[i] = (int) xVerts[i];
                yVertsInt[i] = (int) yVerts[i];
            }

            // Drawn the polygon.

            if (nonFocus.fillPolygon())
                g.fillPolygon(xVertsInt, yVertsInt, xVerts.length);
            else
                g.drawPolygon(xVertsInt, yVertsInt, xVertsInt.length);
        }
    }
}