package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Menu.MainMenuScreen;

public class Center extends Game {

    public SpriteBatch batch;
    private GameRepository gameRepository;

    public static int currentSkin = 1;
    public static ArrayList<String> prices = new ArrayList<>();

    public Center() {
        prices.add("0");
        prices.add("10");
        prices.add("0");
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
