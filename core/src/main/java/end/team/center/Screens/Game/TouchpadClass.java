package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


// Класс TouchpadClass управляет джойстиками.

public class TouchpadClass extends Touchpad {
    public float touchSize = 300;
    private float x;
    private float y;
    private boolean isTouchpadActive;
    float xp = this.x;
    float yp = this.y;

    public TouchpadClass(float x, float y, boolean isTouchpadActive) {
        super(10, createTouchpadStyle());
        setBounds(x, y, touchSize, touchSize);
        this.isTouchpadActive = isTouchpadActive;
    }

    private static TouchpadStyle createTouchpadStyle() {
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
    public void TouchpadLogic() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (!isTouchpadActive) {
                if (touchX >= x - touchSize / 2 && touchX <= x + touchSize / 2 &&
                        touchY >= y - touchSize / 2 && touchY <= y + touchSize / 2) {
                    isTouchpadActive = true;
                } else {
                    x = touchX;
                    y = touchY;
                }
            }
        } else {
            isTouchpadActive = false;
        }
    }

    public void touchpadSetBounds(TouchpadClass touchpad) {

        if(xp != this.x && yp != this.y) {
            touchpad.setBounds(this.x-touchSize/2, this.y-touchSize/2, touchSize, touchSize);
            xp = x+1;
            yp = y+1;
        }


    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
    }
}
