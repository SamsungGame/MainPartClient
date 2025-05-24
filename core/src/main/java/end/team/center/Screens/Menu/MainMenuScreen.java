package end.team.center.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.Center;
import end.team.center.Screens.Game.GameScreen;
import end.team.center.Screens.Game.PowerSelectScreen;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;

    public MainMenuScreen() {
        // Создаем сцену
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Устанавливаем обработчик ввода

        skin = new Skin(Gdx.files.internal("UI/MainMenu/skinPlayButton.json"));

        // Создаем кнопки
        Button buttonStart     = new Button(skin);
        Button buttonSetting   = new Button(skin);
        Button buttonAboutGame = new Button(skin);

        // Создаем таблицу
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Добавляем кнопки в таблицу с отступами
        table.add(buttonStart).height(180).width(640); // pad - отступ между кнопками
        table.row(); // Переход на следующую строку
        table.add(buttonSetting).height(180).width(640);
        table.row();
        table.add(buttonAboutGame).height(180).width(640);

        // Добавляем кнопку на сцену
        stage.addActor(table);

        // Добавляем обработчик нажатия на кнопку
        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        buttonSetting.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new SettingScreen());
            }
        });
        buttonAboutGame.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new AboutGameScreen());
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin .dispose();
    }
}
