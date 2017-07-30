package no.kh498.motc.terminal;

import no.kh498.motc.MotC;

/**
 * @author karl henrik
 */
public class TmlWrtr {

    public static void println(final String str) {
        MotC.getInstance().getTerminal().println(str);
    }
}
