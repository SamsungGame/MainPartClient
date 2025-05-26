package end.team.center.GameCore.Library;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import end.team.center.GameCore.Library.Animations.AnimationGhost;
import end.team.center.GameCore.Library.Animations.AnimationsHero;
import end.team.center.GameCore.Library.Animations.AnimationsOwl;

public enum CharacterAnimation {
    Hero(AnimationsHero.rightFrames, AnimationsHero.leftFrames, AnimationsHero.rightStay, AnimationsHero.leftStay),
    Owl(AnimationsOwl.rightTurn, AnimationsOwl.leftTurn, AnimationsOwl.rightDive, AnimationsOwl.leftDive),
    Ghost(AnimationGhost.ghostWalk, AnimationGhost.ghostAttack, AnimationGhost.ammo, AnimationGhost.ghostWalk);

    private TextureRegion[] rightWalk;
    private TextureRegion[] leftWalk;
    private TextureRegion[] rightStay;
    private TextureRegion[] leftStay;

    CharacterAnimation(TextureRegion[] rightWalk, TextureRegion[] leftWalk, TextureRegion[] rightStay, TextureRegion[] leftStay) {
        this.rightWalk = rightWalk;
        this.leftWalk = leftWalk;
        this.leftStay = leftStay;
        this.rightStay = rightStay;
    }

    public TextureRegion[] getRightWalk() {
        return rightWalk;
    }

    public TextureRegion[] getLeftWalk() {
        return leftWalk;
    }

    public TextureRegion[] getLeftStay() {
        return leftStay;
    }

    public TextureRegion[] getRightStay() {
        return rightStay;
    }
}
