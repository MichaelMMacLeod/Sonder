package com.github.Sonder.Visual;

public class Connection extends Moveable {
    private Chain source;
    private Chain reference;

    Connection(double ax, double ay, double r, Chain source) {
        super(ax, ay, r);

        this.source = source;
    }

    final Chain getReference() {
        return reference;
    }

    @Override
    public void translate(double dx, double dy) {
        super.translate(dx, dy);

        if (reference != null)
            reference.translate(dx, dy);
    }

    @Override
    public void rotate(double dt) {
        super.rotate(dt);

        if (reference != null)
            reference.rotate(dt);
    }

    public final void detachReference() {
        if (reference != null) {
            reference.detach();
            reference = null;
        }
    }

    public final void attachReference(Chain chain) {
        if (reference == null) {
            chain.translate(getAX() - chain.getLink().x, getAY() - chain.getLink().y);
            chain.setAnchor(chain.getLink().x, chain.getLink().y);
            chain.rotate(getR() - chain.getR());

            chain.setParent(this);

            reference = chain;
        }
    }
}
