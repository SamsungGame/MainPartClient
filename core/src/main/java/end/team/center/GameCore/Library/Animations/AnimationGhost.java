package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationGhost {

    public static TextureRegion[] ghostWalk = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostWalk.png")))
    };

    public static TextureRegion[] ghostAttack = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostAttack.png")))
    };

    public static TextureRegion[] ammo = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ammo.png")))
    };
}
