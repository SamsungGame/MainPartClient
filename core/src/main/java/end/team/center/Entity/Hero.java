package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hero extends Actor {

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
    private  String[] rightFrames = {


        "UI/GameUI/Hero/heroRight1.png",
        "UI/GameUI/Hero/heroRight_down.png",
        "UI/GameUI/Hero/heroRight_down.png",

        "UI/GameUI/Hero/heroRight.png",
        "UI/GameUI/Hero/heroRight2_down.png",
        "UI/GameUI/Hero/heroRight2_down.png",

        "UI/GameUI/Hero/heroRight2.png",
        "UI/GameUI/Hero/heroRight_down.png",
        "UI/GameUI/Hero/heroRight_down.png",

        "UI/GameUI/Hero/heroRight.png",
        "UI/GameUI/Hero/heroRight1_down.png",
        "UI/GameUI/Hero/heroRight1_down.png",


    };

    private String[] leftFrames = {

        "UI/GameUI/Hero/heroLeft1.png",
        "UI/GameUI/Hero/heroLeft_down.png",
        "UI/GameUI/Hero/heroLeft_down.png",

        "UI/GameUI/Hero/heroLeft.png",
        "UI/GameUI/Hero/heroLeft2_down.png",
        "UI/GameUI/Hero/heroLeft2_down.png",

        "UI/GameUI/Hero/heroLeft2.png",
        "UI/GameUI/Hero/heroLeft_down.png",
        "UI/GameUI/Hero/heroLeft_down.png",

        "UI/GameUI/Hero/heroLeft.png",
        "UI/GameUI/Hero/heroLeft1_down.png",
        "UI/GameUI/Hero/heroLeft1_down.png",


    };
    private String[] leftStay = {
        "UI/GameUI/Hero/heroLeft.png",
        "UI/GameUI/Hero/heroLeft_down.png",
    };

    private String[] rightStay = {
        "UI/GameUI/Hero/heroRight.png",
        "UI/GameUI/Hero/heroRight_down.png",
    };


    public Hero(float x, float y, float width, float height, int hp, float speed,
                String rightTexturePath, String leftTexturePath) {
        this.hp = hp;
        this.speed = speed;
        this.heroX = x;
        this.heroY = y;
        this.heroWidth = width;
        this.heroHeight = height;
        this.stateTime = 0f;

        playerRightTexture = new Texture(Gdx.files.internal(rightTexturePath));
        playerLeftTexture = new Texture(Gdx.files.internal(leftTexturePath));

        currentRegion = new TextureRegion(playerRightTexture);

        setSize(width, height);
        setPosition(x, y);
        setBounds(x, y, width, height);

        TextureRegion[] rightFramesArray = new TextureRegion[rightFrames.length];
        TextureRegion[] leftFramesArray = new TextureRegion[leftFrames.length];

        TextureRegion[] rightStayArray = new TextureRegion[rightStay.length];
        TextureRegion[] leftStayArray = new TextureRegion[leftStay.length];


        for (int i = 0; i < rightFrames.length; i++) {
            rightFramesArray[i] = new TextureRegion(new Texture(rightFrames[i]));
            leftFramesArray[i] = new TextureRegion(new Texture(leftFrames[i]));
        }

        for (int i = 0; i < rightStay.length; i++) {
            rightStayArray[i] = new TextureRegion(new Texture(rightStay[i]));
            leftStayArray[i] = new TextureRegion(new Texture(leftStay[i]));
        }


        walkRightAnimation = new Animation<>(0.13f, rightFramesArray);
        walkLeftAnimation = new Animation<>(0.13f, leftFramesArray);

        stayRightAnimation = new Animation<>(0.22f, rightStayArray);
        stayLeftAnimation = new Animation<>(0.22f, leftStayArray);
    }

    public void move(float deltaX, float deltaY, float delta) {
        isMoving = deltaX != 0 || deltaY != 0;

        heroX += deltaX * speed * delta;
        heroY += deltaY * speed * delta;

        heroX = Math.max(0, Math.min(heroX, Gdx.graphics.getWidth() - heroWidth));
        heroY = Math.max(0, Math.min(heroY, Gdx.graphics.getHeight() - heroHeight));

        setPosition(heroX, heroY);

        if (deltaX > 0) {
            movingRight = true;

        } else if(deltaX < 0){
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
