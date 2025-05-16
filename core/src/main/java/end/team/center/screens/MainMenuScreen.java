package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class MainMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    public MainMenuScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Создание стиля кнопки
        skin = new Skin(Gdx.files.internal("newGameButton/newGameButton.json"));

        // Создание кнопки
        TextButton newGameButton = new TextButton("Новая игра", skin);
        newGameButton.setSize(320, 150);
        newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - newGameButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - newGameButton.getHeight() / 2);

        // Добавление слушателя нажатия на кнопку
        newGameButton.addListener(event -> {
            if(event.isHandled()){
                game.setScreen(new FieldScreen(game));
                newGameButton.remove();
                return true;
            }
            return false;
        });

        stage.addActor(newGameButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        skin.dispose();
    }
}
