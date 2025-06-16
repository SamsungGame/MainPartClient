// package end.team.center.android.db;
// CurrentSkinEntity.java

package end.team.center.android.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_skin")
public class CurrentSkinEntity {
    @PrimaryKey
    public int id = 0; // Всегда 0, так как будет только одна запись

    @ColumnInfo(name = "skin_id")
    public int skinId; // ID текущего выбранного скина
}
