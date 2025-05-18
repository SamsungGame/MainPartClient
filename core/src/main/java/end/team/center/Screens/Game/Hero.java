package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Hero extends Image {

    private int hp;
    private float speed;
    private float heroWidth;
    private float heroHeight;
    public float heroX;
    public float heroY;
    private Image playerImage;

    public Hero(float x, float y, float width, float height, int hp, float speed, String texturePath) {
        this.hp = hp;
        this.speed = speed;
        this.heroX = x;
        this.heroY = y;
        this.heroWidth = width;
        this.heroHeight = height;
        this.playerImage = new Image(new Texture(texturePath));
        playerImage.setSize(width, height);
        playerImage.setPosition(x, y);
    }

    public void move(float deltaX, float deltaY, float delta) {
        heroX += deltaX * speed * delta;
        heroY += deltaY * speed * delta;

        heroX = Math.max(0, Math.min(heroX, Gdx.graphics.getWidth() - heroWidth));
        heroY = Math.max(0, Math.min(heroY, Gdx.graphics.getHeight() - heroHeight));

        playerImage.setPosition(heroX, heroY);
    }

    public void draw(Batch batch) {
        playerImage.draw(batch, 1);
    }

    public void dispose() {
        ((TextureRegionDrawable) playerImage.getDrawable()).getRegion().getTexture().dispose();
    }
}
