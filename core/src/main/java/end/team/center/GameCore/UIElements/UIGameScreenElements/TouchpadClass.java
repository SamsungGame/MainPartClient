package end.team.center.GameCore.UIElements.UIGameScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;


// Класс TouchpadClass управляет джойстиками.

public class TouchpadClass extends Touchpad {

    public static List<TouchpadClass> touchpads = new ArrayList<>();
    public float touchSize = 300;
    private float x;
    private float y;
    private boolean isTouchpadActive;
    float xp = this.x;
    float yp = this.y;
    private String type;
    public TouchpadClass(float x, float y, boolean isTouchpadActive, String type) {
        super(10, createTouchpadStyle());
        setBounds(x, y, touchSize, touchSize);
        this.isTouchpadActive = isTouchpadActive;
        this.type = type;
        touchpads.add(this);
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


    public void TouchpadLogic(Stage stage) {
        if (Gdx.input.isTouched()) {
            Vector2 stageTouch = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            float touchX = stageTouch.x;
            float touchY = stageTouch.y;

            TouchpadClass movePad = touchpads.get(0);
            if (movePad.type.equals("move")) {
                if (!movePad.isTouchpadActive) {
                    if (touchX >= movePad.x - touchSize / 2 && touchX <= movePad.x + touchSize / 2 &&
                        touchY >= movePad.y - touchSize / 2 && touchY <= movePad.y + touchSize / 2) {
                        movePad.isTouchpadActive = true;
                    } else if (touchX < (float) Gdx.graphics.getWidth() / 2 - touchSize / 2) {
                        movePad.setPosition(touchX - touchSize / 2, touchY - touchSize / 2);
                        movePad.x = touchX;
                        movePad.y = touchY;
                    }
                }
            }

            TouchpadClass attackPad = touchpads.get(1);
            if (attackPad.type.equals("attack")) {
                if (!attackPad.isTouchpadActive) {
                    if (touchX >= attackPad.x - touchSize / 2 && touchX <= attackPad.x + touchSize / 2 &&
                        touchY >= attackPad.y - touchSize / 2 && touchY <= attackPad.y + touchSize / 2) {
                        attackPad.isTouchpadActive = true;
                    } else if (touchX > (float) Gdx.graphics.getWidth() / 2 + touchSize / 2) {
                        attackPad.setPosition(touchX - touchSize / 2, touchY - touchSize / 2);
                        attackPad.x = touchX;
                        attackPad.y = touchY;
                    }
                }
            }

        } else {
            touchpads.get(0).isTouchpadActive = false;
            touchpads.get(1).isTouchpadActive = false;
        }
    }

    public boolean isTouchpadActive() {
        return isTouchpadActive;
    }
    public void touchpadSetBounds() {

        if(xp != this.x && yp != this.y) {
            setBounds(this.x-touchSize/2, this.y-touchSize/2, touchSize, touchSize);
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
