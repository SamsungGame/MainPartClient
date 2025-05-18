package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    public void TouchpadLogic() {


                if(touchpads.get(0).type.equals("move")) {
                    if (Gdx.input.isTouched()) {
                        float touchX = Gdx.input.getX();
                        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                        if (!touchpads.get(0).isTouchpadActive) {
                            if (touchX >= this.x - touchSize / 2 && touchX <= this.x + touchSize / 2 &&
                                touchY >= this.y - touchSize / 2 && touchY <= this.y + touchSize / 2) {
                                touchpads.get(0).isTouchpadActive = true;
                            } else if(touchX < (float) Gdx.graphics.getWidth() /2-touchSize/2){
                                touchpads.get(0).x = touchX;
                                touchpads.get(0).y = touchY;
                            }
                        }
                    }else {
                        touchpads.get(0).isTouchpadActive = false;
                    }

                }

        if(touchpads.get(1).type.equals("attack")) {
            if (Gdx.input.isTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (!touchpads.get(1).isTouchpadActive) {
                    if (touchX >= this.x - touchSize / 2 && touchX <= this.x + touchSize / 2 &&
                        touchY >= this.y - touchSize / 2 && touchY <= this.y + touchSize / 2) {
                        touchpads.get(1).isTouchpadActive = true;
                    } else if(touchX > (float) Gdx.graphics.getWidth() /2+touchSize/2){
                        touchpads.get(1).x = touchX;
                        touchpads.get(1).y = touchY;
                    }
                }
            }else {
                touchpads.get(1).isTouchpadActive = false;
            }

        }
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
