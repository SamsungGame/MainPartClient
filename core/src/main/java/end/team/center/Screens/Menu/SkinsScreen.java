package end.team.center.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.Center;
import end.team.center.ProgramSetting.Config;
import end.team.center.ProgramSetting.LocalDB.GameData;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Game.GameScreen;

public class SkinsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("UI/AboutGame/pixel_font.fnt"));
    private final GlyphLayout layout = new GlyphLayout();
    public final float layoutX;
    public final float layoutY;
    private final Stage stage;
    private final Skin skin;
    private final Skin leftButtonSkin;
    private final Skin rightButtonSkin;
    private final Image currentImage; // Отображаемый Image
    private int currentIndex;
    private float touchStartX;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("UI/MainMenu/fon.jpg"));
    private final Label buyButton;
    private GameRepository repo;
    private boolean twoSkinBy = false;

    public SkinsScreen(GameRepository repo) {
        this.repo = repo;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                touchStartX = screenX;
                return stage.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float deltaX = screenX - touchStartX;
                if (Math.abs(deltaX) > 50) {
                    if (deltaX > 0) {
                        showPreviousImage();
                    }
                    else {
                        showNextImage();
                    }
                    return true;
                }
                return stage.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return stage.touchDragged(screenX, screenY, pointer);
            }
        });

        skin = new Skin(Gdx.files.internal("UI/MainMenu/backButton.json"));

        leftButtonSkin = new Skin();
        leftButtonSkin.add("button_up", new Texture(Gdx.files.internal("UI/Achivements/left.png")));
        ImageButton.ImageButtonStyle leftButtonStyle = new ImageButton.ImageButtonStyle();
        leftButtonStyle.imageUp = leftButtonSkin.getDrawable("button_up");

        rightButtonSkin = new Skin();
        rightButtonSkin.add("button_up", new Texture(Gdx.files.internal("UI/Achivements/right.png")));
        ImageButton.ImageButtonStyle rightButtonStyle = new ImageButton.ImageButtonStyle();
        rightButtonStyle.imageUp = rightButtonSkin.getDrawable("button_up");

        font.getData().setScale(4.0f);

        String skinsText = "Скины";
        layout.setText(font, skinsText);
        layoutX = (Gdx.graphics.getWidth() - layout.width) / 2;
        layoutY = Gdx.graphics.getHeight() - layout.height;

        ImageButton backButton = new ImageButton(skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());

        ImageButton leftButton = new ImageButton(leftButtonStyle);
        leftButton.setSize(100, 100);
        leftButton.setPosition(100, Gdx.graphics.getHeight() / 2 - leftButton.getHeight() / 2);

        ImageButton rightButton = new ImageButton(rightButtonStyle);
        rightButton.setSize(100, 100);
        rightButton.setPosition(Gdx.graphics.getWidth() - rightButton.getWidth() - 100, Gdx.graphics.getHeight() / 2 - rightButton.getHeight() / 2);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (repo.getSkins().get(currentIndex + 1)) {
                    repo.setCurrentSelectedSkinId(currentIndex);


                    if (currentIndex == 0) {
                        Config.skinIsKnight = false;
                        Config.skinIsCyber = false;
                        Config.skinIsGhost = false;
                    } else if (currentIndex == 1) {
                        Config.skinIsCyber = false;
                        Config.skinIsKnight = false;
                        Config.skinIsGhost = true;
                    } else if (currentIndex == 2) {
                        Config.skinIsGhost = false;
                        Config.skinIsCyber = false;
                        Config.skinIsKnight = true;
                    }
                    else if (currentIndex == 3) {
                        Config.skinIsKnight = false;
                        Config.skinIsGhost = false;
                        Config.skinIsCyber = true;
                    }
                }
                ((Center) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(GameScreen.endCode, repo));

            }
        });

        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showPreviousImage();
            }
        });

        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showNextImage();
            }
        });


        currentIndex = Center.currentSkin - 1; // Начинаем с первого изображения

        // Создаём Image для отображения текущего изображения
        currentImage = new Image(new TextureRegionDrawable(MainMenuScreen.images[currentIndex]));
        currentImage.setSize(180, 192);
        currentImage.setPosition(Gdx.graphics.getWidth() / 2 - currentImage.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - currentImage.getHeight() / 2);


        buyButton = new Label("Купить: 0", new Skin(Gdx.files.internal("UI/AboutGame/label.json")));
        buyButton.setSize(200, 150);
        buyButton.setFontScale(2f);
        buyButton.setPosition(Gdx.graphics.getWidth() / 2 - buyButton.getPrefWidth() / 2, Gdx.graphics.getHeight() / 2 - currentImage.getY());

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentIndex == 1 && repo.getCoins() >= 200 && !repo.getSkins().get(currentIndex + 1)) {
                    repo.unlockSkin(currentIndex + 1);
                    repo.spendCoins(200);
                }
                if (currentIndex == 2 && repo.getCoins() >= 400 && !repo.getSkins().get(currentIndex + 1)) {
                    repo.unlockSkin(currentIndex + 100);
                    repo.spendCoins(400);
                }
                if (currentIndex == 3 && repo.getCoins() >= 500 && !repo.getSkins().get(currentIndex + 1)) {
                    repo.unlockSkin(currentIndex + 1);
                    repo.spendCoins(500);
                }
            }
        });

        stage.addActor(backButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(currentImage);

        stage.addActor(buyButton);
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % MainMenuScreen.images.length; // Циклический переход
        updateImage();
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + MainMenuScreen.images.length) % MainMenuScreen.images.length; // Циклический переход назад
        updateImage();
    }

    private void updateImage() {
        currentImage.setDrawable(new TextureRegionDrawable(MainMenuScreen.images[currentIndex]));
        Center.currentSkin = currentIndex + 1;
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

        if (!repo.getSkins().get(currentIndex + 1)) {
            buyButton.setText("Купить: " + Center.prices.get(Center.currentSkin - 1));

        }
        else {
            buyButton.setText("Получено!");
        }
        buyButton.setPosition(Gdx.graphics.getWidth() / 2 - buyButton.getPrefWidth() / 2, Gdx.graphics.getHeight() / 2 - currentImage.getY());

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
        leftButtonSkin.dispose();
        rightButtonSkin.dispose();
        backgroundTexture.dispose();
    }
}
