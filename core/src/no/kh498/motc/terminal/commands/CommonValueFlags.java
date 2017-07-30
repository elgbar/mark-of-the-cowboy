package no.kh498.motc.terminal.commands;

import no.kh498.motc.MotC;
import no.kh498.motc.objects.InteractiveDevice;
import no.kh498.motc.objects.network.Network;
import no.kh498.motc.terminal.TmlWrtr;

/**
 * @author karl henrik
 * @since 29 jul 2017
 */
public class CommonValueFlags {

    /**
     * @param cmdInfo
     *     The cmd info instance to look for the ID flag
     * @param noValueFlag
     *     String to display if there is no ID flag
     *
     * @return The {@link InteractiveDevice} associated with the specified ID, or {@code null} if not found
     */
    public static InteractiveDevice getID(final CmdInfo cmdInfo, final String noValueFlag) {
        final Network network = MotC.getInstance().getNetwork();

        if (cmdInfo.hasValueFlag("ID")) {
            final int id;
            try {
                id = cmdInfo.getFlagValueInteger("ID");
            } catch (final NumberFormatException e) {
                TmlWrtr.println("The ID must be an integer.");
                return null;
            }
            final InteractiveDevice device = network.get(id);
            if (device != null) {
                return device;
            }
        }
        TmlWrtr.println(noValueFlag);
        return null;
    }
}
