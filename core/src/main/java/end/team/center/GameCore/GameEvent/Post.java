package end.team.center.GameCore.GameEvent;

import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Enemy;

public interface Post {
    void post(Enemy[] enemy);
    void post(Drops drops);
}
