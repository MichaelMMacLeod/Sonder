//package com.github.Sonder.Visual;
//
//import java.awt.*;
//import java.awt.geom.Point2D;
//
//public class Engine extends Part {
//    private double force;
//
//    /**
//     * Constructs a Engine with no parent.
//     * @param vector is the initial direction and speed of the Part.
//     * @param x      is the x location of the centroid of the polygon in space.
//     * @param y      is the y location of the centroid of the polygon in space.
//     */
//    public Engine(
//            Point2D.Double vector,
//            double x,
//            double y,
//            double force) {
//        super(
//                vector,
//                new double[] {-5,   5, 5, -5},
//                new double[] {-10, -5, 5, 10},
//                x,
//                y,
//                Color.BLACK,
//                false);
//        this.force = force;
//    }
//
//    /**
//     * Constructs a Engine with a parent.
//     *
//     * @param parent is the parent Part.
//     * @param x      is the x location of the centroid of the polygon in space.
//     * @param y      is the y location of the centroid of the polygon in space.
//     */
//    public Engine(
//            Part parent,
//            double x,
//            double y,
//            double force) {
//        super(
//                parent,
//                new double[] {-5,   5, 5, -5},
//                new double[] {-10, -5, 5, 10},
//                x,
//                y,
//                Color.BLACK,
//                false);
//        this.force = force;
//    }
//
//    public void thrust() {
//        super.thrust();
//
//        vector.x += force * Math.cos(getRotation());
//        vector.y += force * Math.sin(getRotation());
//    }
//}
