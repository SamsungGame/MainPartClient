package end.team.center;

import com.badlogic.gdx.Game;

import end.team.center.screens.MainMenuScreen;

public class MyGame extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}
