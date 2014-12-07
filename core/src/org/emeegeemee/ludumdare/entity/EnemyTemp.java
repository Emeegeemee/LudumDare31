package org.emeegeemee.ludumdare.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by jakej on 12/6/2014.
 */
public class Enemy {

    private Body body;

    public Enemy(float x, float y, float radius, float restitution, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        body.setBullet(true);
        CircleShape cs = new CircleShape();
        cs.setRadius(radius);

        Fixture f = body.createFixture(cs, 0);
        cs.dispose();
        f.setRestitution(restitution);
    }

    public void update(Vector2 gravityDirection) {

        Vector2 dist = gravityDirection.cpy().sub(body.getPosition());
        body.applyForceToCenter(dist.nor().scl(10f), true);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
