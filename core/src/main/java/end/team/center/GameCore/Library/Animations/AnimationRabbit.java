package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationRabbit {

    public static TextureRegion[] walkRight = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Hare/hareRight.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Hare/hareRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Hare/hareRight_up.png")))
    };

    public static TextureRegion[] walkLeft = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Hare/hareLeft.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Hare/hareLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Hare/hareLeft_up.png")))
    };
}
