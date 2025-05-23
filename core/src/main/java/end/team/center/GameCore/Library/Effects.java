package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public enum Effects {
    knifeStep(new Texture(Gdx.files.internal("UI/GameUI/Weapon/effKnifeAttack.png")), 1);
    private final Texture texture;
    private final int duration;
    private final float speed;

    Effects(Texture texture, float speed) {
        this.texture = texture;
        duration = 0;
        this.speed = speed;
    }

    Effects(Texture texture, int duration, float speed) {
        this.texture = texture;
        this.duration = duration;
        this.speed = speed;
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
}
