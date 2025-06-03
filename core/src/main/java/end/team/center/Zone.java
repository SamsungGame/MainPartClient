package end.team.center;

import java.util.Random;

public abstract class Zone {
    public int x;
    public int y;
    public int size;
    public String type;
    public int radiationX;
    public int radiationY;
    public int radiationSize;
    Random random = new Random();

    public Zone(int x, int y, int size, String type) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.type = type;
    }
    public void createRadiation() {
        radiationSize = random.nextInt(50);
        if (radiationSize == 0) {
            radiationSize = 50;
        }
        radiationSize += 50;
        radiationX = random.nextInt(size - radiationSize);
        radiationX += x;
        radiationY = random.nextInt(size - radiationSize);
        radiationY += y;
    }
}
