package end.team.center.GameCore.HelpsClass;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class PowerElement extends ImageButton {
    public boolean isSelected = false;
    private String name, description;
    private boolean animated = false, sAnimated = false;
    private float scale = 0.01f, elapsedTime = 0;
    private int rotation = 3, duration = 2;

    public PowerElement(Skin skin) {
        super(skin);
    }
    public PowerElement(Skin skin, String name) {
        super(skin);
        this.name = name;
    }
    public PowerElement(Skin skin, String name, String description) {
        super(skin);
        this.name = name;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void startAnimation() {
        animated = true;
    }
    public void startSmallAnimation() {
        sAnimated = true;
    }

    public boolean goAnimation(Sprite portal, float deltaTime) {
        if (animated) {
            elapsedTime += deltaTime; // Обновляем время

            // Вычисляем прогресс анимации (от 0 до 1)
            float progress = Math.min(elapsedTime / duration, 1f);

            // Обновляем позицию спрайта
            float currentX = getX() + (portal.getX() - getX()) * progress;
            float currentY = getY() + (portal.getY() - getY()) * progress;

            // Обновляем размер спрайта
            float currentSize = (1 - progress) * getWidth(); // Уменьшаем размер до конечного размера
            setSize(currentSize * scale, currentSize * scale);

            // Обновляем угол вращения спрайта
            setRotation(rotation * 360 * progress); // Вращаем на заданное количество градусов

            if (progress >= 1) return true;
            else return false;
        }
        return false;
    }

    public boolean goSmallAnimation(float deltaTime) {
        if (sAnimated) {
            elapsedTime += deltaTime; // Обновляем время

            // Вычисляем прогресс анимации (от 0 до 1)
            float progress = Math.min(elapsedTime / 0.5f, 1f);

            // Обновляем размер спрайта
            float currentSize = (1 - progress) * getWidth(); // Уменьшаем размер до конечного размера
            setSize(currentSize * scale, currentSize * scale);

            // Обновляем угол вращения спрайта
            setRotation((rotation - 1) * 360 * progress); // Вращаем на заданное количество градусов

            if (progress >= 1) return true;
            else return false;
        }
        return false;
    }
}
