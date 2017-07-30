package no.kh498.motc.terminal.commands.commands;

import com.badlogic.gdx.maps.MapLayer;
import no.kh498.motc.MotC;
import no.kh498.motc.terminal.TmlWrtr;
import no.kh498.motc.terminal.commands.AbstractCommand;
import no.kh498.motc.terminal.commands.CmdInfo;

/**
 * @author karl henrik
 */
public class ToggleWireCommand extends AbstractCommand {

    public ToggleWireCommand() {
        super("wire", "[-s]", "Toggle if the wires are viewable or not");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final CmdInfo cmdInfo) {
        final MapLayer layer = MotC.getInstance().getTiledMap().getLayers().get("Wire");
        final boolean isVisible = !layer.isVisible();
        layer.setVisible(isVisible);
        if (!cmdInfo.hasCharFlag('s')) {
            TmlWrtr.println("Wires are now " + (!isVisible ? "not " : "") + "visible");
        }
    }


}
