package end.team.center.GameCore.Library.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHeroCyber {

    public static final TextureRegion[] leftStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft_down.png")))
    };

    public static final TextureRegion[] rightStay = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight_down.png")))
    };

    public static final TextureRegion[] rightFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight1_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberRight/cyberRight.png")))
    };

    public static final TextureRegion[] leftFrames = new TextureRegion[] {
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft1_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft.png"))),

        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft2_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft_down.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/CyberLeft/cyberLeft.png")))

    };
}
