package org.emeegeemee.ludumdare.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

/**
 * Username: Justin
 * Date: 12/6/2014
 */
public class KeyboardAndMouse extends InputAdapter implements Input {
    private Vector2 direction = new Vector2(Vector2.Zero);
    private Vector2 mousePos = new Vector2(Vector2.Zero);

    public KeyboardAndMouse() {
        Gdx.input.setInputProcessor(this);
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
        mousePos.set(screenX, screenY);
        return true;
    }

    @Override
    public Vector2 getThrust() {
        return direction.cpy().nor();
    }

    @Override
    public Vector2 getDesiredFacing(Vector2 curPos) {
        return new Vector2(mousePos.x - curPos.x, mousePos.y - curPos.y).nor();
    }
}
