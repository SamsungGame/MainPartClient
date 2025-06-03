package end.team.center.ProgramSetting.LocalDB.controllers;


import java.util.List;

import end.team.center.ProgramSetting.LocalDB.dao.SkinDao;
import end.team.center.ProgramSetting.LocalDB.dao.UserSkinDao;
import end.team.center.ProgramSetting.LocalDB.models.Skin;

public class SkinController {

    private final SkinDao skinDao;
    private final UserSkinDao userSkinDao;

    public SkinController(SkinDao skinDao, UserSkinDao userSkinDao) {
        this.skinDao = skinDao;
        this.userSkinDao = userSkinDao;
    }

    public List<Skin> getAllSkins() {
        return skinDao.getAllSkins();
    }

    public void unlockSkin(int skinId) {
        userSkinDao.unlockSkin(skinId);
    }

    public boolean isSkinUnlocked(int skinId) {
        return userSkinDao.isSkinUnlocked(skinId);
    }
}

