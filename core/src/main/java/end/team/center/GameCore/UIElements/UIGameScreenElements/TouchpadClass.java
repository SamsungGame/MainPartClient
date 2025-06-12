package end.team.center.GameCore.UIElements.UIGameScreenElements;

import static end.team.center.Screens.Game.GameScreen.pauseButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

public class TouchpadClass extends Touchpad {

    public static List<TouchpadClass> touchpads = new ArrayList<>();
    public float touchSize = 300;
    public float x = 0;
    public float y = 0;
    public boolean isTouchpadActive;
    private float xp = this.x;
    private float yp = this.y;
    private String type;
    private int pointer = -1;

    private final Rectangle pauseButtonBounds = new Rectangle(10, Gdx.graphics.getHeight() - 325, 100, 125);

    public TouchpadClass(float x, float y, boolean isTouchpadActive, String type) {
        super(5, createTouchpadStyle());
        setBounds(x, y, touchSize, touchSize);
        this.x = x;
        this.y = y;
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
        int maxPointers = 10;

        // Сброс указателей, если палец отпущен
        for (TouchpadClass pad : touchpads) {
            if (pad.pointer != -1 && !Gdx.input.isTouched(pad.pointer)) {
                pad.pointer = -1;
                pad.isTouchpadActive = false;
            }
        }

        // Обработка новых касаний
        for (int i = 0; i < maxPointers; i++) {
            if (!Gdx.input.isTouched(i)) continue;

            Vector2 touch = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i)));
            float touchX = touch.x;
            float touchY = touch.y;

            for (TouchpadClass pad : touchpads) {
                boolean isLeftSide = touchX < Gdx.graphics.getWidth() / 2f;
                boolean isRightSide = touchX >= Gdx.graphics.getWidth() / 2f;

                if (pad.pointer == i) {
                    pad.isTouchpadActive = true;
                    continue;
                }

                if (pad.pointer == -1 && !pauseButtonBounds.contains(touchX, touchY)) {
                    boolean inBounds = touchX >= pad.x - pad.touchSize / 2 && touchX <= pad.x + pad.touchSize / 2 &&
                        touchY >= pad.y - pad.touchSize / 2 && touchY <= pad.y + pad.touchSize / 2;

                    if (pad.type.equals("move") && isLeftSide || pad.type.equals("attack") && isRightSide) {
                        if (inBounds) {
                            pad.pointer = i;
                            pad.isTouchpadActive = true;
                        } else if (!pad.isTouchpadActive) {
                            // Перемещаем и сразу активируем
                            pad.setPosition(touchX - pad.touchSize / 2, touchY - pad.touchSize / 2);
                            pad.x = touchX;
                            pad.y = touchY;
                            pad.pointer = i;
                            pad.isTouchpadActive = true;
                        }
                    }
                }
            }
        }

        // Если вообще нет касаний — сбросить все
        if (!Gdx.input.isTouched()) {
            for (TouchpadClass pad : touchpads) {
                pad.pointer = -1;
                pad.isTouchpadActive = false;
            }
        }
    }

    public boolean isTouchpadActive() {
        return isTouchpadActive;
    }

    public void touchpadSetBounds() {
        if (xp != this.x || yp != this.y) {
            setBounds(this.x - touchSize / 2, this.y - touchSize / 2, touchSize, touchSize);
            xp = x + 1;
            yp = y + 1;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        // Если нужно, освободи ресурсы (например, текстуры)
    }
}
