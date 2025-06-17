package end.team.center.GameCore.GameEvent;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Acumulattor;
import end.team.center.GameCore.Library.Items.Bandage;
import end.team.center.GameCore.Library.Items.SharpnessStone;
import end.team.center.GameCore.Library.Items.Lamp;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Entity;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class SpawnItem {

    private float minRadiusSpawn = Entity.BOUNDARY_PADDING + 100;
    private final int maxRadiusSpawn = (int) Math.min(GameScreen.WORLD_HEIGHT, GameScreen.WORLD_WIDTH); // Это поле пока не используется в setPosition, но оставлено
    private Post poster;
    private Hero hero;
    private ItemType[] canDrop;
    private float timeSpawn;
    private boolean isSpawn = true;
    private Random random; // Перемещаем инициализацию Random сюда

    public SpawnItem(Post poster, Hero hero) {
        this.poster = poster;
        this.hero = hero;
        this.random = new Random(); // Инициализируем Random один раз при создании объекта

        canDrop = new ItemType[4];
        canDrop[0] = ItemType.accumulator;
        canDrop[1] = ItemType.BreakIron;
        canDrop[2] = ItemType.Bandage;
        canDrop[3] = ItemType.lamp;

        timeSpawn = 0.05f;
    }

    public void goWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Проверяем состояние героя и экрана перед запуском цикла
                if (hero.heroLive && !GameScreen.STOP) { // Изменил || на &&, так как цикл должен работать пока герой жив и игра не остановлена
                    while (isSpawn) {
                        try {
                            poster.post(spawn()); // Отправляем сгенерированный предмет

                            Thread.sleep((long) (timeSpawn * 1000L));
                        } catch (InterruptedException e) {
                            // Обработка прерывания, если поток был остановлен
                            System.out.println("Поток SpawnItem был прерван.");
                            Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
                            break; // Выходим из цикла
                        } catch (Exception e) {
                            // Логируем любые другие неожиданные ошибки, чтобы не замалчивать их
                            System.err.println("Ошибка при спавне предмета: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public Drops spawn() {
        int r = random.nextInt(4); // Используем поле random

        if(r == 0) {
            return new Acumulattor(ItemType.accumulator, setPosition(), hero);
        } else if (r == 1) {
            return new SharpnessStone(ItemType.BreakIron, setPosition(), hero);
        } else if (r == 2) {
            return new Bandage(ItemType.Bandage, setPosition(), hero);
        } else {
            return new Lamp(ItemType.lamp, setPosition(), hero);
        }
    }

    public Vector2 setPosition() {
        // Определяем границы для X-координаты
        int minX = (int) minRadiusSpawn;
        int maxX = (int) (GameScreen.WORLD_WIDTH - minRadiusSpawn);

        // Убедимся, что minX <= maxX, если нет - поменяем местами
        if (minX > maxX) {
            int temp = minX;
            minX = maxX;
            maxX = temp;
        }

        int spawnX;
        // Проверка на корректность диапазона (чтобы избежать IllegalArgumentException в nextInt)
        if ((maxX - minX + 1) <= 0) {
            spawnX = minX; // Если диапазон некорректен или пуст, используем минимальное значение
        } else {
            spawnX = random.nextInt(maxX - minX + 1) + minX;
        }

        // Определяем границы для Y-координаты
        int minY = (int) minRadiusSpawn;
        int maxY = (int) (GameScreen.WORLD_HEIGHT - minRadiusSpawn);

        // Убедимся, что minY <= maxY, если нет - поменяем местами
        if (minY > maxY) {
            int temp = minY;
            minY = maxY;
            maxY = temp;
        }

        int spawnY;
        // Проверка на корректность диапазона
        if ((maxY - minY + 1) <= 0) {
            spawnY = minY; // Если диапазон некорректен или пуст, используем минимальное значение
        } else {
            spawnY = random.nextInt(maxY - minY + 1) + minY;
        }

        return new Vector2(spawnX, spawnY);
    }

    public ArrayList<Drops> startDropSet() {
        ArrayList<Drops> dropsStart = new ArrayList<>();

        for(int i = 0; i < 900; i++) {
            dropsStart.add(spawn());
        }

        return dropsStart;
    }

    public void dispose() {
        isSpawn = false; // Останавливаем поток спавна
    }
}
