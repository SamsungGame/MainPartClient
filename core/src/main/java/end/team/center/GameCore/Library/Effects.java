package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public enum Effects {
    knifeStep(new Texture(Gdx.files.internal("UI/GameUI/Weapon/effKnifeAttack.png")), 96, 42, 5);
    private final Texture texture;
    private final int duration, width, height;
    private final float speed;

    Effects(Texture texture, int width, int height, float speed) {
        this.texture = texture;
        duration = 0;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    Effects(Texture texture, int width, int height, int duration, float speed) {
        this.texture = texture;
        this.duration = duration;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getDuration() {
        return duration;
    }

    public float getSpeed() {
        return speed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
