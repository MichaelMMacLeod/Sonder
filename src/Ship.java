import java.awt.geom.Point2D;

import java.util.Arrays;

public class Ship extends Moveable {

    private double acceleration;
    private double rotationSpeed;

    private Drawn healthBar;
    private Drawn healthBarOutline;
    private double maxHealth;
    private double health;
    private double percentHealthOnHit;

    private Drawn shield;
    private double maxShieldHealth;
    private double shieldHealth;
    private double shieldHealthRegen;

    private String[] keys =
            {
                    "rotate counter clockwise key",
                    "rotate clockwise key",
                    "thrust key",
                    "fire key"
            };

    public String[] getKeys() {
        return Arrays.copyOf(keys, keys.length);
    }

    public Ship(
            Drawn shape,
            double acceleration,
            double rotationSpeed,
            double deceleration,
            String[] keys,
            double percentHealthOnHit) {

        super(shape, deceleration);

        this.acceleration = acceleration;
        this.rotationSpeed = rotationSpeed;
        this.keys = keys;
        this.percentHealthOnHit = percentHealthOnHit;

        maxHealth = 50;
        health = 50;

        maxShieldHealth = 50;
        shieldHealth = 50;
        shieldHealthRegen = 0.05;

        shield = new Drawn(
                new double[][]
                        {
                                {-1, 0, 1, 1.5, 1, 0, -1, -1.5},
                                {-1, -1.5, -1, 0, 1, 1.5, 1, 0}
                        },
                new Point2D.Double(
                        shape().getPoint().x,
                        shape().getPoint().y),
                shieldHealth,
                0,
                shape().getColor(),
                false);

        healthBar = new Drawn(
                new double[][]
                        {
                                {-maxHealth, maxHealth, maxHealth, -maxHealth},
                                {-4, -4, 4, 4}
                        },
                new Point2D.Double(
                        shape().getPoint().x,
                        shape().getPoint().y),
                1,
                0,
                shape().getColor(),
                true);
        healthBarOutline = new Drawn(
                new double[][]
                        {
                                {-maxHealth, maxHealth, maxHealth, -maxHealth},
                                {-4, -4, 4, 4}
                        },
                new Point2D.Double(
                        shape().getPoint().x,
                        shape().getPoint().y),
                1,
                0,
                shape().getColor(),
                false);
    }

    @Override
    public void update() {
        shape.translate(vector[0], vector[1]);

        vector[0] *= deceleration;
        vector[1] *= deceleration;

        healthBarOutline.setLocation(
                shape().getPoint().x,
                shape().getPoint().y + 50);
        healthBar.setLocation(
                shape().getPoint().x,
                shape().getPoint().y + 50);

        shield.setLocation(
                shape().getPoint().x,
                shape().getPoint().y);

        shield.setSize(shieldHealth);
        shield.setRotation(shape().getRotation());

        shieldHealth = shieldHealth + shieldHealthRegen < maxShieldHealth ? shieldHealth + shieldHealthRegen : maxShieldHealth;

        healthBar.setShape(
                new double[][]
                        {
                                {-health, health, health, -health},
                                {-4, -4, 4, 4}
                        });
    }

    public void hitShield(double damage) {
        shieldHealth = shieldHealth - damage > 0 ? shieldHealth - damage : 0;
    }

    public double getPercentHealthOnHit() {
        return percentHealthOnHit;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Drawn getShield() {
        return shield;
    }

    public Drawn[] getHealthBar() {
        return new Drawn[] { healthBar, healthBarOutline };
    }

    public void hit(double damage) {
        health = health - damage > 0 ? health - damage : 0;
    }

    public void heal(double amount) {
        health = health < maxHealth ? health + amount : maxHealth;
    }

    public void thrust() {
        vector[0] += acceleration * Math.cos(shape.getRotation());
        vector[1] += acceleration * Math.sin(shape.getRotation());
    }

    public void rotate(boolean clockwise) {
        shape.rotate((clockwise ? 1 : -1) * Math.PI / rotationSpeed);
    }

    public boolean shieldHitBy(Projectile p) {
        Drawn pShape = p.shape();
        return shield.contains(pShape);
    }

    public boolean hitBy(Projectile p) {
        Drawn pShape = p.shape();
        return shape.contains(pShape);
    }
}