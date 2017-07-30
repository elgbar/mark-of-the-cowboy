package no.kh498.motc.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import no.kh498.motc.terminal.commands.CommandMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author karl henrik
 */
public class Terminal implements Disposable {

    private final Texture backgroundTexture;
    private final BitmapFont font;
    private final GlyphLayout layout;

    private int width;
    private float startX;
    private int height;

    private final StringBuilder currInput = new StringBuilder();

    private final Queue<String> history;
    private int maxHistoryLines;

    private final ArrayList<String> cmdHistory;
    private int currHistoryIndex;

    private static final float TERMINAL_WINDOW_SIZE = 2f / 3f;
    private static final char CMD_PREFIX = '>';
    private static final char CMD_SUFFIX = '_';

    private final CommandMap cmdMap;

    public Terminal() {
        this.width = Gdx.graphics.getWidth();
        this.startX = this.width * TERMINAL_WINDOW_SIZE;
        this.height = Gdx.graphics.getHeight();

        final Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(32 / 255f, 23 / 255f, 37 / 255f, 1);
        pixmap.fill();
        this.backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        final FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("fonts" + File.separator + "UbuntuMono-R.ttf"));
        final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 17;
        parameter.minFilter = Texture.TextureFilter.Linear;
        this.font = generator.generateFont(parameter);
        generator.dispose();
        this.layout = new GlyphLayout();

        this.maxHistoryLines = this.height / (int) this.font.getLineHeight();

//        this.history = Collections.asLifoQueue(new ArrayDeque<>());
        this.history = new LinkedList<>();
        this.cmdHistory = new ArrayList<>();

//        System.out.println("history.length = " + this.history.size());

        this.cmdMap = new CommandMap();
    }


    void println(final String line) {
        this.history.add(line);
        if (this.history.size() > this.maxHistoryLines) {
            this.history.remove();
        }
    }


    /**
     * Process enter and add currInput it to the terminal window
     */
    void enter(final int keycode) {
        if (keycode == Input.Keys.ENTER && this.currInput.length() != 0) {
            final String input = this.currInput.toString();

            println(CMD_PREFIX + input);

            if (this.cmdHistory.size() == 0 || !input.equals(this.cmdHistory.get(this.cmdHistory.size() - 1))) {
                this.cmdHistory.add(input);
            }

            final boolean success = this.cmdMap.dispatch(input);

            if (!success) {
                println("Unknown command '" + CommandMap.splitCommand(input)[0] + "'");
            }
            this.currInput.setLength(0);
            this.currHistoryIndex = 0;

        }
        else if (keycode == Input.Keys.UP && this.cmdHistory.size() > this.currHistoryIndex) {
            this.currInput.setLength(0);
            this.currHistoryIndex++;
            this.currInput.append(this.cmdHistory.get(this.cmdHistory.size() - this.currHistoryIndex));
        }
        else if (keycode == Input.Keys.DOWN) {
            this.currInput.setLength(0);
            if (this.currHistoryIndex > 1) {
                this.currHistoryIndex--;
                this.currInput.append(this.cmdHistory.get(this.cmdHistory.size() - this.currHistoryIndex));
            }
        }
    }

    /**
     * @param c
     *     character to check
     *
     * @return if a certain character is allowed to be printed
     */
    private boolean isAllowedChar(final char c) {
        final int cInt = (int) c;
        return cInt != 0 && cInt != 13;
        //NOTE: only tested on windows 10
        //0 = Not printable (eg a modifier key like shift)
        //13 = Enter
    }

    void type(final char c) {
        final int backspaceKey = 8;
        if ((int) c == backspaceKey) {
            if (this.currInput.length() > 0) {
                this.currInput.setLength(this.currInput.length() - 1);
            }
            return;
        }
        this.layout.setText(this.font, tmlInput());
        if (isAllowedChar(c) && this.currInput.length() < this.layout.width) {
            this.currInput.append(c);
        }
//        System.out.println(c + " | " + (int) c + " | " + isAllowedChar(c));
    }

    public void resize(final int width, final int height) {
        this.width = width;
        this.height = height;

        this.startX = width * TERMINAL_WINDOW_SIZE;
        this.maxHistoryLines = height / (int) this.font.getLineHeight();
        System.out.println("width = " + width);
        System.out.println("height = " + height);
        System.out.println("startX = " + this.startX);
    }


    public void render(final Batch batch) {
        batch.draw(this.backgroundTexture, this.startX, 0, this.width, this.height);

        final Iterator iterator = this.history.iterator();
        int i = this.history.size() + 1;
        while (iterator.hasNext()) {
            final float printY = this.font.getLineHeight() * i;
            this.font.draw(batch, iterator.next().toString(), this.startX, printY);
            i--;
        }
//        this.layout.setText(this.font, );
        this.font.draw(batch, tmlInput(), this.startX, this.font.getLineHeight());
    }

    private String tmlInput() {
        return CMD_PREFIX + this.currInput.toString() + CMD_SUFFIX;
    }

    public CommandMap getCmdMap() {
        return this.cmdMap;
    }

    @Override
    public void dispose() {
        this.font.dispose();
        this.backgroundTexture.dispose();
    }
}
