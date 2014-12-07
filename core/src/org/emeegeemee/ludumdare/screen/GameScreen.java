package org.emeegeemee.ludumdare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.emeegeemee.ludumdare.LudumDare;
import org.emeegeemee.ludumdare.entity.Enemy;
import org.emeegeemee.ludumdare.entity.Player;
import org.emeegeemee.ludumdare.input.Controller;
import org.emeegeemee.ludumdare.input.Input;
import org.emeegeemee.ludumdare.input.KeyboardAndMouse;

/**
 * Username: Justin
 * Date: 12/5/2014
 */
public class GameScreen extends ScreenAdapter {
    private LudumDare game;

    Input input;

    SpriteBatch batch;
    //ShapeRenderer renderer;
    //Texture img;

    OrthographicCamera camera;

    World world;
    Box2DDebugRenderer debugRenderer;

   // Body body;
    Player player;
    Enemy[] enemies;

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

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(Vector2.Zero);
        Body body = world.createBody(bodyDef);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(Gdx.graphics.getWidth()/2, 1f, new Vector2(0, -Gdx.graphics.getHeight()/2-2), 0);
        body.createFixture(ps, 0);

        ps.setAsBox(Gdx.graphics.getWidth()/2, 1f, new Vector2(0, Gdx.graphics.getHeight()/2+2), 0);
        body.createFixture(ps, 0);

        ps.setAsBox(1f, Gdx.graphics.getHeight()/2, new Vector2(Gdx.graphics.getWidth()/2+2, 0), 0);
        body.createFixture(ps, 0);

        ps.setAsBox(1f, Gdx.graphics.getHeight()/2, new Vector2(-Gdx.graphics.getWidth()/2-2, 0), 0);
        body.createFixture(ps, 0);

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        player = new Player(10, world);


        enemies = new Enemy[4];

        enemies[0] = new Enemy(100f, 100f, 5f, 1f, world);
        enemies[1] = new Enemy(100f, -100f, 5f, 1f, world);
        enemies[2] = new Enemy(-100f, 100f, 5f, 1f, world);
        enemies[3] = new Enemy(-100f, -100f, 5f, 1f, world);

        if(Controllers.getControllers().size > 0) {
            input = new Controller(player);
        }
        else {
            input = new KeyboardAndMouse(player);
        }


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
        player.applyThrust(input.getThrust());
        player.rotate(input.getDesiredFacing());

        for(Enemy enemy : enemies) {
            enemy.update(player.getPosition());
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
}
