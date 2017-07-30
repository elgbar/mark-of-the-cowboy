package no.kh498.motc.terminal.commands;


import no.kh498.motc.terminal.TmlWrtr;

import java.util.Arrays;

/**
 * @author karl henrik
 */
public abstract class AbstractCommand {

    private final String cmd;
    private final String[] aliases;

    //config
    private final String description;
    private final String usage;

    public AbstractCommand(final String cmd, final String usage, final String description, final String... aliases) {
        if (cmd == null || description == null || usage == null) {
            throw new IllegalArgumentException("Neither of the cmd, desc or usage can be null");
        }
        this.cmd = cmd;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    void preProcessExecute(final CmdInfo cmdInfo) {
        if (cmdInfo.hasCharFlag('?')) {
            help();
            return;
        }
        execute(cmdInfo);
    }

    /**
     * Called when the command is executed
     *
     * @param cmdInfo
     *     The properties of the command that was passed
     */
    public abstract void execute(CmdInfo cmdInfo);

    @SuppressWarnings("WeakerAccess")
    public void help() {
        TmlWrtr.println(getFullUsage());
        if (this.aliases.length > 0) {
            TmlWrtr.println("Aliases: " + Arrays.toString(this.aliases));
        }
        else {
            TmlWrtr.println("");
        }
        TmlWrtr.println(this.description);
    }

    public String getCommand() {
        return this.cmd;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUsage() {
        return this.usage;
    }

    public String getFullUsage() {
        return this.cmd.toUpperCase() + " " + this.usage;
    }
}
