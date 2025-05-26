package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationsOwl {

    public static final TextureRegion[] leftTurn = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_midle.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_up.png")))
    };

    public static final TextureRegion[] rightTurn = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlRight_midle.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlRight_up.png")))
    };

    public static final TextureRegion[] rightDive = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlRight_down.png")))
    };
    public static final TextureRegion[] leftDive = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_down.png")))
    };
}
