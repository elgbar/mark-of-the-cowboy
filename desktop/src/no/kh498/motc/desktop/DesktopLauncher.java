package no.kh498.motc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.kh498.motc.MotC;

public class DesktopLauncher {
    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = false;
//        config.fullscreen = true;
        config.foregroundFPS = -1;
        new LwjglApplication(new MotC(), config);
    }
}
