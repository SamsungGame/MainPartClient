package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public enum EnemyType {
    // Типы существующих врагов
    @SuppressWarnings("NewApi")
    Owl(CharacterAnimation.Owl, 120, 140, 6, 0, 1, new Random().nextInt(2, 4), 100f),
    @SuppressWarnings("NewApi")
    Ghost(CharacterAnimation.Ghost, 144, 126, 5, 0, 1, new Random().nextInt(3, 6), 130f),
    @SuppressWarnings("NewApi")
    Rabbit(CharacterAnimation.Rabbit, 144, 135, 8, 0, 1, new Random().nextInt(1, 3), 160f);

    private final CharacterAnimation anim;
    private final int health, defense, damage, exp; // Базовые
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
