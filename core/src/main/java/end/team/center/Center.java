package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Menu.MainMenuScreen;

public class Center extends Game {

    public SpriteBatch batch;
    private GameRepository gameRepository;

    public Center() {

    }
    public void setGameRepository(GameRepository repo) {
        this.gameRepository = repo;
    }
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(0, gameRepository));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
