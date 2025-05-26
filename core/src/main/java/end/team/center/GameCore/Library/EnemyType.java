package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public enum EnemyType {
    // Типы существующих врагов
    @SuppressWarnings("NewApi")
    Owl(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_down.png")), CharacterAnimation.Owl, 120, 140, 8, 0, 1, new Random().nextInt(2, 4), 100f),
    @SuppressWarnings("NewApi")
    Ghost(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostWalk.png")), CharacterAnimation.Ghost, 126, 144, 5, 0, 1, new Random().nextInt(4, 8), 200f);

    private final Texture texture;
    private final CharacterAnimation anim;
    private final int health, defense, damage, exp; // Базовые
    private final float speed, height, width;

    EnemyType(Texture texture, CharacterAnimation anim, float height, float width, int health, int defense, int damage, int exp, float speed) {
        this.texture = texture;
        this.anim = anim;
        this.height = height;
        this.width = width;
        this.health = health;
        this.defense = defense;
        this.damage = damage;
        this.speed = speed;
        this.exp = exp;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public Texture getTexture() {
        return texture;
    }

    public CharacterAnimation getAnim() {
        return anim;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public int getExp() {
        return exp;
    }
}
