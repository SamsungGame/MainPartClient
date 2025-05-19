package end.team.center.GameCore.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Hero extends Friendly {
    private float heroWidth;
    private float heroHeight;
    private TextureRegion currentRegion;

    public Hero(Texture rightTurn, Texture leftTurn, Vector2 vector, float width, float height, int health, int damage, int defence, float speed) {
        super(rightTurn, leftTurn, vector, health, damage, defence, speed);
        this.heroWidth = width;
        this.heroHeight = height;

        currentRegion = new TextureRegion(rightTurn);

        setSize(width, height);
        setPosition(vector.x, vector.y);
        setBounds(vector.x, vector.y, width, height);
    }

    public void move(float deltaX, float deltaY, float delta) {
        vector.x += deltaX * speed * delta;
        vector.y += deltaY * speed * delta;

        vector.x = Math.max(0, Math.min(vector.x, Gdx.graphics.getWidth() - heroWidth));
        vector.y = Math.max(0, Math.min(vector.y, Gdx.graphics.getHeight() - heroHeight));

        setPosition(vector.x, vector.y);

        if (deltaX > 0) {
            currentRegion.setRegion(rightTurn);
        } else if (deltaX < 0) {
            currentRegion.setRegion(leftTurn);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentRegion, getX(), getY(), getWidth(), getHeight());
    }

    public void dispose() {
        rightTurn.dispose();
        leftTurn.dispose();
    }
}
