package end.team.center.GameCore.UIElements.UIGameScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashSet;
import java.util.Set;

public class TouchpadClass extends Touchpad {

    public static final float TOUCH_SIZE = 300;
    public static TouchpadClass movePad;
    public static TouchpadClass attackPad;

    private float centerX;
    private float centerY;
    private boolean active;
    private int pointer = -1;
    private final String type;

    public TouchpadClass(float x, float y, String type) {
        super(10, createStyle());
        this.centerX = x;
        this.centerY = y;
        this.type = type;
        this.active = false;

        setBounds(centerX - TOUCH_SIZE / 2, centerY - TOUCH_SIZE / 2, TOUCH_SIZE, TOUCH_SIZE);

        if (type.equals("move")) movePad = this;
        if (type.equals("attack")) attackPad = this;
    }

    private static TouchpadStyle createStyle() {
        Skin skin = new Skin();
        skin.add("background", new Texture("UI/GameUI/Direction/backTouch.png"));
        skin.add("knob", new Texture("UI/GameUI/Direction/knobIMG.png"));

        TouchpadStyle style = new TouchpadStyle();
        style.background = new TextureRegionDrawable((TextureRegionDrawable) skin.getDrawable("background"));
        style.knob = new TextureRegionDrawable((TextureRegionDrawable) skin.getDrawable("knob"));
        style.knob.setMinWidth(100);
        style.knob.setMinHeight(100);
        return style;
    }

    public static void handleTouchpads(Stage stage) {
        Set<Integer> usedPointers = new HashSet<>();

        // Reset inactive pads if touch is released
        for (TouchpadClass pad : new TouchpadClass[]{movePad, attackPad}) {
            if (pad.pointer != -1 && !Gdx.input.isTouched(pad.pointer)) {
                pad.pointer = -1;
                pad.active = false;
                pad.resetPosition();
            }
        }

        for (int i = 0; i < 10; i++) {
            if (!Gdx.input.isTouched(i)) continue;

            Vector2 touch = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i)));
            float tx = touch.x;
            float ty = touch.y;
            float screenHalf = Gdx.graphics.getWidth() / 2f;

            boolean left = tx < screenHalf;
            boolean right = tx >= screenHalf;

            TouchpadClass targetPad = left ? movePad : attackPad;

            if (targetPad.pointer == -1 && !usedPointers.contains(i)) {
                targetPad.setPosition(tx - TOUCH_SIZE / 2, ty - TOUCH_SIZE / 2);
                targetPad.centerX = tx;
                targetPad.centerY = ty;
                targetPad.pointer = i;
                targetPad.active = true;
                usedPointers.add(i);
            } else if (targetPad.pointer == i) {
                targetPad.active = true;
                usedPointers.add(i);
            }
        }

        // No touches — reset all
        if (!Gdx.input.isTouched()) {
            for (TouchpadClass pad : new TouchpadClass[]{movePad, attackPad}) {
                pad.pointer = -1;
                pad.active = false;
                pad.resetPosition();
            }
        }
    }

    private void resetPosition() {
        setBounds(centerX - TOUCH_SIZE / 2, centerY - TOUCH_SIZE / 2, TOUCH_SIZE, TOUCH_SIZE);
    }

    public boolean isTouchpadActive() {
        return active;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        // Dispose resources if needed
    }
}
