package no.kh498.motc.terminal.commands;


import java.util.*;
import java.util.regex.Pattern;

/**
 * @author karl henrik
 */
@SuppressWarnings({"unused", "SameParameterValue"})
public class CmdInfo {

    private final Set<Character> flags;
    private final Map<String, String> valueFlags;
    private final String[] cmdAndArgs;

    private static final Pattern VALUE_FLAG = Pattern.compile("^--[a-zA-Z0-9-]+$");
    private static final Pattern FLAG = Pattern.compile("^-[a-zA-Z*?]$");

    public CmdInfo(final String[] cmdAndArgs) {
        this.flags = new HashSet<>();
        this.valueFlags = new HashMap<>();

        //This is a black box, I have no idea how this works, but it does get the flags
        // and value flags so I am happy.. again sorry
        // from https://raw.githubusercontent.com/CitizensDev/CitizensAPI/0af66586ec71de905660fa8d9b1f363dd0798afe/src/main/java/net/citizensnpcs/api/command/CommandContext.java
        int i = 1;
        for (; i < cmdAndArgs.length; i++) {
            // initial pass for quotes
            cmdAndArgs[i] = cmdAndArgs[i].trim();
            if (cmdAndArgs[i].length() != 0 && (cmdAndArgs[i].charAt(0) == '\'' || cmdAndArgs[i].charAt(0) == '"')) {
                final char quote = cmdAndArgs[i].charAt(0);
                final StringBuilder quoted = new StringBuilder(cmdAndArgs[i].substring(1)); // remove initial quote
                if (quoted.length() > 0 && quoted.charAt(quoted.length() - 1) == quote) {
                    cmdAndArgs[i] = quoted.substring(0, quoted.length() - 1);
                    continue;
                }
                for (int inner = i + 1; inner < cmdAndArgs.length; inner++) {
                    if (cmdAndArgs[inner].isEmpty()) {
                        continue;
                    }
                    final String test = cmdAndArgs[inner].trim();
                    quoted.append(" ").append(test);
                    if (test.charAt(test.length() - 1) == quote) {
                        cmdAndArgs[i] = quoted.substring(0, quoted.length() - 1);
                        // remove ending quote
                        for (int j = i + 1; j <= inner; ++j) {
                            cmdAndArgs[j] = ""; // collapse previous
                        }
                        break;
                    }
                }
            }
        }
        for (i = 1; i < cmdAndArgs.length; ++i) {
            // second pass for flags
            final int length = cmdAndArgs[i].length();
            if (length == 0) {
                continue;
            }
            if (i + 1 < cmdAndArgs.length && length > 2 && VALUE_FLAG.matcher(cmdAndArgs[i]).matches()) {
                int inner = i + 1;
                while (cmdAndArgs[inner].length() == 0) {
                    // later cmdAndArgs may have been quoted
                    if (++inner >= cmdAndArgs.length) {
                        inner = -1;
                        break;
                    }
                }

                if (inner != -1) {
                    this.valueFlags.put(cmdAndArgs[i].toLowerCase().substring(2), cmdAndArgs[inner]);
                    cmdAndArgs[i] = "";
                    cmdAndArgs[inner] = "";
                }
            }
            else if (FLAG.matcher(cmdAndArgs[i]).matches()) {
                for (int k = 1; k < cmdAndArgs[i].length(); k++) {
                    this.flags.add(cmdAndArgs[i].charAt(k));
                }
                cmdAndArgs[i] = "";
            }
        }
        final List<String> copied = new ArrayList<>();
        for (final String arg : cmdAndArgs) {
            System.out.println("arg.trim() = '" + arg.trim() + '\'');
            if (arg.trim().isEmpty()) {
                continue;
            }
            copied.add(arg.trim());
        }
        this.cmdAndArgs = copied.toArray(new String[copied.size()]);

        System.out.println("flags = " + Arrays.toString(this.flags.toArray()));
        System.out.println("valueFlags = " + this.valueFlags.toString());
        System.out.println("cmdAndArgs = " + Arrays.toString(this.cmdAndArgs));
    }

    public boolean hasCharFlag(final char flag) {
        return this.flags.contains(flag);
    }

    public boolean hasValueFlag(final String flag) {
        return this.valueFlags.containsKey(flag.toLowerCase());
    }

    public String getFlagValue(final String flag) {
        return this.valueFlags.get(flag.toLowerCase());
    }

    public int getFlagValueInteger(final String flag) throws NumberFormatException {
        return Integer.parseInt(getFlagValue(flag));
    }

    public int getFlagValueInteger(final String flag, final int defaultReturn) {
        try {
            return Integer.parseInt(getFlagValue(flag));
        } catch (final NumberFormatException e) {
            return defaultReturn;
        }
    }

    public double getFlagValueDouble(final String flag) throws NumberFormatException {
        return Double.parseDouble(getFlagValue(flag));
    }

    public double getFlagValueDouble(final String flag, final double defaultReturn) {
        try {
            return Double.parseDouble(getFlagValue(flag));
        } catch (final NumberFormatException e) {
            return defaultReturn;
        }
    }

    public float getFlagValueFloat(final String flag) throws NumberFormatException {
        return Float.parseFloat(getFlagValue(flag));
    }

    public float getFlagValueFloat(final String flag, final float defaultReturn) {
        try {
            return Float.parseFloat(getFlagValue(flag));
        } catch (final NumberFormatException e) {
            return defaultReturn;
        }
    }

    public long getFlagValueLong(final String flag) throws NumberFormatException {
        return Long.parseLong(getFlagValue(flag));
    }

    public long getFlagValueLong(final String flag, final long defaultReturn) {
        try {
            return Long.parseLong(getFlagValue(flag));
        } catch (final NumberFormatException e) {
            return defaultReturn;
        }
    }

    public boolean getFlagValueBoolean(final String flag) {
        return Boolean.parseBoolean(getFlagValue(flag));
    }

    /**
     * @return A copy of the arguments
     */
    public String[] getArguments() {
        return Arrays.copyOfRange(this.cmdAndArgs, 1, this.cmdAndArgs.length);
    }

    public int getArgsLength() {
        return this.cmdAndArgs.length - 1;
    }

    public int getLength() {
        return this.cmdAndArgs.length;
    }

    /**
     * @return The command
     */
    public String getCommand() {
        return this.cmdAndArgs[0];
    }

    public Set<Character> getFlags() {
        return this.flags;
    }

    public Map<String, String> getValueFlags() {
        return this.valueFlags;
    }
}
