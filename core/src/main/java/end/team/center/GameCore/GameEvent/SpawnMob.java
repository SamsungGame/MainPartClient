package end.team.center.GameCore.GameEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Library.Mobs.Ghost;
import end.team.center.GameCore.Library.Mobs.Owl;
import end.team.center.GameCore.Library.Mobs.Rabbit;
import end.team.center.GameCore.Logic.AI.AI_Ghost;
import end.team.center.GameCore.Logic.AI.AI_Owl;
import end.team.center.GameCore.Logic.AI.AI_Rabbit;
import end.team.center.GameCore.Objects.OnMap.Enemy;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class SpawnMob {
    private final int minRadiusSpawnMobY = Gdx.graphics.getHeight();
    private final int minRadiusSpawnMobX = Gdx.graphics.getWidth();
    private final int maxCountMobInMap = 60;
    private int countEnemy = 0;
    private final int maxRadiusSpawn = (int) Math.min(GameScreen.WORLD_HEIGHT, GameScreen.WORLD_WIDTH);
    private float timeSpawn;
    private ArrayList<EnemyType> canSpawn;
    private Post poster;
    private Hero hero;
    private int levelMobSpawn = 0;
    private boolean isSpawn = true;

    public SpawnMob(Post poster, Hero hero) {
        this.poster = poster;
        this.hero = hero;

        canSpawn = new ArrayList<>();
        for(int i = 0; i < 3; i++) { // 3 - коль-во видов мобов
            canSpawn.add(null);
        }

        timeSpawn = 2f;
    }

    public void startWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isSpawn) {
                    setNewMob();

                    if (maxCountMobInMap > countEnemy && !GameScreen.STOP) { // true
                        Enemy[] enemies = new Enemy[canSpawn.size()];

                        for (int i = 0; i < canSpawn.size(); i++) {
                            enemies[i] = spawn(canSpawn.get(i));
                        }

                        poster.post(enemies);

                        System.out.println("Отправка мобов: " + Arrays.toString(enemies));

                        try {
                            Thread.sleep((long) (timeSpawn * 2000L));
                        } catch (InterruptedException ignore) {}
                    }
                }
            }
        }).start();
    }

    protected void setNewMob() {
        if (GameScreen.totalTime % 20 == 0) {
            levelMobSpawn = (int) (GameScreen.totalTime / 15);
            System.out.println("Уровень врагов: " + levelMobSpawn);
        }

        if        (GameScreen.totalTime >= 5 && canSpawn.get(0) == null) {
            System.out.println("Новый моб: Rabbit");
            canSpawn.set(0, EnemyType.Rabbit);
        } else if (GameScreen.totalTime >= 15 && canSpawn.get(1) == null) {
            System.out.println("Новый моб: Owl");
            canSpawn.set(1, EnemyType.Owl);
        } else if (GameScreen.totalTime >= 25 && canSpawn.get(2) == null) {
            System.out.println("Новый моб: Ghost");
            canSpawn.set(2, EnemyType.Ghost);
        }
    }

    private Enemy spawn(EnemyType type) {
        if (type == null) return null;

        if        (type == EnemyType.Owl) {
            return new Owl(type, new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_down.png")), randomCord(), levelMobSpawn, GameScreen.WORLD_HEIGHT, GameScreen.WORLD_WIDTH, new AI_Owl(hero));
        } else if (type == EnemyType.Ghost) {
            return new Ghost(type, new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostWalk.png")), randomCord(), levelMobSpawn, GameScreen.WORLD_HEIGHT, GameScreen.WORLD_WIDTH, new AI_Ghost(hero), new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ghostAttack.png"))));
        } else if (type == EnemyType.Rabbit) {
            return new Rabbit(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Hare/hareLeft.png")), type, randomCord(), levelMobSpawn, GameScreen.WORLD_HEIGHT, GameScreen.WORLD_WIDTH, new AI_Rabbit(hero));
        } else return null;
    }

    private Vector2 randomCord() {
        float zone = (float) (Math.random() * 4);

        Vector2 vector;

        if (zone <= 1) vector = new Vector2((int) (0 - (Math.random() * maxRadiusSpawn / 2)), (int) (0 + (Math.random() * maxRadiusSpawn / 2)));
        if (zone <= 2) vector = new Vector2((int) (0 - (Math.random() * maxRadiusSpawn / 2)), (int) (0 - (Math.random() * maxRadiusSpawn)));
        if (zone <= 3) vector = new Vector2((int) (minRadiusSpawnMobX + (Math.random() * maxRadiusSpawn / 2)), (int) (0 + (Math.random() * maxRadiusSpawn / 2)));
        else           vector = new Vector2((int) (minRadiusSpawnMobX + (Math.random() * maxRadiusSpawn / 2)), (int) (0 - (Math.random() * maxRadiusSpawn / 2)));

        return vector;
    }

    public void dispose() {
        isSpawn = false;
    }

}
