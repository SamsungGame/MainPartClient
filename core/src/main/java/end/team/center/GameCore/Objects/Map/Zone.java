package end.team.center.GameCore.Objects.Map;

import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import end.team.center.Screens.Game.GameScreen;

public class Zone {
    public Rectangle bound;
    public int level;

    public Zone(int level) {
        float p = (GameScreen.WORLD_WIDTH + GameScreen.WORLD_HEIGHT) / 2;

        float w = (float) (Math.random() * p / 10);
        float h = (float) (Math.random() * p / 10);

        bound = new Rectangle((float) (Math.random() * GameScreen.WORLD_WIDTH), (float) (Math.random() * GameScreen.WORLD_HEIGHT), w, h);

        this.level = level;
    }
}
