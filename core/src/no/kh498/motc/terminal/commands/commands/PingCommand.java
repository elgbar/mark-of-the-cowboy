package no.kh498.motc.terminal.commands.commands;

import com.badlogic.gdx.math.Vector2;
import no.kh498.motc.MotC;
import no.kh498.motc.objects.InteractiveDevice;
import no.kh498.motc.objects.network.Network;
import no.kh498.motc.terminal.TmlWrtr;
import no.kh498.motc.terminal.commands.AbstractCommand;
import no.kh498.motc.terminal.commands.CmdInfo;
import no.kh498.motc.terminal.commands.CommonValueFlags;

import java.util.HashMap;
import java.util.Map;

/**
 * @author karl henrik
 * @since 28 jul 2017
 */
public class PingCommand extends AbstractCommand {

    public PingCommand() {
        super("ping", "[--ID ID]", "Ping all devices on the network");
    }

    /**
     * Called when the command is executed
     *
     * @param cmdInfo
     *     The properties of the command that was passed
     */
    @Override
    public void execute(final CmdInfo cmdInfo) {
        final Network network = MotC.getInstance().getNetwork();

        if (cmdInfo.hasValueFlag("ID")) {
            final InteractiveDevice device = CommonValueFlags.getID(cmdInfo, "Usage: " + getFullUsage());
            if (device != null) {
                TmlWrtr.println("Found device with the id " + cmdInfo.getFlagValue("ID"));
                TmlWrtr.println("Name: " + device.getName() + " Type: " + device.getType());
            }
            else {
                TmlWrtr.println("Could not find any devices with the id '" + cmdInfo.getFlagValue("ID") + '\'');
            }
            return;
        }
        final HashMap<Integer, InteractiveDevice> map = network.getMap();
        final Vector2 playerLoc = MotC.getInstance().getPlayer().getLocation();
        for (final Map.Entry<Integer, InteractiveDevice> entrySet : map.entrySet()) {
            final float dis = calculateDistance(entrySet.getValue(), playerLoc);
            TmlWrtr.println(entrySet.getValue().getName() + ": " + dis);
        }
    }

    private float calculateDistance(final InteractiveDevice device, final Vector2 origin) {
        return device.getLocation().dst(origin);
    }
}
