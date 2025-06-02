package end.team.center.android.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_state")
public class UserStateEntity {
    @PrimaryKey
    public int id;

    public int coins;
}

