package end.team.center.GameCore.HelpsClass;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class PowerElement extends Actor {
    public boolean isSelected = false;
    private Texture texture;
    private Sprite portal;
    private String name, description;
    private boolean animated = false, sAnimated = false;
    private float scale = 1f, startScaleX, startScaleY, elapsedTime = 0;
    private int rotation = 3, duration = 2;

    public PowerElement(Texture texture, Sprite portal) {
        this.texture = texture;
        this.portal = portal;
        setSize(texture.getWidth(), texture.getHeight());
        setHeight(texture.getHeight());
        setWidth(texture.getWidth());
        setOrigin((getX() + getWidth()) / 2, (getY() + getHeight()) / 2);
        startScaleX = getScaleX();
        startScaleY = getScaleY();
    }
    public PowerElement(Texture texture, Sprite portal, String name) {
        this.texture = texture;
        this.name = name;
        this.portal = portal;
        setSize(texture.getWidth(), texture.getHeight());
        setHeight(texture.getHeight());
        setWidth(texture.getWidth());
        setOrigin((getX() + getWidth()) / 2, (getY() + getHeight()) / 2);
    }
    public PowerElement(Texture texture, Sprite portal, String name, String description) {
        this.texture = texture;
        this.name = name;
        this.description = description;
        this.portal = portal;
        setSize(texture.getWidth(), texture.getHeight());
        setHeight(texture.getHeight());
        setWidth(texture.getWidth());
        setOrigin((getX() + getWidth()) / 2, (getY() + getHeight()) / 2);
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

    @Override
    public void act(float delta) {
        super.act(delta);
        if (animated) {
            boolean finished = goAnimation(portal, delta);
            if (finished) animated = false;
        }
        if (sAnimated) {
            boolean finished = goSmallAnimation(delta);
            if (finished) sAnimated = false;
        }
    }

    public boolean goAnimation(Sprite portal, float deltaTime) {
        if (animated) {
            elapsedTime += deltaTime; // Обновляем время

            System.out.println("Пройденное время: " + elapsedTime);

            // Вычисляем прогресс анимации (от 0 до 1)
            float progress = Math.min(elapsedTime / duration, 1f);

            System.out.println("Прогресс: " + progress);

            // Обновляем позицию спрайта
            float currentX = getX() + (portal.getX() - getX()) * progress;
            float currentY = getY() + (portal.getY() - getY()) * progress;
            setPosition(currentX, currentY);

            // Обновляем размер спрайта
            float currentSize = (scale - startScaleX) * progress; // Уменьшаем размер до конечного размера
            setSize(startScaleX * currentSize, startScaleY * currentSize);

            System.out.println("Размер: " + currentSize);

            // Обновляем угол вращения спрайта
            setOrigin((getX() + getWidth()) / 2, (getY() + getHeight()) / 2);
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

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(new TextureRegion(texture),
            getX(), getY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation());
    }
}
