package end.team.center.GameCore.UIElements.UIGameScreenElements;

import static end.team.center.Screens.Game.GameScreen.pauseButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable; // Import Drawable

import java.util.ArrayList;
import java.util.List;

public class TouchpadClass extends Touchpad {

    public static List<TouchpadClass> touchpads = new ArrayList<>();
    public float touchSize = 300;
    public float x = 0;
    public float y = 0;
    public boolean isTouchpadActive;
    private String type;
    private int pointer = -1;

    private final Rectangle pauseButtonBounds = new Rectangle(10, Gdx.graphics.getHeight() - 325, 100, 125);
    private final Rectangle abilityButton = new Rectangle(Gdx.graphics.getWidth() - 400, 40, 200, 200);

    // Keep a reference to the skin to dispose of it later
    private static Skin touchpadSkin;

    public TouchpadClass(float x, float y, boolean isTouchpadActive, String type) {
        super(0.1f, createTouchpadStyle());
        setBounds(x, y, touchSize, touchSize);
        this.x = x + touchSize / 2;
        this.y = y + touchSize / 2;
        this.isTouchpadActive = isTouchpadActive;
        this.type = type;
        touchpads.add(this);
    }

    private static TouchpadStyle createTouchpadStyle() {
        // Only create the skin once since it's static
        if (touchpadSkin == null) {
            touchpadSkin = new Skin();
            touchpadSkin.add("background", new Texture("UI/GameUI/Direction/backTouch.png"));
            touchpadSkin.add("knob", new Texture("UI/GameUI/Direction/knobIMG.png"));
        }

        TouchpadStyle style = new TouchpadStyle();
        // Use getDrawable from the skin directly
        style.background = touchpadSkin.getDrawable("background");
        style.knob = touchpadSkin.getDrawable("knob");

        // Ensure the drawables are of type TextureRegionDrawable to set size
        if (style.knob instanceof TextureRegionDrawable) {
            ((TextureRegionDrawable) style.knob).setMinWidth(100);
            ((TextureRegionDrawable) style.knob).setMinHeight(100);
        } else {
            // Fallback if not TextureRegionDrawable (though it should be with the current setup)
            style.knob.setMinWidth(100);
            style.knob.setMinHeight(100);
        }

        return style;
    }

    public void TouchpadLogic(Stage stage) {
        int maxPointers = 2;

        for (TouchpadClass pad : touchpads) {
            if (pad.pointer != -1 && !Gdx.input.isTouched(pad.pointer)) {
                pad.pointer = -1;
                pad.isTouchpadActive = false;
            }
        }

        for (int i = 0; i < maxPointers; i++) {
            if (!Gdx.input.isTouched(i)) {
                continue;
            }

            Vector2 touch = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i)));
            float touchX = touch.x;
            float touchY = touch.y;


            if (pauseButtonBounds.contains(touchX, touchY) || abilityButton.contains(touchX, touchY)) {
                continue;
            }

            boolean isPointerAlreadyAssigned = false;
            for (TouchpadClass otherPad : touchpads) {
                if (otherPad.pointer == i) {
                    isPointerAlreadyAssigned = true;
                    if (otherPad.equals(this)) {
                        otherPad.isTouchpadActive = true;
                    }
                    break;
                }
            }

            if (isPointerAlreadyAssigned) {
                continue;
            }

            for (TouchpadClass pad : touchpads) {
                boolean isLeftSide = touchX < Gdx.graphics.getWidth() / 2f;
                boolean isRightSide = touchX >= Gdx.graphics.getWidth() / 2f;

                boolean touchMatchesPadSide = (pad.type.equals("move") && isLeftSide) || (pad.type.equals("attack") && isRightSide);

                if (pad.pointer == -1 && touchMatchesPadSide) {
                    pad.setPosition(touchX - pad.getWidth() / 2, touchY - pad.getHeight() / 2);
                    pad.x = touchX;
                    pad.y = touchY;

                    pad.pointer = i;
                    pad.isTouchpadActive = true;


                    Vector2 localCoords = pad.screenToLocalCoordinates(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i)));

                    InputEvent event = new InputEvent();
                    event.setType(InputEvent.Type.touchDown);
                    event.setStageX(touchX);
                    event.setStageY(touchY);
                    event.setPointer(i);
                    event.setButton(0);
                    event.setRelatedActor(pad);


                    pad.fire(event);

                    break;
                }
            }
        }
    }

    public boolean isTouchpadActive() {
        return isTouchpadActive;
    }

    public float getCenterX() { return getX() + getWidth() / 2; }
    public float getCenterY() { return getY() + getHeight() / 2; }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        // Only dispose the static skin once, typically when the game is shutting down
        // or when you are sure no more TouchpadClass instances will be used.
        // If TouchpadClass instances are created and disposed frequently,
        // you might want a more sophisticated skin management.
        if (touchpadSkin != null) {
            // Dispose of the textures held by the skin
            Drawable backgroundDrawable = touchpadSkin.getDrawable("background");
            if (backgroundDrawable instanceof TextureRegionDrawable) {
                ((TextureRegionDrawable) backgroundDrawable).getRegion().getTexture().dispose();
            }

            Drawable knobDrawable = touchpadSkin.getDrawable("knob");
            if (knobDrawable instanceof TextureRegionDrawable) {
                ((TextureRegionDrawable) knobDrawable).getRegion().getTexture().dispose();
            }

            touchpadSkin.dispose();
            touchpadSkin = null;
        }
        // Clear the static list of touchpads if this dispose is meant for global shutdown
        touchpads.clear();
    }
}
