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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.Center;
import end.team.center.GameCore.UIElements.Fon;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Game.GameScreen;

public class MainMenuScreen implements Screen {
    public static GameRepository gameRepository;
    private Stage stage;
    private Skin skin;
    private Fon background;
    private Music backgroundMusic;
    private Label conclusionText, coinsText;
    public static Image activeSkin;

    public static boolean showAchivs = false;
    public static Image imageAchivs;
    public static int idAchivs;
    boolean start = false;

    public int timeShowNewAch = 4; // sec

    private static final String[] texts = {
        "ДОБРО ПОЖАЛОВАТЬ!",
        "Сбегаешь от правды? \n   Победа!",
        "Разве... это спасение? \n     Поражение...",
        "Не это сделало их такими... \n              Поражение..."
    };

    public MainMenuScreen(int code, GameRepository repo) {
        if (activeSkin == null) activeSkin = new Image(new Texture(Gdx.files.internal("UI/GameUI/Hero/Left/heroLeftKnife.png")));
        this.gameRepository = repo;

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

        activeSkin.setSize(300, 320);
        activeSkin.setPosition(Gdx.graphics.getWidth() - activeSkin.getWidth() - 50, (float) Gdx.graphics.getHeight() / 2 - activeSkin.getHeight() / 2 - 20);

        // Текст результата

        String str = (code >= 0 && code < texts.length) ? texts[code] : texts[0];
        Skin labelSkin = new Skin(Gdx.files.internal("UI/AboutGame/label.json"));

        conclusionText = new Label(str, labelSkin);
        conclusionText.setFontScale(2.5f);

        Texture coinT = new Texture("UI/GameUI/OtherGameItems/coin.png");
        Image coinImg = new Image(coinT);

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
                backgroundMusic.dispose();
                ((Center) Gdx.app.getApplicationListener()).setScreen(new GameScreen(repo));
            }
        });

        skin = new Skin(Gdx.files.internal("UI/MainMenu/skinButton.json"));
        ImageButton buttonSkin = new ImageButton(skin);

        buttonSkin.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new SkinsScreen(repo));
            }
        });
        buttonSkin.setSize(140, 140);
        buttonSkin.setPosition(activeSkin.getX() + activeSkin.getWidth() / 2 - buttonSkin.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2 - activeSkin.getHeight() - 20);

        skin = new Skin(Gdx.files.internal("UI/MainMenu/achievButton.json"));
        ImageButton buttonAch = new ImageButton(skin);

        buttonAch.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new AchievementsScreen(repo));
            }
        });
        buttonAch.setSize(120, 120);
        buttonAch.setPosition(buttonAch.getWidth(), buttonAch.getHeight());

        // Таблица для монет в правом верхнем углу
        Table coinsTable = new Table();
        coinsTable.top().right();
        coinsTable.setFillParent(true);

        coinImg.setSize(120, 120);
        coinsTable.add(coinsText).padRight(30).padTop(30);
        coinsTable.add(coinImg).size(120, 120).padTop(30).padRight(60);

        // Основная таблица для текста и кнопки (центр по горизонтали и вертикали)
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        mainTable.add(conclusionText).padBottom(50).row();

        mainTable.add(buttonStart).width(500).height(250);

        // Добавляем на stage сначала фон, затем таблицы
        stage.addActor(background);
        stage.addActor(mainTable);
        stage.addActor(coinsTable);
        stage.addActor(activeSkin);
        stage.addActor(buttonAch);
        stage.addActor(buttonSkin);


        // Музыка
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/lobby.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
    }

    private void updateCoinsDisplay() {
        int coins = gameRepository.getCoins();
        coinsText.setText(coins);
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (showAchivs) showNewAchivs();

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

    public void showNewAchivs() {
        if (!start) {
            imageAchivs.setSize(imageAchivs.getWidth() * 5, imageAchivs.getHeight() * 5);
            imageAchivs.setPosition(Gdx.graphics.getWidth() / 2 - imageAchivs.getWidth() / 2, imageAchivs.getHeight() + 20);
            stage.addActor(imageAchivs);

            start = true;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeShowNewAch * 1000);
                } catch (InterruptedException ignored) {}

                showAchivs = false;
                start = false;

                imageAchivs.remove();

                gameRepository.unlockAchievement(idAchivs);
                idAchivs = -1;
            }
        }).start();
    }
}
