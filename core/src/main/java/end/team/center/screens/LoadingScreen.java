package end.team.center.screens;

import static end.team.center.MyGame.isFieldScreenLoaded;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import end.team.center.MyGame;

public class LoadingScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layout = new GlyphLayout();
    public final float layoutX;
    public final float layoutY;
    private boolean isFieldScreenCreated = false;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private FieldScreen fieldScreen;
    private final MyGame game;

    public LoadingScreen(MyGame game) {
        this.game = game;

        font.getData().setScale(2.0f);
        String loadingText = "Подождите, идёт загрузка...";
        layout.setText(font, loadingText);
        layoutX = (Gdx.graphics.getWidth() - layout.width) / 2;
        layoutY = (Gdx.graphics.getHeight() - layout.height) / 2;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                fieldScreen = new FieldScreen(game);
                isFieldScreenCreated = true;
            }
        }, 1);
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
        batch.end();

        if (isFieldScreenCreated) {
            fieldScreen.show();
            isFieldScreenCreated = false;
        }
        if (isFieldScreenLoaded) {
            game.setScreen(fieldScreen);
        }
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
