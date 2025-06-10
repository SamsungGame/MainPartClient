package end.team.center.GameCore.Objects.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import end.team.center.GameCore.Objects.OnMap.StaticObject;

public class Tree extends StaticObject {

    public Tree(Texture texture, Vector2 vector, float height, float width, boolean canWalk) {
        super(texture, vector, height, width, canWalk);

        bound = new Rectangle(vector.x + width / 3.4f, vector.y, width / 3.4f, height / 2.4f);

        // Отключаем обработку касаний, чтобы не мешать вводу джойстика
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getStage() == null) return;

        OrthographicCamera camera = (OrthographicCamera) getStage().getCamera();

        float camLeft = camera.position.x - camera.viewportWidth / 2 * camera.zoom;
        float camRight = camera.position.x + camera.viewportWidth / 2 * camera.zoom;
        float camBottom = camera.position.y - camera.viewportHeight / 2 * camera.zoom;
        float camTop = camera.position.y + camera.viewportHeight / 2 * camera.zoom;

        // Рисуем только если дерево в зоне видимости камеры
        if (getX() + getWidth() < camLeft || getX() > camRight ||
            getY() + getHeight() < camBottom || getY() > camTop) {
            return;
        }

        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBound() {
        return bound;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Можно сюда добавить логику дерева, если нужно
    }
}
