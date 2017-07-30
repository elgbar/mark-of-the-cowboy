package no.kh498.motc.terminal.commands;

import no.kh498.motc.terminal.commands.commands.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @author karl henrik
 */
public class CommandMap {

    private HashMap<String, AbstractCommand> commandMap;
    private final HashMap<String, AbstractCommand> aliasMap;

    private static final Pattern SPACE_PATTERN = Pattern.compile(" ", Pattern.LITERAL);

    public CommandMap() {
        this.commandMap = new HashMap<>();
        this.aliasMap = new HashMap<>();

        registerCommands();
    }

    private void registerCommands() {
        register(new ToggleWireCommand());
        register(new PingCommand());
        register(new DoorCommand());
        register(new HelpCommand());
        register(new ApplicationConfigCommand());
    }

    /**
     * Split a string a space character
     *
     * @param str
     *     The string to split
     *
     * @return An array with one entry per word
     */
    public static String[] splitCommand(final String str) {
        return SPACE_PATTERN.split(str);
    }

    public boolean register(final AbstractCommand command) {
        return register(command.getCommand(), command);
    }

    /**
     * Register a command and it's aliases. If the alias is already register then ignore it
     *
     * @param cmdStr
     *     The command string
     * @param command
     *     The command object
     *
     * @return {@code false} if the command with {@code cmdStr} is already register.
     */
    private boolean register(final String cmdStr, final AbstractCommand command) {
        if (this.commandMap.containsKey(cmdStr.toLowerCase())) {
            return false;
        }
        this.commandMap.put(cmdStr.toLowerCase(), command);

        for (final String alias : command.getAliases()) {
            this.aliasMap.putIfAbsent(alias, command);
        }
        return true;
    }

    public boolean dispatch(final String cmdStr) {
        if (cmdStr == null || cmdStr.length() == 0) {
            return false;
        }
        final String[] cmdArray = splitCommand(cmdStr);

        final AbstractCommand cmd = getCommand(cmdArray[0]);
        if (cmd == null) {
            return false;
        }

        final CmdInfo cmdInfo = new CmdInfo(cmdArray);

        cmd.preProcessExecute(cmdInfo);
        return true;
    }

    public void clearCommands() {
        this.commandMap = new HashMap<>();
    }

    private AbstractCommand getCommand(final String cmdStr) {
        final String lowerCmdStr = cmdStr.toLowerCase();
        if (this.commandMap.containsKey(lowerCmdStr)) {
            return this.commandMap.get(lowerCmdStr);
        }
        else if (this.aliasMap.containsKey(lowerCmdStr)) {
            return this.aliasMap.get(lowerCmdStr);
        }
        return null;
    }

    public Collection<AbstractCommand> getCommands() {
        return this.commandMap.values();
    }
}