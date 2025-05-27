package end.team.center.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.Center;
import end.team.center.GameCore.UIElements.Fon;
import end.team.center.Screens.Game.GameScreen;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Fon backround;
    private Music backgroundMusic;
    private String[] texts = new String[] {
        "ДОБРО ПОЖАЛОВАТЬ!",
        "Сбегаешь от правды? \n   Победа!",
        "Разве... это спасение? \n     Поражение...",
        "Не это сделало их такими... \n              Поражение..."
    };

    public MainMenuScreen(int code) { // 0 - ничего, 1 - победа, 2 - поражение от ХП, 3 - поражение от радиации
        // Создаем сцену
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Устанавливаем обработчик ввода

        Texture fon = new Texture(Gdx.files.internal("UI/MainMenu/fon.jpg"));

        Vector2 size = new Vector2(fon.getWidth(), fon.getHeight());

        int x = Gdx.graphics.getWidth() - fon.getWidth();
        int y = Gdx.graphics.getHeight() - fon.getHeight();

        if (x > y) {
            size.add(x, x);
        } else {
            size.add(y, y);
        }

        TextureRegion tr = new TextureRegion(fon);
        backround = new Fon(tr, new Vector2(0, 0), size);

        String str;

        if (code == 0)      str = texts[code];
        else if (code == 1) str = texts[code];
        else if (code == 2) str = texts[code];
        else                str = texts[code];

        Label label = new Label(str, new Skin(Gdx.files.internal("UI/AboutGame/label.json")));
        label.setFontScale(2f);

        skin = new Skin(Gdx.files.internal("UI/MainMenu/skinPlayButton.json"));

        // Создаем кнопки
        Button buttonStart     = new Button(skin);

        // Создаем таблицу
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Добавляем кнопки в таблицу с отступами
        table.add(label).fillY().center().pad(80).row();
        table.add(buttonStart).height(290).width(630); // pad - отступ между кнопками

        // Добавляем кнопку на сцену
        stage.addActor(backround);
        stage.addActor(table);

        // Добавляем обработчик нажатия на кнопку
        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
                backgroundMusic.stop();
            }
        });

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/lobby.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
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
        backgroundMusic.stop();
        backgroundMusic.dispose();
    }
}
