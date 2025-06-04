package end.team.center.screens;

import static end.team.center.MyGame.currentSkin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class MainMenuScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layout = new GlyphLayout();
    public final float layoutX;
    public final float layoutY;
    private final Stage stage;
    private final Skin skin;
    private final Skin skinsButtonSkin;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private final Texture skinTexture = new Texture(Gdx.files.internal("skins/heroLeft.png"));
    private final Texture nightSkinTexture = new Texture(Gdx.files.internal("skins/heroNightLeft.png"));

    public MainMenuScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));
        skinsButtonSkin = new Skin();
        skinsButtonSkin.add("button_up", new Texture(Gdx.files.internal("buttons/skinsButton.png")));
        skinsButtonSkin.add("button_down", new Texture(Gdx.files.internal("buttons/skinsButton.png")));
        ImageButton.ImageButtonStyle skinsButtonStyle = new ImageButton.ImageButtonStyle();
        skinsButtonStyle.imageUp = skinsButtonSkin.getDrawable("button_up");
        skinsButtonStyle.imageDown = skinsButtonSkin.getDrawable("button_down");

        font.getData().setScale(5.0f);
        String titleText = "Temporal Forest";
        layout.setText(font, titleText);
        layoutX = (Gdx.graphics.getWidth() - layout.width) / 2;
        layoutY = Gdx.graphics.getHeight() - layout.height;

        TextButton newGameButton = new TextButton("Новая игра", skin);
        newGameButton.setSize(300, 150);
        newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - newGameButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - newGameButton.getHeight());

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

        ImageButton skinsButton = new ImageButton(skinsButtonStyle);
        skinsButton.setSize(100, 100);
        skinsButton.setPosition(Gdx.graphics.getWidth() - skinsButton.getWidth() * 2, 50);

        Image skinImage = new Image(skinTexture);
        skinImage.setSize(200, 200);
        skinImage.setPosition(skinsButton.getX() - skinsButton.getWidth() / 2, skinsButton.getY() + skinsButton.getHeight() + 40);

        Image nightSkinImage = new Image(nightSkinTexture);
        nightSkinImage.setSize(200, 200);
        nightSkinImage.setPosition(skinsButton.getX() - skinsButton.getWidth() / 2, skinsButton.getY() + skinsButton.getHeight() + 40);

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

        skinsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SkinsScreen(game));
            }
        });

        stage.addActor(newGameButton);
        stage.addActor(achievementsButton);
        stage.addActor(settingsButton);
        stage.addActor(aboutUsButton);
        stage.addActor(skinsButton);
        if (currentSkin == 1) {
            stage.addActor(skinImage);
        }
        else {
            stage.addActor(nightSkinImage);
        }
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
        skinsButtonSkin.dispose();
        backgroundTexture.dispose();
        skinTexture.dispose();
        nightSkinTexture.dispose();
    }
}
