package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum ItemType {
    exp(new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/exp.png")), 28, 42);

    private final Texture texture;
    private final int width, height;

    ItemType(Texture texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
