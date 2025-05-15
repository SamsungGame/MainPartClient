package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


// Класс TouchpadClass управляет джойстиком.

public class TouchpadClass extends Touchpad {

    public TouchpadClass(float x, float y, float size) {
        super(10, createTouchpadStyle());
        setSize(size, size);
        setBounds(x, y, size, size);
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
    }
}
