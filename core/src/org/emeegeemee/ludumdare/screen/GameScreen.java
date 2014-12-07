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
import org.emeegeemee.ludumdare.entity.Player;

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

   // Body body;
    Player player;
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

//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(new Vector2(0, 0));
//
//        body = world.createBody(bodyDef);
//        CircleShape cs = new CircleShape();
//        cs.setRadius(10);
//
//        body.createFixture(cs, 50);
//
//        cs.dispose();

//        PolygonShape ps = new PolygonShape();
//        ps.setAsBox(1, 20, new Vector2(13, 0), 0);
//
//        Fixture f = body.createFixture(ps, 0);
        player = new Player(10, world);


        bodies = new Body[4];

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100f, 100f);
        bodies[0] = world.createBody(bodyDef);
        bodies[0].setBullet(true);
        CircleShape cs = new CircleShape();
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
        f.setRestitution(1.5f);

        Gdx.input.setInputProcessor(this);


    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.translate(-width / 2f, -height / 2f);
    }

    private static final float S_PER_UPDATE = 1/45f;
    private float lag = 0.0f;


    @Override
    public void render(float delta) {
        lag += delta;

        while(lag >= S_PER_UPDATE) {
            physics();
            logic();

            lag -= S_PER_UPDATE;
        }

        draw(/*lag / S_PER_UPDATE*/);
    }

    private void physics() {
        world.step(S_PER_UPDATE, 6, 2);
    }

    private void logic() {

        System.out.println(direction);

        player.applyThrust(direction.cpy().nor());

        for(Body body2 : bodies) {
            Vector2 pos = body2.getPosition();
            Vector2 posCpy = pos.cpy();

            Vector2 dist = player.getPosition().cpy().sub(pos);
            body2.applyForceToCenter(dist.nor().scl(10f), true);

            if(!pos.equals(posCpy)) {
                body2.setLinearVelocity(Vector2.Zero);
            }
        }
    }

    private void draw(/*float alpha*/) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        //renderer.setProjectionMatrix(camera.combined);

        batch.begin();
        debugRenderer.render(world, camera.combined);
        batch.end();
    }

    Vector2 direction = new Vector2();
    boolean wDown;
    float angularVel = 0.0f;
    private static final float angVel = 2.0f;
    private static final float linVel = 100.0f;

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                direction.add(-1, 0);
                return true;
            case Input.Keys.D:
                direction.add(1,0);
                return true;
            case Input.Keys.W:
                direction.add(0, 1);
                return true;
            case Input.Keys.S:
                direction.add(0, -1);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                direction.add(1, 0);
                return true;
            case Input.Keys.D:
                direction.add(-1,0);
                return true;
            case Input.Keys.W:
                direction.add(0, -1);
                return true;
            case Input.Keys.S:
                direction.add(0, 1);
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
