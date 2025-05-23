package end.team.center.GameCore.UIElements.UIGameScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Heart extends Group {
    private final Texture heartFullTexture;
    private final Texture heartEmptyTexture;
    private final List<Image> heartImages;
    private final List<Texture> heartAnimationFrames;
    private float hh = 80;
    private float wh = 80;
    private float space = 15;

    private int maxHearts;
    private int currentHealth;

    private float frameTime = 0f;
    private float frameDuration = 0.2f;
    private int currentFrame = 0;

    public Heart(Texture heartFullTexture, Texture heartEmptyTexture, Texture heartFullTextureBit, int initialHealth) {
        this.heartFullTexture = heartFullTexture;
        this.heartEmptyTexture = heartEmptyTexture;
        this.heartImages = new ArrayList<>();
        this.maxHearts = initialHealth;

        heartAnimationFrames = new ArrayList<>();
        heartAnimationFrames.add(new Texture(heartFullTexture.getTextureData()));
        heartAnimationFrames.add(new Texture(heartFullTextureBit.getTextureData()));

        for (int i = 0; i < maxHearts; i++) {
            Image heart = new Image(heartFullTexture);
            heart.setSize(hh, wh);
            heart.setPosition(i * (wh + space), Gdx.graphics.getHeight() - hh * 2);
            heartImages.add(heart);
            this.addActor(heart);
        }

        setSize(maxHearts * 70f, 64);

    }

    public void updateHealth(int currentHealth) {
        this.currentHealth = currentHealth;
        updateAnimation(0);
    }
    public void updateAnimation(float delta) {
        frameTime += delta;
        if (frameTime >= frameDuration) {
            frameTime = 0;
            currentFrame = (currentFrame + 1) % heartAnimationFrames.size();
        }

        for (int i = 0; i < heartImages.size(); i++) {
            if (i < currentHealth) {
                heartImages.get(i).setDrawable(new Image(heartAnimationFrames.get(currentFrame)).getDrawable());
            } else {
                heartImages.get(i).setDrawable(new Image(heartEmptyTexture).getDrawable());
            }
        }
    }


}
