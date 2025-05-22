package end.team.center.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import end.team.center.animations.HeroAnimations;

public class Hero extends Actor {


    private static final float BOUNDARY_PADDING = 100f;
    private float WORLD_WIDTH;
    private float WORLD_HEIGHT;
    private int hp;
    private float speed;
    private float heroWidth;
    private float heroHeight;
    public float heroX;
    public float heroY;

    private Texture playerRightTexture;
    private Texture playerLeftTexture;
    private TextureRegion currentRegion;

    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> stayRightAnimation;
    private Animation<TextureRegion> stayLeftAnimation;
    private float stateTime;
    private boolean movingRight = true;
    private boolean isMoving = false;

    private HeroAnimations heroAnimations = new HeroAnimations();

    public Hero(float x, float y, float width, float height, int hp, float speed,
                String rightTexturePath, String leftTexturePath, float WORLD_WIDTH, float WORLD_HEIGHT) {
        this.hp = hp;
        this.speed = speed;
        this.heroX = x;
        this.heroY = y;
        this.heroWidth = width;
        this.heroHeight = height;
        this.stateTime = 0f;
        this.WORLD_WIDTH = WORLD_WIDTH;
        this.WORLD_HEIGHT = WORLD_HEIGHT;

        playerRightTexture = new Texture(Gdx.files.internal(rightTexturePath));
        playerLeftTexture = new Texture(Gdx.files.internal(leftTexturePath));

        currentRegion = new TextureRegion(playerRightTexture);

        setSize(width, height);
        setPosition(x, y);
        setBounds(x, y, width, height);

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
        isMoving = deltaX != 0 || deltaY != 0;

        float potentialX = heroX + deltaX * speed * delta;
        float potentialY = heroY + deltaY * speed * delta;

        if (potentialX < BOUNDARY_PADDING) {
            potentialX = BOUNDARY_PADDING;
        } else if (potentialX + heroWidth > WORLD_WIDTH - BOUNDARY_PADDING) {
            potentialX = WORLD_WIDTH - BOUNDARY_PADDING - heroWidth;
        }

        if (potentialY < BOUNDARY_PADDING) {
            potentialY = BOUNDARY_PADDING;
        } else if (potentialY + heroHeight > WORLD_HEIGHT - BOUNDARY_PADDING) {
            potentialY = WORLD_HEIGHT - BOUNDARY_PADDING - heroHeight;
        }

        heroX = potentialX;
        heroY = potentialY;
        setPosition(heroX, heroY);

        if (deltaX > 0) {
            movingRight = true;
        } else if (deltaX < 0) {
            movingRight = false;
        }

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;

        if (isMoving) {
            currentFrame = movingRight
                ? walkRightAnimation.getKeyFrame(stateTime, true)
                : walkLeftAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = movingRight
                ? stayRightAnimation.getKeyFrame(stateTime, true)
                : stayLeftAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
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
}
