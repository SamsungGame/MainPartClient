package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Animations.HeroAnimations;

public class Hero extends Friendly {

    private static final float BOUNDARY_PADDING = 100f;

    private float WORLD_WIDTH;
    private float WORLD_HEIGHT;
    private float heroWidth;
    private float heroHeight;

    private Texture playerRightTexture;
    private TextureRegion currentRegion;

    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> stayRightAnimation;
    private Animation<TextureRegion> stayLeftAnimation;

    private float stateTime;
    private boolean movingRight = true;
    private boolean isMoving = false;

    private HeroAnimations heroAnimations = new HeroAnimations();

    public Hero(Texture rightTurn, Texture leftTurn, Vector2 vector, int health, int damage, int defence, float speed, float width, float height, float worldWidth, float worldHeight) {
        super(rightTurn, leftTurn, vector, health, damage, defence, speed);
        this.heroWidth = width;
        this.heroHeight = height;
        this.stateTime = 0f;
        this.WORLD_WIDTH = worldWidth;
        this.WORLD_HEIGHT = worldHeight;

        playerRightTexture = rightTurn;
        currentRegion = new TextureRegion(playerRightTexture);

        setSize(width, height);
        setPosition(vector.x, vector.y);
        setBounds(vector.x, vector.y, width, height);

        // Загрузка кадров анимации
        TextureRegion[] rightFramesArray = new TextureRegion[heroAnimations.rightFrames.length];
        TextureRegion[] leftFramesArray = new TextureRegion[heroAnimations.leftFrames.length];
        TextureRegion[] rightStayArray = new TextureRegion[heroAnimations.rightStay.length];
        TextureRegion[] leftStayArray = new TextureRegion[heroAnimations.leftStay.length];

        for (int i = 0; i < heroAnimations.rightFrames.length; i++) {
            rightFramesArray[i] = new TextureRegion(new Texture(heroAnimations.rightFrames[i]));
            leftFramesArray[i] = new TextureRegion(new Texture(heroAnimations.leftFrames[i]));
        }

        for (int i = 0; i < heroAnimations.rightStay.length; i++) {
            rightStayArray[i] = new TextureRegion(new Texture(heroAnimations.rightStay[i]));
            leftStayArray[i] = new TextureRegion(new Texture(heroAnimations.leftStay[i]));
        }

        walkRightAnimation = new Animation<>(0.13f, rightFramesArray);
        walkLeftAnimation = new Animation<>(0.13f, leftFramesArray);
        stayRightAnimation = new Animation<>(0.22f, rightStayArray);
        stayLeftAnimation = new Animation<>(0.22f, leftStayArray);
    }

    public void move(float deltaX, float deltaY, float delta) {
//        isMoving = deltaX != 0 || deltaY != 0;
//
//        Vector2 potentialPosition = new Vector2(vector.x + deltaX * speed * delta,
//            vector.y + deltaY * speed * delta);
//
//        // Ограничения по границам мира
//        potentialPosition.x = Math.max(BOUNDARY_PADDING,
//                Math.min(potentialPosition.x, WORLD_WIDTH - BOUNDARY_PADDING - heroWidth));
//        potentialPosition.y = Math.max(BOUNDARY_PADDING,
//                Math.min(potentialPosition.y, WORLD_HEIGHT - BOUNDARY_PADDING - heroHeight));
//
//        vector.set(potentialPosition);
//        setPosition(vector.x, vector.y);
//
//        if (deltaX > 0) {
//            movingRight = true;
//        } else if (deltaX < 0) {
//            movingRight = false;
//        }
//
//        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        TextureRegion currentFrame;
//
//        if (isMoving) {
//            currentFrame = movingRight
//                    ? walkRightAnimation.getKeyFrame(stateTime, true)
//                    : walkLeftAnimation.getKeyFrame(stateTime, true);
//        } else {
//            currentFrame = movingRight
//                    ? stayRightAnimation.getKeyFrame(stateTime, true)
//                    : stayLeftAnimation.getKeyFrame(stateTime, true);
//        }
//
//        batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());
    }

    public void dispose() {
        for (TextureRegion region : walkRightAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : walkLeftAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : stayRightAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : stayLeftAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }

    public Vector2 getCenterPosition() {
        return new Vector2(vector.x + heroWidth / 2f, vector.y + heroHeight / 2f);
    }

    public Vector2 getPositionVector() {
        return vector;
    }
}
