package org.emeegeemee.ludumdare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.emeegeemee.ludumdare.LudumDare;

/**
 * Username: Justin
 * Date: 12/5/2014
 */
public class GameScreen extends ScreenAdapter implements InputProcessor {
    private LudumDare game;

    SpriteBatch batch;
    //ShapeRenderer renderer;
    //Texture img;

    OrthographicCamera camera;

    World world;
    Box2DDebugRenderer debugRenderer;

    Body body;
    Body[] bodies;

    public GameScreen(LudumDare gam) {
        game = gam;

        batch = new SpriteBatch();
        //renderer = new ShapeRenderer();

        World.setVelocityThreshold(0.2f);
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Gdx.app.log("World", "contact!");
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });


        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(0, 0));

        body = world.createBody(bodyDef);
        CircleShape cs = new CircleShape();
        cs.setRadius(10);

        body.createFixture(cs, 50);

        cs.dispose();

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(1, 20, new Vector2(13, 0), 0);

        body.createFixture(ps, 0);

        bodies = new Body[4];

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100f, 100f);
        bodies[0] = world.createBody(bodyDef);
        bodies[0].setBullet(true);
        cs = new CircleShape();
        cs.setRadius(5);

        Fixture f = bodies[0].createFixture(cs, 0);
        f.setRestitution(1.0f);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100f, -100f);
        bodies[1] = world.createBody(bodyDef);
        bodies[1].setBullet(true);
        cs = new CircleShape();
        cs.setRadius(5);

        f = bodies[1].createFixture(cs, 0);
        f.setRestitution(1.0f);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-100f, 100f);
        bodies[2] = world.createBody(bodyDef);
        bodies[2].setBullet(true);
        cs = new CircleShape();
        cs.setRadius(5);

        f = bodies[2].createFixture(cs, 0);
        f.setRestitution(1.0f);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-100f, -100f);
        bodies[3] = world.createBody(bodyDef);
        bodies[3].setBullet(true);
        cs = new CircleShape();
        cs.setRadius(5);

        f = bodies[3].createFixture(cs, 0);
        f.setRestitution(1.0f);

        Gdx.input.setInputProcessor(this);


    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.translate(-width / 2f, -height / 2f);
    }

    @Override
    public void render(float delta) {
        if(wDown) {
            force.x = MathUtils.cos(body.getAngle()) * linVel;
            force.y = MathUtils.sin(body.getAngle()) * linVel;
        }
        else {
            force.set(0, 0);
        }

        for(Body body2 : bodies) {
            Vector2 pos = body2.getPosition();
            Vector2 posCpy = pos.cpy();

            Vector2 dist = body.getPosition().cpy().sub(pos);
            body2.setLinearVelocity(body2.getLinearVelocity().lerp(dist.nor(), 0.8f).scl(20));

            posCpy.x = MathUtils.clamp(pos.x, -200, 200);
            posCpy.y = MathUtils.clamp(pos.y, -200, 200);

            if(!pos.equals(posCpy)) {
                float rot = -20 + MathUtils.random(40f);
                body2.setLinearVelocity(dist.nor().scl(20f).rotate(rot));
            }
        }


        body.setLinearVelocity(force);
        body.setAngularVelocity(angularVel);
        world.step(1/45f, 6, 2);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        //renderer.setProjectionMatrix(camera.combined);

        batch.begin();
        debugRenderer.render(world, camera.combined);
        batch.end();
    }

    Vector2 force = new Vector2();
    boolean wDown;
    float angularVel = 0.0f;
    private static final float angVel = 2.0f;
    private static final float linVel = 100.0f;

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                angularVel += angVel;
                return true;
            case Input.Keys.D:
                angularVel -= angVel;
                return true;
            case Input.Keys.W:
                wDown = true;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                angularVel -= angVel;
                return true;
            case Input.Keys.D:
                angularVel += angVel;
                return true;
            case Input.Keys.W:

                wDown = false;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
