package com.github.Sonder.Visual;

class Connection extends Moveable {
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
    protected void translate(double dx, double dy) {
        super.translate(dx, dy);

        if (reference != null)
            reference.translate(dx, dy);
    }

    @Override
    protected void rotate(double dt) {
        super.rotate();

        if (reference != null)
            reference.rotate(dt);
    }

    public final void detachReference() {
        if (reference != null) {
            reference.detachFromParent();
            reference = null;
        }
    }

    public final void attachReference(Chain chain) {
        if (reference == null) {
            chain.translate(getAX() - chain.getLinkX(), getAY() - chain.getLinkY());
            chain.setAnchor(chain.getLinkX(), chain.getLinkY());
            chain.rotate(getR() - chain.getR());

            chain.setParent(this);

            reference = chain;
        }
    }
}
