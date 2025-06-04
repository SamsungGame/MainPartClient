package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHeroKnight {

    public static final TextureRegion[] leftStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife_down.png")))
    };

    public static final TextureRegion[] rightStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife_down.png")))
    };

    public static final TextureRegion[] rightFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife_down.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife2_down.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife_down.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife1_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRightKnife1_down.png")))
    };

    public static final TextureRegion[] leftFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife_down.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife2_down.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife_down.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife1_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife1_down.png"))),
    };
}
