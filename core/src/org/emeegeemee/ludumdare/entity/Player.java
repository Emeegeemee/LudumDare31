package org.emeegeemee.ludumdare.entity;

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
        System.out.println(force.scl(thrustForce));
        body.applyForceToCenter(force, true);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void rotate(Vector2 desiredFacing) {

    }

    public void setPosition(Vector2 newPosition) {

    }
}
