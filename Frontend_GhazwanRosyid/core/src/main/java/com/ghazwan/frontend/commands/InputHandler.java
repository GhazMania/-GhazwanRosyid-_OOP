package com.ghazwan.frontend.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ghazwan.frontend.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    // 1. Key Configuration
    // TODO 1: Create private int fields for:
    // - keyShoot
    // - keyBomb
    // - keyFocus
    // Assign each attribute a key from Input.Keys according to your preference. (Example: `int keyShoot = Input.Keys.Z`)
    // These are the keyboard keys you will press to perform those actions.
    private int keyShoot=Input.Keys.Z;
    private int keyBomb=Input.Keys.C;
    private int keyFocus=Input.Keys.SHIFT_LEFT;

    // 2. Pre-allocated Command Slots (Zero-GC Invoker)
    // TODO 2: Create private fields for:
    // - shootCommand
    // - bombCommand
    // - focusOn initialized with parameter `true`
    // - focusOff initialized with parameter `false`
    // - moveCommand, specifically declared as `final` for MoveCommand with initial parameters (0, 0)!
    // Match each field type to its corresponding Command Pattern class and instantiate it with `new`. (Example: `ShootCommand shootCommand = new ShootCommand()`)

    private ShootCommand shootCommand = new ShootCommand();
    private BombCommand bombCommand= new BombCommand();
    private FocusCommand focusOn = new FocusCommand(true);
    private FocusCommand focusOff= new FocusCommand(false);
    private MoveCommand moveCommand= new MoveCommand(0,0);

    // TODO 3: Create a private final field for a List of Command named activeCommands, and initialize it with a new ArrayList.
    // This field will be used as a buffer for the commands generated during the Case Study.
    private final List<Command> activeCommands = new ArrayList<>();

    public InputHandler() {}

    public InputHandler(List<GameObject> entities) {
        this.bombCommand.setEntities(entities); // Make sure this does not cause an error!
    }

    public void setEntities(List<GameObject> entities) {
        this.bombCommand.setEntities(entities); // Make sure this does not cause an error!
    }

    // Getters and Setters for Key Rebinding
    // TODO 4: Create getters & setters for each key you created in TODO 1.
    public int getKeyShoot() {
        return keyShoot;
    }

    public void setKeyShoot(int keyShoot) {
        this.keyShoot = keyShoot;
    }

    public int getKeyBomb() {
        return keyBomb;
    }

    public void setKeyBomb(int keyBomb) {
        this.keyBomb = keyBomb;
    }

    public int getKeyFocus() {
        return keyFocus;
    }

    public void setKeyFocus(int keyFocus) {
        this.keyFocus = keyFocus;
    }

    public List<Command> handleInput(float delta) {
        activeCommands.clear();
        if (Gdx.input == null) return activeCommands;

        // 1. Focus Mode Toggle (Left or Right Shift) (3 points)
        // TODO:
        // - Check whether keyFocus or SHIFT_RIGHT is currently pressed (isKeyPressed).
        // - Store the result in a boolean variable `isFocusHeld`.
        // - Add to activeCommands: if isFocusHeld then focusOn, otherwise focusOff.
        boolean isFocusHeld = Gdx.input.isKeyPressed(keyFocus) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
        activeCommands.add(isFocusHeld ? focusOn : focusOff);

        // 2. 8-Directional Movement (WASD or Arrow Keys) (5 points)
        float dx = 0;
        float dy = 0;
        // TODO:
        // - If W or UP is pressed (isKeyPressed), increase dy by 1
        // - If S or DOWN is pressed (isKeyPressed), decrease dy by 1
        // - If A or LEFT is pressed (isKeyPressed), decrease dx by 1
        // - If D or RIGHT is pressed (isKeyPressed), increase dx by 1
        // - Calculate float speedMult = isFocusHeld ? 0.45f : 1.0f;
        // - Update displacement: moveCommand.set(dx * delta * speedMult, dy * delta * speedMult);
        // - Add moveCommand to activeCommands.
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx += 1;
        }

        float speedMult = isFocusHeld ? 0.45f : 1.0f;

        moveCommand.set(dx*delta*speedMult,dy*delta*speedMult);

        activeCommands.add(moveCommand);

        // 3. Shoot Command (Hold key for continuous shooting) (3 points)
        // TODO:
        // - Check whether keyShoot is currently pressed (isKeyPressed).
        // - If YES: add shootCommand to activeCommands.
        if(Gdx.input.isKeyPressed(keyShoot))
            activeCommands.add(shootCommand);

        // 4. Bomb / Spell Card Command (Single press) (3 points)
        // TODO:
        // - Check whether keyBomb was just pressed (isKeyJustPressed).
        // - If YES: add bombCommand to activeCommands.
        if(Gdx.input.isKeyJustPressed(keyShoot))
            activeCommands.add(bombCommand);

        return activeCommands;
    }
}
