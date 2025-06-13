package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;

import end.team.center.GameCore.UIElements.Power;
import end.team.center.GameCore.UIElements.PowerElement;

public class PowerSelectScreen implements Screen {
    private Label label;
    private Stage stage;
    private SpriteBatch batch;
    private ArrayList<PowerElement> buffs = new ArrayList<>();
    private Image portal;
    private Table table;
    private boolean buffSelected = false;
    private boolean finishedAnimation = false;

    public PowerSelectScreen(Power[] powers) {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        label = new Label("", new Skin(Gdx.files.internal("UI/AboutGame/label.json")));
        label.setFontScale(1.7f);

        portal = new Image(new Texture(Gdx.files.internal("UI/GameUI/Structure/portal3.png")));
        portal.setSize(190, 210);

        table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(new Actor()).width(portal.getWidth()).height(portal.getHeight());
        table.add(portal).width(portal.getWidth()).height(portal.getHeight()).pad(60);
        table.add(new Actor()).width(portal.getWidth()).height(portal.getHeight()).row();

        for (int i = 0; i < powers.length; i++) {
            final int index = i;
            Power power = powers[i];

            PowerElement element = new PowerElement(
                power.getUnActiveTexture(),
                power,
                portal,
                power.getDescp()
            );

            element.setSize(130, 130);
            element.setTouchable(Touchable.enabled);

            element.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    selectBuff(index);
                }
            });

            buffs.add(element);
        }

        for (int i = 0; i < buffs.size(); i++) {
            table.add(buffs.get(i)).width(130).height(130).pad(20);
            if ((i + 1) % 3 == 0) {
                table.row();
            }
        }

        stage.addActor(table);
        stage.addActor(label);

        setLabelPos();
        Gdx.input.setInputProcessor(stage);
    }

    private void selectBuff(int id) {
        PowerElement selected = buffs.get(id);

        if (!buffSelected) {
            buffSelected = true;
            selected.isSelected = true;
            selected.setTexture(selected.getPower().getActiveTexture());
            label.setText(selected.getDescription());
            setLabelPos();
        } else if (selected.isSelected) {
            selected.setOnAnimationFinished(() -> finishedAnimation = true);
            selected.startAnimation();

            for (int i = 0; i < buffs.size(); i++) {
                if (i != id) {
                    buffs.get(i).startSmallAnimation();
                }
            }

            System.out.println("Выбрано!");
        } else {
            for (PowerElement element : buffs) {
                if (element.isSelected) {
                    element.isSelected = false;
                    element.setTexture(element.getPower().getUnActiveTexture());
                }
            }
            selected.isSelected = true;
            selected.setTexture(selected.getPower().getActiveTexture());
            label.setText(selected.getDescription());
            setLabelPos();
        }

        table.invalidate();
    }

    private void setLabelPos() {
        label.setPosition(
            (float) Gdx.graphics.getWidth() / 2 - label.getPrefWidth() / 2,
            80
        );
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.begin();
        batch.end();
        stage.draw();

        if (finishedAnimation) {

        }
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    public boolean isFinished() {
        return finishedAnimation;
    }

    public Stage getStage() {
        return stage;
    }
}
