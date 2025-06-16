package end.team.center.GameCore.Library.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import end.team.center.Center;
import end.team.center.GameCore.Logic.ShaderManager;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.Objects.OnMap.StaticObject;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Game.GameScreen;
import end.team.center.Screens.Menu.MainMenuScreen;

public class Portal extends StaticObject {
    private GameRepository gameRepository;
    protected Hero hero;
    protected TextureRegion[] t;
    protected Animation<TextureRegion> tr;
    public float stateTime;
    public Portal(GameRepository repo, Texture texture, Texture texture2, Texture texture3, Vector2 vector, Hero hero, float height, float width) {
        super(texture, vector, height, width, true);
        this.gameRepository = repo;
        this.hero = hero;
        t = new TextureRegion[] {
            new TextureRegion(texture),
            new TextureRegion(texture2),
            new TextureRegion(texture3),
        };

        stateTime = 0;

        tr = new Animation<>(0.19f, t);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (hero.getBound().overlaps(bound)) {
            gameRepository.addCoins(((int) GameScreen.coinForGame * 2));
            ((Center) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(1, gameRepository));

            if (!MainMenuScreen.gameRepository.getAchievements().get(1)) {
                MainMenuScreen.showAchivs = true;
                MainMenuScreen.imageAchivs = new Image(new Texture("UI/GameUI/Achievements/open/exit_open.png"));
                MainMenuScreen.idAchivs = 1;
            } else if (!MainMenuScreen.gameRepository.getAchievements().get(2) && !GameScreen.isPickupItem) {
                MainMenuScreen.showAchivs = true;
                MainMenuScreen.imageAchivs = new Image(new Texture("UI/GameUI/Achievements/open/knife_open.png"));
                MainMenuScreen.idAchivs = 2;
            } else if (!MainMenuScreen.gameRepository.getAchievements().get(5) && !GameScreen.isKill) {
                MainMenuScreen.showAchivs = true;
                MainMenuScreen.imageAchivs = new Image(new Texture("UI/GameUI/Achievements/open/clear_open.png"));
                MainMenuScreen.idAchivs = 5;
            }


            ShaderManager.radiusView1 = 0.2f;
            ShaderManager.radiusView3 = 0.15f;
        }

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion drawRegion = tr.getKeyFrame(stateTime, true);

        batch.draw(drawRegion, getX(), getY(), getWidth(), getHeight());
    }
}
