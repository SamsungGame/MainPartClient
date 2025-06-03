package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import end.team.center.ProgramSetting.LocalDB.DatabaseManager;
import end.team.center.ProgramSetting.LocalDB.controllers.UserStateController;
import end.team.center.Screens.Menu.MainMenuScreen;

public class Center extends Game {

    public SpriteBatch batch;
    private final DatabaseManager databaseManager;
    private final UserStateController userStateController;

    public Center(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.userStateController = new UserStateController(getDatabaseManager().getUserStateDao());
    }
    @Override
    public void create() {
        // Создание спрайт-объекта, который будет использоваться для отрисовки
        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(0, this));

    }
    public UserStateController getUserStateController() {
        return userStateController;
    }
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void render() {
        super.render(); // Рендерит текущий экран
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
