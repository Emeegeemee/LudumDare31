package org.emeegeemee.ludumdare.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import org.emeegeemee.ludumdare.entity.Player;

/**
 * Username: Justin
 * Date: 12/6/2014
 */
public class KeyboardAndMouse extends InputAdapter implements Input {
    private Vector2 direction = new Vector2(Vector2.Zero);
    private Vector2 mousePos = new Vector2(Vector2.Zero);

    private Player player;

    public KeyboardAndMouse(Player player) {
        Gdx.input.setInputProcessor(this);
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case com.badlogic.gdx.Input.Keys.A:
                direction.add(-1, 0);
                return true;
            case com.badlogic.gdx.Input.Keys.D:
                direction.add(1,0);
                return true;
            case com.badlogic.gdx.Input.Keys.W:
                direction.add(0, 1);
                return true;
            case com.badlogic.gdx.Input.Keys.S:
                direction.add(0, -1);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case com.badlogic.gdx.Input.Keys.A:
                direction.add(1, 0);
                return true;
            case com.badlogic.gdx.Input.Keys.D:
                direction.add(-1,0);
                return true;
            case com.badlogic.gdx.Input.Keys.W:
                direction.add(0, -1);
                return true;
            case com.badlogic.gdx.Input.Keys.S:
                direction.add(0, 1);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean mouseMoved (int screenX, int screenY) {
        mousePos.set(screenX - Gdx.graphics.getWidth()/2, -screenY + Gdx.graphics.getHeight()/2);
        return true;
    }

    @Override
    public Vector2 getThrust() {
        return direction.cpy().nor();
    }

    @Override
    public Vector2 getDesiredFacing() {
        Vector2 curPos = player.getPosition();
        return new Vector2(mousePos.x - curPos.x, mousePos.y - curPos.y).nor();
    }
}
