package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHeroGhost {
    public static final TextureRegion[] leftStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft2.png")))
    };

    public static final TextureRegion[] rightStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight2.png")))
    };

    public static final TextureRegion[] rightFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight1.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostRight/heroGhostRight2.png"))),

    };

    public static final TextureRegion[] leftFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft1.png"))),


        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/GhostLeft/heroGhostLeft2.png"))),

    };
}
