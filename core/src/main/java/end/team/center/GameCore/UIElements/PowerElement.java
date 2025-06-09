package end.team.center.GameCore.UIElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerElement extends Actor {
    public boolean isSelected = false;
    private Texture texture;
    private final Image portal;
    private final Power p;
    private String name, description;

    private boolean animated = false, sAnimated = false;
    private float scale = 50, startScaleX = 130, startScaleY = 130, elapsedTime = 0;
    private float startX, startY;
    private float rotation = 1, duration = 1.5f, alpha = 1f;

    private Runnable onFinishedAnimation;

    public PowerElement(Texture texture, Power p, Image portal) {
        this(texture, p, portal, null, null);
    }

    public PowerElement(Texture texture, Power p, Image portal, String description) {
        this(texture, p, portal, null, description);
    }

    public PowerElement(Texture texture, Power p, Image portal, String name, String description) {
        this.p = p;
        this.texture = texture;
        this.portal = portal;
        this.name = name;
        this.description = description;
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2f, getHeight() / 2f);
    }

    public String getDescription() {
        return description;
    }

    public Power getPower() {
        return p;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setOnAnimationFinished(Runnable r) {
        this.onFinishedAnimation = r;
    }

    public void startAnimation() {
        animated = true;
        elapsedTime = 0;
        startX = getX() + getWidth() / 2f;
        startY = getY() + getHeight() / 2f;
    }

    public void startSmallAnimation() {
        sAnimated = true;
        elapsedTime = 0;
        duration /= 2f;
        startX = getX() + getWidth() / 2f;
        startY = getY() + getHeight() / 2f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (animated) {
            boolean finished = goAnimation(delta);
            if (finished) {
                animated = false;
                p.effect();
                if (onFinishedAnimation != null) {
                    onFinishedAnimation.run();
                    onFinishedAnimation = null;
                }
            }
        }
        if (sAnimated) {
            boolean finished = goSmallAnimation(delta);
            if (finished) sAnimated = false;
        }
    }

    private boolean goAnimation(float deltaTime) {
        elapsedTime += deltaTime;
        float progress = Math.min(elapsedTime / duration, 1f);

        float portalCenterX = portal.getX() + portal.getWidth() / 2f;
        float portalCenterY = portal.getY() + portal.getHeight() / 2f;
        float currentX = startX + (portalCenterX - startX) * progress;
        float currentY = startY + (portalCenterY - startY) * progress;
        setPosition(currentX - getWidth() / 2f, currentY - getHeight() / 2f);

        float currentScaleX = startScaleX + (scale - startScaleX) * progress;
        float currentScaleY = startScaleY + (scale - startScaleY) * progress;
        setSize(currentScaleX, currentScaleY);

        setOrigin(getWidth() / 2f, getHeight() / 2f);
        setRotation(rotation * 360f * progress);

        alpha = 1f - progress;

        return progress >= 1f;
    }

    private boolean goSmallAnimation(float deltaTime) {
        elapsedTime += deltaTime;
        float progress = Math.min(elapsedTime / duration, 1f);

        float currentScaleX = startScaleX + (scale - startScaleX) * progress;
        float currentScaleY = startScaleY + (scale - startScaleY) * progress;
        setSize(currentScaleX, currentScaleY);

        setOrigin(getWidth() / 2f, getHeight() / 2f);
        setRotation(rotation * 360f * progress);

        alpha = 1f - progress;

        return progress >= 1f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1f, 1f, 1f, alpha);
        batch.draw(new TextureRegion(texture), getX(), getY(),
            getOriginX(), getOriginY(), getWidth(), getHeight(),
            getScaleX(), getScaleY(), getRotation());
    }
}
