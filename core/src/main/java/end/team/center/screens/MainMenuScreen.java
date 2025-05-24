package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class MainMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture gameTitleTexture = new Texture(Gdx.files.internal("gameTitle.png"));
    public MainMenuScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        Image gameTitleImage = new Image(gameTitleTexture);
        gameTitleImage.setSize(1000, 200);
        gameTitleImage.setPosition(Gdx.graphics.getWidth() / 2 - gameTitleImage.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - gameTitleImage.getHeight() / 2 + 200);

        TextButton newGameButton = new TextButton("Новая игра", skin);
        newGameButton.setSize(300, 100);
        newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - newGameButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - newGameButton.getHeight() / 2 - newGameButton.getHeight() / 2);

        TextButton achievementsButton = new TextButton("Достижения", skin);
        achievementsButton.setSize(300, 100);
        achievementsButton.setPosition(Gdx.graphics.getWidth() / 2 - achievementsButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - achievementsButton.getHeight() / 2 - achievementsButton.getHeight() / 2 - newGameButton.getHeight());

        TextButton settingsButton = new TextButton("Настройки", skin);
        settingsButton.setSize(300, 100);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2 - settingsButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - settingsButton.getHeight() / 2 - settingsButton.getHeight() / 2 - achievementsButton.getHeight() * 2);

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FieldScreen(game));
            }
        });

        achievementsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new AchievementsScreen(game));
            }
        });

        stage.addActor(gameTitleImage);
        stage.addActor(newGameButton);
        stage.addActor(achievementsButton);
        stage.addActor(settingsButton);
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
        gameTitleTexture.dispose();
    }
}
