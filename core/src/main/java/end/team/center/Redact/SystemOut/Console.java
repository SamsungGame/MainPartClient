package end.team.center.Redact.SystemOut;

import java.util.logging.Logger;

import end.team.center.GameCore.Library.Other.Portal;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class Console {

    public static final void showPlayerAndPortalCords(Hero hero, Portal portal) {
        System.out.println(
            "--------------------------------" + "\n" +
            "Кординаты портала: " + portal.getCenterVector().x + "/" + portal.getCenterVector().y + "\n" +
            "----" + "\n" +
            "Кординаты игрока: " + hero.getCenterVector().x + "/" + hero.getCenterVector().y + "\n" +
            "--------------------------------");
    }
}
