package end.team.center.android.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "unlock_state")
public class UnlockStateEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String type;   // "achievement" или "skin"
    public int itemId;    // id ачивки или скина
    public boolean unlocked;
}
