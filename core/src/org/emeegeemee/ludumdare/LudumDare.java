package org.emeegeemee.ludumdare;

import com.badlogic.gdx.Game;
import org.emeegeemee.ludumdare.screen.GameScreen;

public class LudumDare extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}
}
