package end.team.center.android.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import end.team.center.android.db.entity.UserStateEntity;

@Dao
public interface UserStateDaoRoom {
    @Query("SELECT * FROM user_state WHERE id = 1")
    UserStateEntity getUserState();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserState(UserStateEntity userState);

    @Query("UPDATE user_state SET coins = coins + :amount WHERE id = 1")
    void addCoins(int amount);
}
