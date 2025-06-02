package end.team.center.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.Center;
import end.team.center.GameCore.UIElements.Fon;
import end.team.center.LocalDB.controllers.UserStateController;
import end.team.center.Screens.Game.GameScreen;

public class MainMenuScreen implements Screen {
    private final Center center;
    private final UserStateController controller;
    private Stage stage;
    private Skin skin;
    private Fon background;
    private Music backgroundMusic;
    private Label conclusionText, coinsText;

    private static final String[] texts = {
        "ДОБРО ПОЖАЛОВАТЬ!",
        "Сбегаешь от правды? \n   Победа!",
        "Разве... это спасение? \n     Поражение...",
        "Не это сделало их такими... \n              Поражение..."
    };

    public MainMenuScreen(int code, Center center) {
        this.center = center;
        this.controller = center.getUserStateController();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загрузка фона
        Texture fon = new Texture(Gdx.files.internal("UI/MainMenu/fon.jpg"));
        Vector2 size = new Vector2(fon.getWidth(), fon.getHeight());

        int x = Gdx.graphics.getWidth() - fon.getWidth();
        int y = Gdx.graphics.getHeight() - fon.getHeight();

        size.add(Math.max(x, y), Math.max(x, y));

        TextureRegion tr = new TextureRegion(fon);
        background = new Fon(tr, new Vector2(0, 0), size);

        // Текст результата
        String str = (code >= 0 && code < texts.length) ? texts[code] : texts[0];
        Skin labelSkin = new Skin(Gdx.files.internal("UI/AboutGame/label.json"));

        conclusionText = new Label(str, labelSkin);
        conclusionText.setFontScale(2f);

        // Количество монет из БД
        coinsText = new Label("", labelSkin);
        coinsText.setFontScale(2f);
        updateCoinsDisplay();

        // Кнопка старта
        skin = new Skin(Gdx.files.internal("UI/MainMenu/skinPlayButton.json"));
        Button buttonStart = new Button(skin);

        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                backgroundMusic.stop();
                center.setScreen(new GameScreen(center));
            }
        });

        // UI-компоновка
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(coinsText).pad(10).row();
        table.add(conclusionText).pad(80).row();
        table.add(buttonStart).height(290).width(630);

        stage.addActor(background);
        stage.addActor(table);

        // Музыка
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/lobby.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
    }

    private void updateCoinsDisplay() {
        int coins = controller.getUserState().getCoins();
        coinsText.setText("Монеты: " + coins);
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundMusic.dispose();
    }
}
