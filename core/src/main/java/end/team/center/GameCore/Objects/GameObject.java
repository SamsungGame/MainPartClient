package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameObject extends Sprite {

    public GameObject(Texture texture) {
        super(texture);
    }
    protected int x, y;
}
