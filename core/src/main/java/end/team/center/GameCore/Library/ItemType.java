package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum ItemType {
    exp(new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/exp.png")), 28, 42),
    accumulator(new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/accomulator.png")), 48, 56),
    BreakIron(new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/stone.png")), 48, 48),
    Bandage(new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/bandage.png")), 57, 45),
    lamp(new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/lamp.png")), 45, 72);

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
