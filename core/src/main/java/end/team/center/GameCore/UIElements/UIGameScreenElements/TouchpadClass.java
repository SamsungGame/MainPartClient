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
    private int pointer = -1;
    public TouchpadClass(float x, float y, boolean isTouchpadActive, String type) {
        super(5, createTouchpadStyle());
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
        int maxPointers = 10;

        // Сбрасываем джойстики, если палец отпущен
        for (TouchpadClass pad : touchpads) {
            if (pad.pointer != -1 && !Gdx.input.isTouched(pad.pointer)) {
                pad.pointer = -1;
                pad.isTouchpadActive = false;
            }
        }

        // Обрабатываем касания
        for (int i = 0; i < maxPointers; i++) {
            if (!Gdx.input.isTouched(i)) continue;

            Vector2 touch = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i)));
            float touchX = touch.x;
            float touchY = touch.y;

            for (TouchpadClass pad : touchpads) {
                boolean isLeftSide = touchX < Gdx.graphics.getWidth() / 2f;
                boolean isRightSide = touchX >= Gdx.graphics.getWidth() / 2f;

                if (pad.pointer == i) {
                    // Палец держит джойстик — не перемещаем его
                    pad.isTouchpadActive = true;
                } else if (pad.pointer == -1) {
                    // Джойстик свободен — можем занять или переместить
                    boolean inBounds = touchX >= pad.x - touchSize / 2 && touchX <= pad.x + touchSize / 2 &&
                        touchY >= pad.y - touchSize / 2 && touchY <= pad.y + touchSize / 2;

                    if (pad.type.equals("move") && isLeftSide) {
                        if (inBounds) {
                            pad.pointer = i;
                            pad.isTouchpadActive = true;
                        } else {
                            // Перемещаем джойстик, только если он не активен
                            if (!pad.isTouchpadActive) {
                                pad.setPosition(touchX - touchSize / 2, touchY - touchSize / 2);
                                pad.x = touchX;
                                pad.y = touchY;
                            }
                        }
                    }

                    if (pad.type.equals("attack") && isRightSide) {
                        if (inBounds) {
                            pad.pointer = i;
                            pad.isTouchpadActive = true;
                        } else {
                            if (!pad.isTouchpadActive) {
                                pad.setPosition(touchX - touchSize / 2, touchY - touchSize / 2);
                                pad.x = touchX;
                                pad.y = touchY;
                            }
                        }
                    }
                }
            }
        }

        // Если нет ни одного касания — сбрасываем все джойстики
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
