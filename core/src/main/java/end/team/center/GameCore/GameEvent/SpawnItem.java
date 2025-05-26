package end.team.center.GameCore.GameEvent;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Acumulattor;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Entity;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class SpawnItem {

    private float minRadiusSpawn = Entity.BOUNDARY_PADDING + 100;
    private final int maxRadiusSpawn = (int) Math.min(GameScreen.WORLD_HEIGHT, GameScreen.WORLD_WIDTH);
    private Post poster;
    private Hero hero;
    private ItemType[] canDrop;
    private int timeSpawn;

    public SpawnItem(Post poster, Hero hero) {
        this.poster = poster;
        this.hero = hero;

        canDrop = new ItemType[2];
        canDrop[0] = ItemType.accumulator;
        canDrop[1] = ItemType.BreakIron;

        timeSpawn = 25;
    }

    public void goWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!GameScreen.STOP) {
                        setTimeSpawn();

                        poster.post(spawn());

                        try {
                            Thread.sleep(timeSpawn * 1000L);
                        } catch (InterruptedException ignore) {
                        }
                    }
                }
            }
        }).start();
    }
    @SuppressWarnings("NewApi")
    public Drops spawn() {
        Random random = new Random();
        int r = random.nextInt(1, 2);

        if(r == 1) {
            return new Acumulattor(ItemType.accumulator, setPosition(), hero);
        } else {
            return new Acumulattor(ItemType.accumulator, setPosition(), hero);
        }
    }

    @SuppressWarnings("NewApi")
    public Vector2 setPosition() {
        Random random = new Random();

        return new Vector2(random.nextInt((int) minRadiusSpawn, (int) (GameScreen.WORLD_WIDTH - minRadiusSpawn)), random.nextInt((int) minRadiusSpawn, (int) (GameScreen.WORLD_HEIGHT - minRadiusSpawn)));
    }

    private void setTimeSpawn() {
        if (GameScreen.totalTime <= 0 && timeSpawn > 20) {
            System.out.println("Время спавна вещей: 20");
            timeSpawn = 20;
        } else if (GameScreen.totalTime <= 50 && timeSpawn > 10) {
            System.out.println("Время спавна вещей: 10");
            timeSpawn = 10;
        } else if (GameScreen.totalTime <= 100 && timeSpawn > 5) {
            System.out.println("Время спавна вещей: 5");
            timeSpawn = 5;
        }
    }
}
