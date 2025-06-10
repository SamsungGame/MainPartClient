package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHeroCyber {

    public static final TextureRegion[] leftStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft_down.png")))
    };

    public static final TextureRegion[] rightStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight_down.png")))
    };

    public static final TextureRegion[] rightFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight1_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderRight/cyberRight.png")))
    };

    public static final TextureRegion[] leftFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft1_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyderLeft/cyberLeft.png")))

    };
}
