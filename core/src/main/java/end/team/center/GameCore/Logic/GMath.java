package end.team.center.GameCore.Logic;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class GMath {
    public static boolean circleRectangleOverlap(Circle circle, Rectangle rect) {
        float circleX = circle.x;
        float circleY = circle.y;
        float radius = circle.radius;

        float rectX = rect.x;
        float rectY = rect.y;
        float rectWidth = rect.width;
        float rectHeight = rect.height;

        // Находим ближайшую точку на прямоугольнике к центру окружности
        float closestX = Math.max(rectX, Math.min(circleX, rectX + rectWidth));
        float closestY = Math.max(rectY, Math.min(circleY, rectY + rectHeight));

        // Расстояние от центра окружности до этой точки
        float dx = circleX - closestX;
        float dy = circleY - closestY;

        // Проверка пересечения
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    public static Vector2 vectorDistance(Vector2 first, Vector2 second) {
        return new Vector2(Math.abs(first.x - second.x), Math.abs(first.y - second.y));
    }

    public static boolean checkVectorDistance(Vector2 first, Vector2 second, int x, int y) {
        Vector2 d = vectorDistance(first, second);

        if (d.x <= x && d.y <= y) return true;
        else                      return false;
    }
}
