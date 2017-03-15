package com.github.Sonder.Visual;

import java.awt.geom.Point2D;

public interface Linked extends Transformable {
    Connection[] getConnections();
    boolean hasInChain(Linked chain);
    void setParent(Connection parent);
    void detach();
    void detachFromParent();
    void attachChild(Connection connection, Linked chain);
    Point2D.Double getLink();
    Point2D.Double[] getConnectionPoints();
}
