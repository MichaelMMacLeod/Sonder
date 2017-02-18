//package com.github.Sonder.Visual;
//
//import java.awt.*;
//import java.awt.geom.Point2D;
//
//public class Hull extends Part {
//    /**
//     * Constructs a Hull with no parent.
//     * @param vector is the initial direction and speed of the Part.
//     * @param x      is the x location of the centroid of the polygon in space.
//     * @param y      is the y location of the centroid of the polygon in space.
//     */
//    public Hull(
//            Point2D.Double vector,
//            double x,
//            double y) {
//        super(
//                vector,
//                new double[] {-10,  10, 10, -10},
//                new double[] {-10, -10, 10,  10},
//                x,
//                y,
//                Color.BLACK,
//                false);
//    }
//
//    /**
//     * Constructs a Hull with a parent.
//     *
//     * @param parent is the parent Part.
//     * @param x      is the x location of the centroid of the polygon in space.
//     * @param y      is the y location of the centroid of the polygon in space.
//     */
//    public Hull(
//            Part parent,
//            double x,
//            double y) {
//        super(
//                parent,
//                new double[] {-10,  10, 10, -10},
//                new double[] {-10, -10, 10,  10},
//                x,
//                y,
//                Color.BLACK,
//                false);
//    }
//}