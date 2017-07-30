package no.kh498.motc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.kh498.motc.MotC;

public class DesktopLauncher {

    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1278;
        config.height = 720;

        config.resizable = false;

        config.vSyncEnabled = false;
        config.foregroundFPS = 0;
        config.fullscreen = false;
        new LwjglApplication(new MotC(), config);
    }
}
