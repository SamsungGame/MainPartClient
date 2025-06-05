package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHeroKnight {

    public static final TextureRegion[] leftStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft_down.png")))
    };

    public static final TextureRegion[] rightStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KKnightRight/heroNighRight.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight_down.png")))
    };

    public static final TextureRegion[] rightFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight.png")))
    };

    public static final TextureRegion[] leftFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/NightLeft/heroNighLeft.png")))

    };
}
