package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import org.w3c.dom.Text;

public enum WeaponType {
    knife(new Texture(Gdx.files.internal("UI/GameUI/Weapon/gradient.png")), new Texture(Gdx.files.internal("UI/GameUI/Weapon/redGradient.png")), new Texture(Gdx.files.internal("UI/GameUI/Weapon/effKnifeAttack.png")), 140, 260, 5, 2, 200);

    private final Texture canTexture, cantTexture, texture;
    private final int width, height, damage;
    private final float reload, distance;

    WeaponType(Texture canTexture, Texture cantTexture, Texture texture, int width, int height, int damage, float reload, float distance) {
        this.canTexture = canTexture;
        this.cantTexture = cantTexture;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.reload = reload;
        this.damage = damage;
        this.distance = distance;
    }

    public Texture getCanTexture() {
        return canTexture;
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

    public Texture getCantTexture() {
        return cantTexture;
    }

    public Texture getTexture() {
        return texture;
    }
}
