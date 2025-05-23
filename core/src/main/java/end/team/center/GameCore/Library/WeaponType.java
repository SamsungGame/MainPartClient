package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum WeaponType {
    knife(new Texture(Gdx.files.internal("UI/GameUI/Weapon/knife.png")), 13, 3, 5,2, 400);

    private final Texture texture;
    private final int width, height, damage;
    private final float reload, distance;

    WeaponType(Texture texture, int width, int height, int damage, float reload, float distance) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.reload = reload;
        this.damage = damage;
        this.distance = distance;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getHeight() {
        return height;
    }

    public float getReload() {
        return reload;
    }

    public int getWidth() {
        return width;
    }

    public int getDamage() {
        return damage;
    }

    public float getDistance() {
        return distance;
    }
}
