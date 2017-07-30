package no.kh498.motc.terminal.commands.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import no.kh498.motc.terminal.TmlWrtr;
import no.kh498.motc.terminal.commands.AbstractCommand;
import no.kh498.motc.terminal.commands.CmdInfo;

/**
 * @author karl henrik
 * @since 30 jul 2017
 */
public class ApplicationConfigCommand extends AbstractCommand {


    public ApplicationConfigCommand() {
        super("config", "[--fs bool] [--vsnc bool]", "Change the application config", "cfg");
    }

    /**
     * Called when the command is executed
     *
     * @param cmdInfo
     *     The properties of the command that was passed
     */
    @Override
    public void execute(final CmdInfo cmdInfo) {
        if (cmdInfo.hasValueFlag("fs")) {
            final boolean newFs;
            try {
                newFs = cmdInfo.getFlagValueBoolean("fs");
            } catch (final NumberFormatException e) {
                TmlWrtr.println("The value must either be 'true' or 'false'");
                return;
            }
            if (!newFs) {
                Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
            else {
                Graphics.DisplayMode bestMode = null;
                for (final Graphics.DisplayMode mode : Gdx.graphics.getDisplayModes()) {
                    if (mode.width == Gdx.graphics.getWidth() && mode.height == Gdx.graphics.getHeight()) {
                        if (bestMode == null || bestMode.refreshRate < Gdx.graphics.getDisplayMode().refreshRate) {
                            bestMode = mode;
                        }
                    }
                }
                if (bestMode == null) {
                    bestMode = Gdx.graphics.getDisplayMode();
                }
                Gdx.graphics.setFullscreenMode(bestMode);
            }
        }
        if (cmdInfo.hasValueFlag("vsnc")) {
            final boolean vsnc;
            try {
                vsnc = cmdInfo.getFlagValueBoolean("fs");
            } catch (final NumberFormatException e) {
                TmlWrtr.println("The value must either be 'true' or 'false'");
                return;
            }
            Gdx.graphics.setVSync(vsnc);
        }
    }
}
