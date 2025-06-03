package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class MainMenuScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layout = new GlyphLayout();
    private final Stage stage;
    private final Skin skin;

    public MainMenuScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        TextButton newGameButton = new TextButton("Новая игра", skin);
        newGameButton.setSize(300, 150);
        newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - newGameButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - newGameButton.getHeight() / 2 - newGameButton.getHeight() / 2);

        TextButton achievementsButton = new TextButton("Достижения", skin);
        achievementsButton.setSize(300, 150);
        achievementsButton.setPosition(Gdx.graphics.getWidth() / 2 - achievementsButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - achievementsButton.getHeight() * 1.5f - 15);

        TextButton settingsButton = new TextButton("Настройки", skin);
        settingsButton.setSize(300, 150);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2 - settingsButton.getWidth() / 2,
            achievementsButton.getY() - achievementsButton.getHeight() / 2 - 15);

        TextButton aboutUsButton = new TextButton("О нас", skin);
        aboutUsButton.setSize(200, 150);
        aboutUsButton.setPosition(50, 0);

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

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        aboutUsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new AboutUsScreen(game));
            }
        });

        stage.addActor(newGameButton);
        stage.addActor(achievementsButton);
        stage.addActor(settingsButton);
        stage.addActor(aboutUsButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        font.getData().setScale(5.0f);
        String aboutUsText = "Temporal Forest";
        layout.setText(font, aboutUsText);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = Gdx.graphics.getHeight() - layout.height;

        batch.begin();
        font.draw(batch, layout, x, y);
        batch.end();

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
        batch.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
    }
}
