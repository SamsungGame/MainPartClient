package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.GameCore.HelpsClass.PowerElement;

public class PowerSelectScreen implements Screen {
    /*
    Предпологается что png у PowerElements равен 130 на 130 пикселей (картинка (.png) может быть ЛЮБОГО КВАДРАТНОГО размера)
     */
    private Stage stage;
    private SpriteBatch batch;
    private PowerElement[] buffs = new PowerElement[3];
    private Texture selectBuff, unSelectBuff;
    private Image portal;
    private Table table;
    private boolean buffSelected = false;
    private int distance = 30;

    @Override
    public void show() {
        // Создаем сцену
        stage = new Stage(new ScreenViewport());

        selectBuff   = new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/selectPower.png"));
        unSelectBuff = new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/power.png"));

        portal = new Image(new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/portal.png")));

        batch = new SpriteBatch();
        buffs[0] = new PowerElement(unSelectBuff, portal);
        buffs[1] = new PowerElement(unSelectBuff, portal);
        buffs[2] = new PowerElement(unSelectBuff, portal);

        buffs[0].setTouchable(Touchable.enabled);
        buffs[1].setTouchable(Touchable.enabled);
        buffs[2].setTouchable(Touchable.enabled);

        buffs[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectBuff(0);
            }
        });
        buffs[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectBuff(1);
            }
        });
        buffs[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectBuff(2);
            }
        });

        buffs[0].setSize(130, 130);
        buffs[1].setSize(130, 130);
        buffs[2].setSize(130, 130);

        // Создаем таблицу
        table = new Table();
        table.setFillParent(true);
        table.center();

        // Добавляем изображения в таблицу с отступами
        table.add(new Actor())
            .padRight(0)
            .height(portal.getHeight()).width(portal.getWidth());

        table.add(portal)
            .padRight(0)
            .height(portal.getHeight()).width(portal.getWidth())
            .pad(60);

        table.add(new Actor())
            .padRight(0)
            .height(portal.getHeight()).width(portal.getWidth())
            .row();

        table.add(buffs[0])
            .height(130).width(130);

        table.add(buffs[1])
            .height(130).width(130)
            .fillX().center();

        table.add(buffs[2])
            .height(130).width(130);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage); // Устанавливаем обработчик ввода
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        batch.begin();
        batch.end();
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
        unSelectBuff.dispose();
        batch.dispose();
    }

    private void selectBuff(int id) {
        if (!buffSelected) { // Если не был выбран ни один бафф - мы выделяем текущий
            buffSelected = true;
            buffs[id].isSelected = true;
            buffs[id].setTexture(selectBuff);
        }
        else if (buffs[id].isSelected) { // Если мы нажимаем на бафф второй раз - мы выбираем его
            buffs[id].startAnimation();
            for (int i = 0; i < buffs.length; i++) {
                if (i != id) buffs[i].startSmallAnimation();
            }
            System.out.println("Выбрано!");
        }
        else { // Если был выбран другой бафф, но мы нажали на новый, то мы выключаем прошлый и включаем новый
            for (int i = 0; i < buffs.length; i++) {
                if (buffs[i].isSelected) {
                    buffs[i].isSelected = false;
                    buffs[i].setTexture(unSelectBuff);
                }
            }
            buffSelected = true;
            buffs[id].isSelected = true;
            buffs[id].setTexture(selectBuff);
        }
        table.invalidate();
    }
}
