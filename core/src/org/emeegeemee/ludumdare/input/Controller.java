package org.emeegeemee.ludumdare.input;

import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import org.emeegeemee.ludumdare.entity.Player;

/**
 * Username: Justin
 * Date: 12/6/2014
 */
public class Controller extends ControllerAdapter implements Input {
    private static final double EPSILON = 0.2;

    private Vector2 movement = new Vector2(Vector2.Zero);
    private Vector2 facing = new Vector2(Vector2.Zero);
    private Player player;

    public Controller(Player player) {
        Controllers.addListener(this);
        this.player = player;
    }

    @Override
    public boolean axisMoved (com.badlogic.gdx.controllers.Controller controller, int axisIndex, float value) {

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
                if(Math.abs(value) > EPSILON)
                    facing.y = value;
                else {
                    facing.set(player.getAngle());
                }
                return true;
            case 3:
                if(Math.abs(value) > EPSILON)
                    facing.x = value;
                else {
                    facing.set(player.getAngle());
                }
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
    public Vector2 getDesiredFacing() {
        return facing.cpy();
    }
}
