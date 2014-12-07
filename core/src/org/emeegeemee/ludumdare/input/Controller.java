package org.emeegeemee.ludumdare.input;

import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;

/**
 * Username: Justin
 * Date: 12/6/2014
 */
public class Controller extends ControllerAdapter implements Input {
    private static final double EPSILON = 0.2;

    private Vector2 movement = new Vector2(Vector2.Zero);
    private Vector2 facing = new Vector2(Vector2.Zero);

    public Controller() {
        Controllers.addListener(this);
    }

    @Override
    public boolean axisMoved (com.badlogic.gdx.controllers.Controller controller, int axisIndex, float value) {
        System.out.println(value);
        switch(axisIndex) {
            case 0:
                value = -value;
                movement.y = Math.abs(value) > EPSILON ? value : 0.0f;
                return true;
            case 1:
                movement.x = Math.abs(value) > EPSILON ? value : 0.0f;
                return true;
            case 2:
                value = -value;
                facing.y = Math.abs(value) > EPSILON ? value : 0.0f;
                return true;
            case 3:
                facing.x = Math.abs(value) > EPSILON ? value : 0.0f;
                return true;
            default:
                return false;
        }
    }

    @Override
    public Vector2 getThrust() {
        return movement.cpy();
    }

    @Override
    public Vector2 getDesiredFacing(Vector2 curPos) {
        return facing.cpy();
    }
}
