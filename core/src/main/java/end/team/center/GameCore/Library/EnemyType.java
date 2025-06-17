package end.team.center.GameCore.Library;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public enum EnemyType {
    // Типы существующих врагов
    Owl(CharacterAnimation.Owl, 120, 140, 6, 0, 1, random.nextInt(3) + 2, 140f),
    Ghost(CharacterAnimation.Ghost, 144, 126, 5, 0, 1, random.nextInt(4) + 3, 160f),
    Rabbit(CharacterAnimation.Rabbit, 96, 90, 8, 0, 1, random.nextInt(3) + 1, 180f);

    private final CharacterAnimation anim;
    private final int health, defense, damage, exp;
    private final float speed, height, width;

    EnemyType(CharacterAnimation anim, float height, float width, int health, int defense, int damage, int exp, float speed) {
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
