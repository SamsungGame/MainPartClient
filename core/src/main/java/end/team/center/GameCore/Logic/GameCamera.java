package end.team.center.GameCore.Logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Gdx;


public class GameCamera {

    private OrthographicCamera camera;

    private float worldWidth;
    private float worldHeight;

    // Размеры экрана (понадобятся для ограничения камеры)
    private float screenWidth;
    private float screenHeight;

    public GameCamera(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
    }

    public void updateCameraPosition(float heroX, float heroY, float heroWidth, float heroHeight) {
        float heroCenterX = heroX + heroWidth / 2f;
        float heroCenterY = heroY + heroHeight / 2f;

        float halfScreenWidth = screenWidth / 2f;
        float halfScreenHeight = screenHeight / 2f;

        // Ограничиваем позицию камеры, чтобы не выйти за края мира
        float cameraX = MathUtils.clamp(heroCenterX, halfScreenWidth, worldWidth - halfScreenWidth);
        float cameraY = MathUtils.clamp(heroCenterY, halfScreenHeight, worldHeight - halfScreenHeight);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Обновляет размеры экрана (вызывать в resize)
     */
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
}
