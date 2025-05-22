package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Text;

import end.team.center.GameCore.Animations.AnimationsHero;

public class Entity extends GameObject {

    private static final float BOUNDARY_PADDING = 100f;

    Texture rightTurn, leftTurn;
    protected int health;
    protected int damage, defence;
    protected float speed;
    protected float stateTime;
    protected float worldWidth, worldHeight;
    protected boolean isLive = true, isMoving = false;
    protected boolean mRight = false;

    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> stayRightAnimation;
    private Animation<TextureRegion> stayLeftAnimation;

    public Entity(Texture rightTurn, Texture leftTurn, Vector2 vector, float width, float height, int health, int damage, int defence, float speed) {
        super(vector, width, height);
        this.rightTurn = rightTurn;
        this.leftTurn = leftTurn;
        this.health = health;
        this.damage = damage;
        this.defence = defence;
        this.speed = speed;

        stateTime = 0;

        // Загрузка кадров анимации
        TextureRegion[] rightFramesArray = new TextureRegion[AnimationsHero.rightFrames.length];
        TextureRegion[] leftFramesArray  = new TextureRegion[heroAnimations.leftFrames.length];
        TextureRegion[] rightStayArray   = new TextureRegion[heroAnimations.rightStay.length];
        TextureRegion[] leftStayArray    = new TextureRegion[heroAnimations.leftStay.length];

        walkRightAnimation = new Animation<>(0.13f, rightFramesArray);
        walkLeftAnimation = new Animation<>(0.13f, leftFramesArray);
        stayRightAnimation = new Animation<>(0.22f, rightStayArray);
        stayLeftAnimation = new Animation<>(0.22f, leftStayArray);
    }

    @Override
    public void act(float delta) {
        move();
    }

    public void move(float deltaX, float deltaY, float delta) {
        isMoving = deltaX != 0 || deltaY != 0;

        Vector2 potentialPosition = new Vector2(vector.x + deltaX * speed * delta,
                vector.y + deltaY * speed * delta);

        // Ограничения по границам мира
        potentialPosition.x = Math.max(BOUNDARY_PADDING,
                Math.min(potentialPosition.x, worldWidth - BOUNDARY_PADDING - width));
        potentialPosition.y = Math.max(BOUNDARY_PADDING,
                Math.min(potentialPosition.y, worldHeight - BOUNDARY_PADDING - height));

        vector.set(potentialPosition);
        setPosition(vector.x, vector.y);

        if (deltaX > 0) {
            mRight = true;
        } else if (deltaX < 0) {
            mRight = false;
        }

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion currentFrame;

        if (isMoving) {
            currentFrame = mRight
                    ? walkRightAnimation.getKeyFrame(stateTime, true)
                    : walkLeftAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = mRight
                    ? stayRightAnimation.getKeyFrame(stateTime, true)
                    : stayLeftAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());
    }
}
