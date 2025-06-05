package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationGhost {

    public static TextureRegion[] ghostWalkLeft = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostWalkLeft.png")))
    };
    public static TextureRegion[] ghostWalkRight = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostWalkRight.png")))
    };

    public static TextureRegion[] ghostAttack = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostAttackRight.png")))
    };

    public static TextureRegion[] ammo = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ammo.png")))
    };
}
