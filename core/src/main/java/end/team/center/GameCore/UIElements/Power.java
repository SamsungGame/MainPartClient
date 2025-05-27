package end.team.center.GameCore.UIElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Power extends Actor {
    private Texture trd;

    public Power(Texture imageUp) {
        this.trd = imageUp;
    }

    public void effect() {

    }

    public Texture getTexture() {
        return trd;
    }
}
