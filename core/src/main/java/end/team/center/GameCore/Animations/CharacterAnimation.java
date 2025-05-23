package end.team.center.GameCore.Animations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum CharacterAnimation {
    Hero(AnimationsHero.rightFrames, AnimationsHero.leftFrames, AnimationsHero.rightStay, AnimationsHero.leftStay),
    Owl(AnimationsOwl.rightTurn, AnimationsOwl.leftTurn, AnimationsOwl.rightTurn, AnimationsOwl.leftTurn);

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
