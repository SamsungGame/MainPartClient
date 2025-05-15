package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Класс TouchpadClass наследуется от Actor и управляет джойстиком.
 */
public class TouchpadClass extends Actor {

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin skin;

    private boolean isTouchpadActive = false;

    public TouchpadClass(float startX, float startY, float touchSize) {
        setBounds(startX - touchSize / 2, startY - touchSize / 2, touchSize, touchSize);

        skin = new Skin();
        skin.add("touchBackground", new Texture("UI/GameUI/Direction/backTouch.png"));
        skin.add("touchKnob", new Texture("UI/GameUI/Direction/knobIMG.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = new TextureRegionDrawable((TextureRegion) skin.getDrawable("touchBackground"));
        touchpadStyle.knob = new TextureRegionDrawable((TextureRegion) skin.getDrawable("touchKnob"));

        touchpadStyle.knob.setMinHeight(100);
        touchpadStyle.knob.setMinWidth(100);

        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (!isTouchpadActive) {
                // Активация джойстика при первом касании
                if (touchX >= getX() && touchX <= getX() + getWidth() &&
                        touchY >= getY() && touchY <= getY() + getHeight()) {
                    isTouchpadActive = true;
                }
            }
        } else {
            isTouchpadActive = false;
        }

        // Синхронизация позиции Touchpad с Actor
        touchpad.setPosition(getX(), getY());
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        touchpad.draw(batch, parentAlpha);
    }

    public float getKnobPercentX() {
        return touchpad.getKnobPercentX();
    }

    public float getKnobPercentY() {
        return touchpad.getKnobPercentY();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        touchpad.setPosition(x, y);
    }

    public void dispose() {
        skin.dispose();
    }
}
