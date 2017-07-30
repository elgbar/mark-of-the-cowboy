package no.kh498.motc.terminal;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import no.kh498.motc.MotC;

/**
 * @author karl henrik
 */
public class InputListener implements InputProcessor {

    /**
     * Called when a key was pressed
     *
     * @param keycode
     *     one of the constants in {@link Input.Keys}
     *
     * @return whether the enter was processed
     */
    @Override
    public boolean keyDown(final int keycode) {
        MotC.getInstance().getTerminal().enter(keycode);
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode
     *     one of the constants in {@link Input.Keys}
     *
     * @return whether the enter was processed
     */
    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character
     *     The character
     *
     * @return whether the enter was processed
     */
    @Override
    public boolean keyTyped(final char character) {
        MotC.getInstance().getTerminal().type(character);
        return true;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link
     * Input.Buttons#LEFT} on iOS.
     *
     * @param screenX
     *     The x coordinate, origin is in the upper left corner
     * @param screenY
     *     The y coordinate, origin is in the upper left corner
     * @param pointer
     *     the pointer for the event.
     * @param button
     *     the button
     *
     * @return whether the enter was processed
     */
    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link
     * Input.Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer
     *     the pointer for the event.
     * @param button
     *     the button   @return whether the enter was processed
     */
    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer
     *     the pointer for the event.  @return whether the enter was processed
     */
    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     *
     * @return whether the enter was processed
     */
    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount
     *     the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     *
     * @return whether the enter was processed.
     */
    @Override
    public boolean scrolled(final int amount) {
        return false;
    }
}
