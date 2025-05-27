package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.OnMap.Hero;

public class AI_Rabbit extends AI {
    public AI_Rabbit(Hero hero) {
        super(hero);
    }

    @Override
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        return super.MoveToPlayer(hero.getVector(), position, speed, delta);
    }
}
