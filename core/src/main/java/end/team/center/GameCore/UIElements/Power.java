package end.team.center.GameCore.UIElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Power extends Actor {
    private Texture trd;
    private Texture aTrd;
    private String desc;

    public Power(Texture imageUp, Texture imageDown, String descp) {
        this.trd = imageUp;
        this.aTrd = imageDown;
        this.desc = descp;
    }

    public void effect() {

    }

    public Texture getUnActiveTexture() {
        return trd;
    }

    public Texture getActiveTexture() {
        return aTrd;
    }

    public String getDescp() {
        return desc;
    }
}
