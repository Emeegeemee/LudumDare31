//Launcher for Ludum Dare game of 12/5-7: Entire Game on 1 Screen
package org.emeegeemee.ludumdare.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.emeegeemee.ludumdare.LudumDare;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 480;
		config.resizable = false;
		new LwjglApplication(new LudumDare(), config);
	}
}
