package end.team.center.GameCore.Logic;

import static end.team.center.GameCore.Objects.OnMap.Hero.HeroClassType.CYBER_HERO;
import static end.team.center.GameCore.Objects.OnMap.Hero.HeroClassType.GHOST_HERO;
import static end.team.center.GameCore.Objects.OnMap.Hero.HeroClassType.KNIGHT_HERO;
import static end.team.center.GameCore.Objects.OnMap.Hero.HeroClassType.STALKER_HERO;
import static end.team.center.Screens.Game.GameScreen.WORLD_HEIGHT;
import static end.team.center.Screens.Game.GameScreen.WORLD_WIDTH;
import static end.team.center.Screens.Game.GameScreen.backgroundMusicInstrumental;
import static end.team.center.Screens.Game.GameScreen.energyValue;
import static end.team.center.Screens.Game.GameScreen.expBar;
import static end.team.center.Screens.Game.GameScreen.hearts;
import static end.team.center.Screens.Game.GameScreen.hero;
import static end.team.center.Screens.Game.GameScreen.heroClassType;
import static end.team.center.Screens.Game.GameScreen.hidePowerDialog;
import static end.team.center.Screens.Game.GameScreen.pauseButton;
import static end.team.center.Screens.Game.GameScreen.powers;
import static end.team.center.Screens.Game.GameScreen.radiationValue;
import static end.team.center.Screens.Game.GameScreen.togglePause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.UIElements.AbilityButton;
import end.team.center.GameCore.UIElements.Power;
import end.team.center.GameCore.UIElements.UIGameScreenElements.Heart;
import end.team.center.GameCore.UIElements.UIGameScreenElements.TouchpadClass;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Game.GameScreen;
import end.team.center.Screens.Menu.MainMenuScreen;

public class GameLauncher {

    public GameLauncher() {

    }

    public Hero loadHero(GameRepository repo) {
        int selectedSkinId = repo.getCurrentSelectedSkinId();

        CharacterAnimation characterAnimation;
        Texture heroImage;

        switch (selectedSkinId) {
            case 0:
                characterAnimation = CharacterAnimation.Hero;
                heroImage = MainMenuScreen.images[0];
                heroClassType = STALKER_HERO;
                break;
            case 1:
                characterAnimation = CharacterAnimation.GhostHero;
                heroImage = MainMenuScreen.images[1];
                heroClassType = GHOST_HERO;
                break;
            case 2:
                characterAnimation = CharacterAnimation.Knight;
                heroImage = MainMenuScreen.images[2];
                heroClassType = KNIGHT_HERO;
                break;
            case 3:
                characterAnimation = CharacterAnimation.Cyber;
                heroImage = MainMenuScreen.images[3];
                heroClassType = CYBER_HERO;
                break;
            default:
                characterAnimation = CharacterAnimation.Hero;
                heroImage = MainMenuScreen.images[0];
                heroClassType = STALKER_HERO;
                break;
        }

        return new Hero(
            repo,
            heroImage,
            characterAnimation,
            heroClassType,
            new Vector2(WORLD_WIDTH / 2f - 70, WORLD_HEIGHT / 2f - 80),
            140, 120, 3,
            1, 0, 300f,
            WORLD_WIDTH, WORLD_HEIGHT
        );
    }

    public ArrayList<Actor> generationUI() {
        ArrayList<Actor> a = new ArrayList<>();

        GameScreen.touchpadMove
            = new TouchpadClass(200, 200, false, "move");

        GameScreen.touchpadAttack
            = new TouchpadClass(Gdx.graphics.getWidth() - 500, 200, false, "attack");

        // Определение скина и создание кнопки
        Skin abilitySkin = null;

        if(heroClassType == STALKER_HERO) {
            abilitySkin = new Skin(Gdx.files.internal("UI/GameUI/Direction/abilityStalkerHero.json"));
        }
        else if(heroClassType == GHOST_HERO) {
            abilitySkin = new Skin(Gdx.files.internal("UI/GameUI/Direction/abilityStalkerHero.json"));
        }
        else if(heroClassType == KNIGHT_HERO) {
            abilitySkin = new Skin(Gdx.files.internal("UI/GameUI/Direction/abilityStalkerHero.json"));
        }
        else if(heroClassType == CYBER_HERO) {
            abilitySkin = new Skin(Gdx.files.internal("UI/GameUI/Direction/abilityStalkerHero.json"));
        }
        GameScreen.abilityButton = new AbilityButton(abilitySkin);


        // Создание сердец
        Texture heartFull = new Texture("UI/GameUI/OtherGameItems/heart_full.png");
        Texture heartEmpty = new Texture("UI/GameUI/OtherGameItems/heart_empty.png");
        Texture heartFullBit = new Texture("UI/GameUI/OtherGameItems/heart_full_bit.png");
        hearts = new Heart(heartFull, heartEmpty, heartFullBit, hero.getHealth());

        // Создание шкалы энергии
        Texture EnergyValue = new Texture("UI/GameUI/OtherGameItems/energy.png");
        Image EnergyValueImg = new Image(EnergyValue);
        EnergyValueImg.setSize(70, 98);
        EnergyValueImg.setPosition((float) Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 130);
        a.add(EnergyValueImg);
        // Текст
//        Skin energySkin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/energyText.json"));
        energyValue = new Label(String.valueOf(hero.getAntiRadiationCostumePower()), GameScreen.label);
        energyValue.setFontScale(2.5f);
        energyValue.setPosition(Gdx.graphics.getWidth() - (EnergyValueImg.getWidth() + energyValue.getWidth() + 150),
            Gdx.graphics.getHeight() - (energyValue.getHeight() + EnergyValueImg.getHeight() / 2 + 30));

        // Создание шкалы радиации
        Texture radiationLevel = new Texture("UI/GameUI/OtherGameItems/warning.png");
        Image radiationLevelImg = new Image(radiationLevel);
        radiationLevelImg.setSize(90, 78);
        radiationLevelImg.setPosition((float) Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - (130 + EnergyValueImg.getHeight()));
        a.add(radiationLevelImg);
        // Текст
//        Skin radiationSkin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/energyText.json"));
        radiationValue = new Label(String.valueOf(hero.getAntiRadiationCostumePower()), GameScreen.label);
        radiationValue.setFontScale(2.5f);
        radiationValue.setPosition(Gdx.graphics.getWidth() - radiationLevelImg.getWidth() - radiationValue.getWidth() - 30,
            Gdx.graphics.getHeight() - (energyValue.getHeight() + EnergyValueImg.getHeight() + radiationLevelImg.getHeight() / 2 + 50));

        Texture ExpTexture1 = new Texture("UI/GameUI/OtherGameItems/expBorderLeft.png");
        Image image = new Image(ExpTexture1);
        image.setSize(20, 20);
        image.setPosition((float) Gdx.graphics.getWidth() / 2 - 420, Gdx.graphics.getHeight() - 110);
        a.add(image);

        Skin skin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/expProgress.json"));
        expBar = new ProgressBar(0, hero.getMaxExp(), 1, false, skin);
        expBar.setSize(800, 60);
        expBar.setPosition((float) Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() - 130);

        Texture ExpTexture2 = new Texture("UI/GameUI/OtherGameItems/expBorderRight.png");
        Image image2 = new Image(ExpTexture2);
        image2.setSize(20, 20);
        image2.setPosition((float) Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() - 110);
        a.add(image2);


        Skin pauseSkin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/pauseSkin.json"));
        pauseButton = new ImageButton(pauseSkin);
        pauseButton.setSize(75, 94);
        pauseButton.setPosition(10, Gdx.graphics.getHeight() - pauseButton.getHeight() - 200);

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause(true);
            }
        });


        return a;
    }

    public ArrayList<Power> generatePowers() {
        ArrayList<Power> p = new ArrayList<>();

        Power p1 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/expMore.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/expMore_active.png"),
            "Увеличивает получаемый тобой опыт в 2 раза") {
            @Override
            public void effect() {
                hero.setExpBonus(hero.getExpBonus() * 2);

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p1.setSize(600, 600);
        Power p2 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/HPforAttack.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/HPforAttack_active.png"),
            "Вы получаете вампиризм") {
            @Override
            public void effect() {
                hero.setVampirism(true);

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p2.setSize(600, 600);
        Power p3 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/speedHP.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/speedHP_active.png"),
            "Ваша скорость увеличиваеться в 1.4 раза, но вы теряете 1 максимальное HP") {
            @Override
            public void effect() {
                hero.setSpeed(hero.getSpeed() * 1.5f);

                hero.setMaxHealth(hero.getMaxHealth() - 1);

                if (hero.getHealth() > hero.getMaxHealth()) {
                    hero.setHealth(hero.getMaxHealth());
                }

                // Обновляем UI:
                hearts.setMaxHearts(hero.getMaxHealth());
                hearts.setCurrentHealth(hero.getHealth());


                hidePowerDialog();
                powers.remove(this);
            }
        };
        p3.setSize(600, 600);
        Power p4 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/visible.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/visible_active.png"),
            "Вы увеличиваете свой радиус обзора") {
            @Override
            public void effect() {
                ShaderManager.radiusView1 *= 1.2f;
                ShaderManager.radiusView2 *= 1.2f;
                ShaderManager.radiusView3 *= 1.2f;

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p4.setSize(600, 600);
        Power p5 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/saveHeart.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/saveHeart_active.png"),
            "Вы получаете 1 перерождение в случае смерти") {
            @Override
            public void effect() {
                hero.setSafeInDeadDamage(true);

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p5.setSize(600, 600);
        Power p6 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/aura.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/aura_active.png"),
            "Мобы умирают, если попадают по вам") {
            @Override
            public void effect() {
                hero.setReturnDamage(true);

                powers.remove(this);
                hidePowerDialog();
            }
        };
        p6.setSize(600, 600);
        Power p7 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/timeShield.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/timeShield_active.png"),
            "Вы получаете щит который улучшаетсья каждую минуту \n Улучшения сбрасываються при получении урона!") {
            @Override
            public void effect() {
                hero.activeBuffShield();

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p7.setSize(600, 600);
        Power p8 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/powerForAttack.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/powerForAttack_active.png"),
            "Вы получаете немного энергии за каждый удар по мобам") {
            @Override
            public void effect() {
                hero.activeCollectEnergy();

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p8.setSize(600, 600);

        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p5);
        p.add(p6);
        p.add(p7);
        p.add(p8);

        return p;
    }

    public void loadMusic() {
        backgroundMusicInstrumental = Gdx.audio.newMusic(Gdx.files.internal("Sounds/instrumentalGame.mp3"));
        backgroundMusicInstrumental.setLooping(true);
        backgroundMusicInstrumental.setVolume(0.5f);
        backgroundMusicInstrumental.play();

        GameScreen.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/nightMusic.mp3"));
        GameScreen.backgroundMusic.setLooping(true);
        GameScreen.backgroundMusic.setVolume(0.1f);
        GameScreen.backgroundMusic.play();
    }

}
