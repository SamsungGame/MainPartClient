package end.team.center.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import end.team.center.Center;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Game.GameScreen;

public class LoadingScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("UI/AboutGame/pixel_font.fnt"));
    private final GlyphLayout layout = new GlyphLayout();
    public final float layoutX;
    public final float layoutY;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("UI/MainMenu/fon.jpg"));
    private GameScreen fieldScreen;
    private ProgressBar bar;
    private float progress;

    public LoadingScreen(GameRepository repo) {
        font.getData().setScale(2.0f);
        String loadingText = "Подождите, идёт загрузка...";
        layout.setText(font, loadingText);
        layoutX = (Gdx.graphics.getWidth() - layout.width) / 2;
        layoutY = (Gdx.graphics.getHeight() - layout.height) / 2;

        Timer.schedule(new Task() {
            @Override
            public void run() {
                fieldScreen = new GameScreen(repo);
            }
        }, 1);

        Timer.schedule(new Task() {
            @Override
            public void run() {
                ((Center) Gdx.app.getApplicationListener()).setScreen(fieldScreen);
            }
        }, 10);

        bar = new ProgressBar(0, 10000, 1, false, new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/expProgress.json")));
        bar.setSize(bar.getWidth() * 5, bar.getHeight());
        bar.setPosition((float) Gdx.graphics.getWidth() / 2 - bar.getWidth() / 2, (float) Gdx.graphics.getHeight() / 5);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, layout, layoutX, layoutY);

        progress += delta * 1000;
        bar.setValue(Math.min(progress, 9950));
        bar.draw(batch, 1f);
        batch.end();
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
        backgroundTexture.dispose();
    }
}
