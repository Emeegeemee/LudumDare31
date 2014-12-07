package org.emeegeemee.ludumdare.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by jakej on 12/6/2014.
 */
public class Player {

    private Body body;
    private float thrustForce;
    private float acceleration = 100;
    private float maxVelocity = 100;
    private float angularVelocityRads = 2f;

    public Player(int size, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(0, 0));

        body = world.createBody(bodyDef);
        CircleShape cs = new CircleShape();
        cs.setRadius(size);

        body.createFixture(cs, 50);
        cs.dispose();

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(1, size*2, new Vector2(size+3, 0), 0);

        body.createFixture(ps, 0);
        ps.dispose();
        body.setLinearDamping(0.8f);
        thrustForce = acceleration*body.getMass();

    }

    public void applyThrust(Vector2 force) {
        body.applyForceToCenter(force.scl(thrustForce), true);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void rotate(Vector2 desiredFacing) {
        Vector2 playerFacing = new Vector2(MathUtils.cos(body.getAngle()), MathUtils.sin(body.getAngle()));
        float angle = playerFacing.angleRad(desiredFacing);

        if (Math.abs(angle) <= 0.05) {
            body.setTransform(body.getPosition(), desiredFacing.angleRad());
        }
        else {
            float omega = angle < 0f ? -angularVelocityRads : angularVelocityRads;
            body.setAngularVelocity(omega);
        }

    }

    public Vector2 getAngle() {
        return new Vector2(MathUtils.cos(body.getAngle()), MathUtils.sin(body.getAngle()));
    }

    public void setPosition(Vector2 newPosition) {

    }
}
