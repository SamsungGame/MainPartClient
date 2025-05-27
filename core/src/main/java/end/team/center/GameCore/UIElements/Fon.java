package end.team.center.GameCore.UIElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Fon extends Actor {
    TextureRegion texture;
    Vector2 vector, size;
    public Fon(TextureRegion texture, Vector2 vector, Vector2 size) {
        this.texture = texture;
        this.vector  = vector;

        setPosition(vector.x, vector.y);
        setSize(size.x, size.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
