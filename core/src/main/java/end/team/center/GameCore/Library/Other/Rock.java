package end.team.center.GameCore.Library.Other;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.OnMap.StaticObject;

public class Rock extends StaticObject {
    public Rock(Texture texture, Vector2 vector, float height, float width, boolean canWalk) {
        super(texture, vector, height, width, canWalk);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
