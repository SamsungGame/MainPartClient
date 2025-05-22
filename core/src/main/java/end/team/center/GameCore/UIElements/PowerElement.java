package end.team.center.GameCore.UIElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerElement extends Actor {
    public boolean isSelected = false;
    private Texture texture;
    private Image portal;
    private String name, description;
    private boolean animated = false, sAnimated = false;
    private float scale = 50, startScaleX = 130, startScaleY = 130, elapsedTime = 0, startX, startY;
    private float rotation = 1, duration = 1.5f, alpha = 1f;

    public PowerElement(Texture texture, Image portal) {
        this.texture = texture;
        this.portal = portal;
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin((getX() + getWidth()) / 2, (getY() + getHeight()) / 2);
    }
    public PowerElement(Texture texture, Image portal, String name) {
        this.texture = texture;
        this.name = name;
        this.portal = portal;
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin((getX() + getWidth()) / 2, (getY() + getHeight()) / 2);
    }
    public PowerElement(Texture texture, Image portal, String name, String description) {
        this.texture = texture;
        this.name = name;
        this.description = description;
        this.portal = portal;
        setSize(texture.getWidth(), texture.getHeight());
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
        startX = getX() + getWidth() / 2;
        startY = getY() + getHeight() / 2;
    }
    public void startSmallAnimation() {
        sAnimated = true;
        duration /= 2;
        startX = getX() + getWidth() / 2;
        startY = getY() + getHeight() / 2;
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

    public boolean goAnimation(Image portal, float deltaTime) {
        if (animated) {
            elapsedTime += deltaTime; // Обновляем время
            float progress = Math.min(elapsedTime / duration, 1f);

            // Центр цели (портала)
            float portalCenterX = portal.getX() + portal.getWidth() / 2f;
            float portalCenterY = portal.getY() + portal.getHeight() / 2f;

            // Текущий центр нашего объекта
            float currentX = startX + (portalCenterX - startX) * progress;
            float currentY = startY + (portalCenterY - startY) * progress;

            // Устанавливаем позицию так, чтобы центр совпадал
            setPosition(currentX - getWidth() / 2f, currentY - getHeight() / 2f);

            // Анимация масштаба (используем setScale)
            float currentScaleX = startScaleX + (scale - startScaleX) * progress;
            float currentScaleY = startScaleY + (scale - startScaleY) * progress;
            setSize(currentScaleX, currentScaleY);

            // Вращение
            setOrigin(getWidth() / 2f, getHeight() / 2f); // Вращение вокруг центра
            float currentRotation = rotation * 360f * progress; // rotation — число оборотов или градусов?
            setRotation(currentRotation);

            alpha = 1 - progress;

            if (progress >= 1f) return true; // Анимация завершена
            else return false; // Анимация продолжается
        }
        return false;
    }

    public boolean goSmallAnimation(float deltaTime) {
        if (sAnimated) {
            elapsedTime += deltaTime; // Обновляем время
            float progress = Math.min(elapsedTime / duration, 1f);

            // Анимация масштаба (используем setScale)
            float currentScaleX = startScaleX + (scale - startScaleX) * progress;
            float currentScaleY = startScaleY + (scale - startScaleY) * progress;
            setSize(currentScaleX, currentScaleY);

            // Вращение
            setOrigin(getWidth() / 2f, getHeight() / 2f); // Вращение вокруг центра
            float currentRotation = rotation * 360f * progress; // rotation — число оборотов или градусов?
            setRotation(currentRotation);

            alpha = 1 - progress;

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
        batch.setColor(1f, 1f, 1f, alpha);
        batch.draw(new TextureRegion(texture),
            getX(), getY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation());
    }
}
