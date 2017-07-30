package no.kh498.motc.terminal.commands.commands;

import no.kh498.motc.objects.InteractiveDevice;
import no.kh498.motc.objects.Type;
import no.kh498.motc.terminal.TmlWrtr;
import no.kh498.motc.terminal.commands.AbstractCommand;
import no.kh498.motc.terminal.commands.CmdInfo;
import no.kh498.motc.terminal.commands.CommonValueFlags;

/**
 * @author karl henrik
 * @since 29 jul 2017
 */
public class DoorCommand extends AbstractCommand {

    public DoorCommand() {
        super("door", "--ID ID", "Handle doors");
    }

    /**
     * Called when the command is executed
     *
     * @param cmdInfo
     *     The properties of the command that was passed
     */
    @Override
    public void execute(final CmdInfo cmdInfo) {
        final InteractiveDevice device = CommonValueFlags.getID(cmdInfo, "Usage: " + getFullUsage());
        if (device == null) {
            return;
        }
        if (device.getType() == Type.DOOR) {
            final boolean visible = !device.isVisible();
            device.setVisible(visible);
            TmlWrtr.println("Door \"" + device.getName() + "\"'s visibility is now " + visible);
        }
        else {
            TmlWrtr.println("This device is not a door!");
        }
    }
}
