package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {
    Texture texture;

    public GameObject(Texture texture) {
        this.texture = texture;
    }
    protected int x, y;
}
