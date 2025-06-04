package end.team.center.android.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coins")
public class CoinsEntity {
    @PrimaryKey
    public int id = 0; // всегда одна запись с id=0
    public int amount;
}
