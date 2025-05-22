package end.team.center.GameCore.GameEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Library.Mobs.ForFrog;
import end.team.center.GameCore.Objects.Enemy;

public class Spawner {
    private final int minRadiusSpawnMobY = Gdx.graphics.getHeight();
    private final int minRadiusSpawnMobX = Gdx.graphics.getWidth();
    private final int maxCountMobInMap = 100;
    private final int maxRadiusSpawn = 1000;
    private int timeSpawn;
    private ArrayList<EnemyType> canSpawn;
    private PostMob poster;

    public Spawner(PostMob poster, int countLocation) {
        this.poster = poster;

        if (countLocation == 0) {
            canSpawn.add(EnemyType.Owl);

            timeSpawn = 10 * 1000;
        } // Добавить больше врагов в список, по мере продвижении по локациям
    }

    public void startWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Enemy[] enemies = new Enemy[canSpawn.size()];

                for(int i = 0; i < canSpawn.size(); i++) {
                    enemies[i] = spawn(canSpawn.get(i));
                }

                poster.post(enemies);
                System.out.println("Отправка мобов: " + Arrays.toString(enemies));

                try {
                    Thread.sleep(timeSpawn);
                } catch (InterruptedException ignore) {}
            }
        });


    }

    private Enemy spawn(EnemyType type) {
        if (type == EnemyType.Owl) {
            return new ForFrog(type, randomCord());
        } else return null;
    }

    private Vector2 randomCord() {
        float zone = (float) (Math.random() * 4);

        Vector2 vector;

        if (zone <= 1) return vector = new Vector2((int) (0 - (Math.random() * maxRadiusSpawn)), (int) (0 + (Math.random() * maxRadiusSpawn)));
        if (zone <= 2) return vector = new Vector2((int) (0 - (Math.random() * maxRadiusSpawn)), (int) (0 - (Math.random() * maxRadiusSpawn)));
        if (zone <= 3) return vector = new Vector2((int) (minRadiusSpawnMobX + (Math.random() * maxRadiusSpawn)), (int) (0 + (Math.random() * maxRadiusSpawn)));
        else           return vector = new Vector2((int) (minRadiusSpawnMobX + (Math.random() * maxRadiusSpawn)), (int) (0 - (Math.random() * maxRadiusSpawn)));
    }

}
