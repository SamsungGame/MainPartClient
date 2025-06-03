package end.team.center.ProgramSetting.LocalDB.dao;


public interface UserSkinDao {
    void unlockSkin(int skinId);
    boolean isSkinUnlocked(int skinId);
}
