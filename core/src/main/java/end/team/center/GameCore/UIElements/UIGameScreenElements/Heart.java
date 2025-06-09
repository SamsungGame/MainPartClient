package end.team.center.GameCore.UIElements.UIGameScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class Heart extends Group {
    private final Texture heartFullTexture;
    private final Texture heartEmptyTexture;
    private final List<Texture> heartAnimationFrames;
    private final List<Image> heartImages = new ArrayList<>();

    private float heartWidth = 80f;
    private float heartHeight = 80f;
    private float spacing = 15f;

    private int maxHearts = 0;
    private int currentHealth = 0;

    private float frameTime = 0f;
    private float frameDuration = 0.2f;
    private int currentFrame = 0;

    public Heart(Texture heartFullTexture, Texture heartEmptyTexture, Texture heartFullBitTexture, int initialHealth) {
        this.heartFullTexture = heartFullTexture;
        this.heartEmptyTexture = heartEmptyTexture;

        this.heartAnimationFrames = new ArrayList<>();
        this.heartAnimationFrames.add(heartFullTexture);
        this.heartAnimationFrames.add(heartFullBitTexture);

        setMaxHearts(initialHealth);
        setCurrentHealth(initialHealth);
    }

    public void setMaxHearts(int newMaxHearts) {
        this.maxHearts = newMaxHearts;

        // Удалить старые
        for (Image img : heartImages) {
            removeActor(img);
        }
        heartImages.clear();

        // Добавить новые
        for (int i = 0; i < maxHearts; i++) {
            Image heart = new Image(heartFullTexture);
            heart.setSize(heartWidth, heartHeight);
            heart.setPosition(i * (heartWidth + spacing), Gdx.graphics.getHeight() - heartHeight * 2); // адаптируй при необходимости
            heartImages.add(heart);
            addActor(heart);
        }

        // Обновить отрисовку текущего здоровья
        updateVisuals();

        setSize(maxHearts * (heartWidth + spacing), heartHeight);
    }

    public void setCurrentHealth(int newHealth) {
        this.currentHealth = Math.max(0, Math.min(newHealth, maxHearts));
        updateVisuals();
    }

    private void updateVisuals() {
        for (int i = 0; i < heartImages.size(); i++) {
            if (i < currentHealth) {
                heartImages.get(i).setDrawable(new Image(heartAnimationFrames.get(currentFrame)).getDrawable());
            } else {
                heartImages.get(i).setDrawable(new Image(heartEmptyTexture).getDrawable());
            }
        }
    }

    public void updateAnimation(float delta) {
        frameTime += delta;
        if (frameTime >= frameDuration) {
            frameTime = 0f;
            currentFrame = (currentFrame + 1) % heartAnimationFrames.size();
            updateVisuals();
        }
    }
}
