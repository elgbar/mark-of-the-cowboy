package no.kh498.motc.terminal.commands.commands;

import no.kh498.motc.MotC;
import no.kh498.motc.terminal.TmlWrtr;
import no.kh498.motc.terminal.commands.AbstractCommand;
import no.kh498.motc.terminal.commands.CmdInfo;
import no.kh498.motc.terminal.commands.CommandMap;

/**
 * @author karl henrik
 * @since 27 jul 2017
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "", "View all commands that are registered", "?");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final CmdInfo cmdInfo) {
        final CommandMap commandMap = MotC.getInstance().getTerminal().getCmdMap();
        for (final AbstractCommand cmd : commandMap.getCommands()) {
            TmlWrtr.println(cmd.getCommand() + ": " + cmd.getDescription());
        }
    }
}
