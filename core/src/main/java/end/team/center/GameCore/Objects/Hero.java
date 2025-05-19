package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    public Hero(float x, float y, float width, float height, int hp, float speed, String rightTexturePath, String leftTexturePath) {
        this.hp = hp;
        this.speed = speed;
        this.heroX = x;
        this.heroY = y;
        this.heroWidth = width;
        this.heroHeight = height;

        playerRightTexture = new Texture(Gdx.files.internal(rightTexturePath));
        playerLeftTexture = new Texture(Gdx.files.internal(leftTexturePath));

        currentRegion = new TextureRegion(playerRightTexture);

        setSize(width, height);
        setPosition(x, y);
        setBounds(x, y, width, height);
    }

    public void move(float deltaX, float deltaY, float delta) {
        heroX += deltaX * speed * delta;
        heroY += deltaY * speed * delta;

        heroX = Math.max(0, Math.min(heroX, Gdx.graphics.getWidth() - heroWidth));
        heroY = Math.max(0, Math.min(heroY, Gdx.graphics.getHeight() - heroHeight));

        setPosition(heroX, heroY);

        if (deltaX > 0) {
            currentRegion.setRegion(playerRightTexture);
        } else if (deltaX < 0) {
            currentRegion.setRegion(playerLeftTexture);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentRegion, getX(), getY(), getWidth(), getHeight());
    }

    public void dispose() {
        playerRightTexture.dispose();
        playerLeftTexture.dispose();
    }
}
