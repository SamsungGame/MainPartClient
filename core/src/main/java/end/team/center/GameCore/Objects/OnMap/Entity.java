package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;

public abstract class Entity extends GameObject {

    public static final float BOUNDARY_PADDING = 100f;

    protected int health;
    protected int damage, defence;
    protected float speed;
    public float stateTime;
    protected float worldWidth, worldHeight;
    protected boolean isLive = true, isMoving = false;
    protected boolean mRight = false;

    public Animation<TextureRegion> firstTypeAnimation;
    public Animation<TextureRegion> secondTypeAnimation;
    public Animation<TextureRegion> therdTypeAnimation;
    public Animation<TextureRegion> fourthTypeAnimation;


    public Entity(Texture texture, CharacterAnimation anim, Vector2 vector, float width,
                  float height, int health, int damage, int defence,
                  float speed, float worldHeight, float worldWidth) {
        super(texture, vector, width, height);
        this.health = health;
        this.damage = damage;
        this.defence = defence;
        this.speed = speed;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        stateTime = 0;

        // Загрузка кадров анимации
        TextureRegion[] rightFramesArray = anim.getRightWalk();
        TextureRegion[] leftFramesArray  = anim.getLeftWalk();
        TextureRegion[] rightStayArray   = anim.getRightStay();
        TextureRegion[] leftStayArray    = anim.getLeftStay();

        firstTypeAnimation = new Animation<>(0.13f, rightFramesArray);
        secondTypeAnimation = new Animation<>(0.13f, leftFramesArray);
        therdTypeAnimation = new Animation<>(0.22f, rightStayArray);
        fourthTypeAnimation = new Animation<>(0.22f, leftStayArray);
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    public float getSpeed() {
        return speed;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        if (health < 0) health = 0;

        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void updateBound() {
        bound.x = vector.x;
        bound.y = vector.y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        super.dispose();

        for (TextureRegion region : firstTypeAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : secondTypeAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : therdTypeAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : fourthTypeAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }
}
