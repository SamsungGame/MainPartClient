package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import end.team.center.GameCore.Animations.CharacterAnimation;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.AI.AI_Owl;

public enum EnemyType {
    // Типы существующих врагов
    Owl(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_down.png")), CharacterAnimation.Owl, 120, 140, 5, 0, 1, 1, 100f);

    private final Texture texture;
    private final CharacterAnimation anim;
    private final int health, defense, damage, level; // Базовые
    private final float speed, height, width;

    EnemyType(Texture texture, CharacterAnimation anim, float height, float width, int health, int defense, int damage, int level, float speed) {
        this.texture = texture;
        this.anim = anim;
        this.height = height;
        this.width = width;
        this.health = health;
        this.defense = defense;
        this.damage = damage;
        this.level = level;
        this.speed = speed;
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

    public int getLevel() {
        return level;
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
}
