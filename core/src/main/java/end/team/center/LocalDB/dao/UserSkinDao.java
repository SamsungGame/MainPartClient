package end.team.center.LocalDB.dao;


public interface UserSkinDao {
    void unlockSkin(int skinId);
    boolean isSkinUnlocked(int skinId);
}
