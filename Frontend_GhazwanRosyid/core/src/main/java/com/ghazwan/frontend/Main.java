package com.ghazwan.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ghazwan.frontend.commands.Command;
import com.ghazwan.frontend.commands.InputHandler;
import com.ghazwan.frontend.objects.GameObject;
import com.ghazwan.frontend.objects.Player;
import com.ghazwan.frontend.objects.enemies.Boss;
import com.ghazwan.frontend.objects.enemies.Fairy;
import com.ghazwan.frontend.objects.items.Item;
import com.ghazwan.frontend.objects.items.ItemType;
import com.ghazwan.frontend.objects.patterns.entityStrategy.TargetPointMovement;
import com.ghazwan.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.ghazwan.frontend.systems.AssetManager;
import com.ghazwan.frontend.systems.BulletManager;
import com.ghazwan.frontend.systems.CollisionReferee;
import com.ghazwan.frontend.systems.EntityFactory;
import com.ghazwan.frontend.ui.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;

    private float enemyShootTimer = 0f;
    private float phaseTimer = 0f;
    private int currentPhase = 0; // 0: Upper Left (Aimed SpreadShot), 1: Upper Right (AlternatingRingShot)


    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    private InputHandler inputHandler;
    private GameHUD gameHUD;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        inputHandler = new InputHandler();
        gameHUD = new GameHUD();

        // 1. Centralized Asset Registration (AssetManager Singleton + Flyweight)
        AssetManager.getInstance().init();

        // 2. Instantiate entities via Factory Pattern (EntityFactory)
        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        bulletManager.setEntities(entities);
        // TODO (3 points): Inject initial strategies into Boss Rumia
        boss.setMovementPattern(new TargetPointMovement(200f, 420f, 150f));
        boss.setShootingPattern(new SpreadShot(220f, 5, 25f, 10, player, "bullet_danmaku_red"));

        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        // 3. Register GameHUD as Observer to Player (Observer Pattern)
        player.registerObserver(gameHUD);

        entities.add(player);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        // 4. Configure InputHandler with entities list for Bomb drops
        inputHandler.setEntities(entities);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 0. Restart Game on 'R' key when Game Over
        if (Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.R) && player.isGameOver()) {
            restartGame();
        }

        // 1. Process Input via Command Pattern (Bob Nystrom Invoker Command Stream)
        List<Command> commands = inputHandler.handleInput(delta);
        for (Command command : commands) {
            command.execute(player, bulletManager);
        }

        // 2. Periodic Enemy Action Scheduler (Foreshadows Module 9 strategy updates)
        updateEnemyScheduler(delta);

        // 3. Generic update & safe removal of standard entities
        updateAndClean(entities, delta, 416, 560);

        // 4. Object Pool Update (BulletManager)
        bulletManager.update(delta, 416, 560);

        // 5. Mediator Pattern Collision Resolution (CollisionReferee - Core vs Graze detection)
        collisionReferee.resolveCollisions(player, entities, bulletManager);

        // 6. Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 7. Render active entity sprites, pooled bullets, and Observer UI HUD with SpriteBatch
        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }
        bulletManager.render(batch);
        gameHUD.render(batch);
        batch.end();

        // 8. Render UI Frame Lines & Focus Mode Core Hurtbox Indicator (Drawn ON TOP of sprites!)
        gameHUD.renderBackground();
        player.renderFocusIndicator(gameHUD.getShapeRenderer());
    }

    // Periodic Enemy Action Scheduler (Foreshadows Module 9 dynamic strategy updates)
    private void updateEnemyScheduler(float delta) {
        // 1. Update fairy wave
        updateFairyWave(delta);
        fairy.removeIf(f -> f.isDestroyed() || f.isOffScreen(416, 560));

        // 2. Periodic Boss Rumia Shooting via Method Injection (Every 1.5 seconds)
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f && boss != null && !boss.isDestroyed()) {
            // TODO (4 points): Command boss to shoot!
            enemyShootTimer = 0f;
        }

        // 3. Dynamic Strategy Swapping for Rumia Every 4.0 Seconds
        phaseTimer += delta;
        if (phaseTimer >= 4.0f && boss != null && !boss.isDestroyed()) {
            phaseTimer = 0f;
            currentPhase = (currentPhase + 1) % 2;

            if (currentPhase == 0) {
                // Phase 1: Move to Upper Left (120, 420) & Aimed SpreadShot pattern (Red Fan)
                // TODO (1 point): Inject Movement Pattern into boss as TargetPointMovement to coordinates (120f, 420f) with speed 150f
                boss.setMovementPattern(new TargetPointMovement(120f,420f,150f));
                // TODO (1 point): Inject Shooting Pattern into boss as SpreadShot with speed 220f, 5 bullets, angle 25f, DMG 10, target player, and sprite "bullet_danmaku_red"
                boss.setShootingPattern(new SpreadShot(220f,5,25f,10,player,"bullet_danmaku_red"));
                System.out.println("[Boss Strategy] Rumia shifted to Upper Left with Aimed SpreadShot!");
            } else {
                // Phase 2: Move to Upper Right (280, 420) & AlternatingRingShot pattern (Ring Nova)
                // TODO (1 point): Inject Movement Pattern into boss as TargetPointMovement to coordinates (280f, 420f) with speed 150f
                // TODO (1 point): Inject Shooting Pattern into boss as AlternatingRingShot with baseSpeed 180f, 16 bullets per ring, and DMG 10
                System.out.println("[Boss Strategy] Rumia shifted to Upper Right with AlternatingRingShot!");
            }
        }
    }


    // Stage 1: Wave 1 Fairy Stream
    private float waveTimer = 0f;
    private int wave1SpawnIndex = 0;

    private void updateFairyWave(float delta) {
        float[] spawnX = {60f, 68f, 76f, 84f, 92f};
        waveTimer += delta;
        if (wave1SpawnIndex < spawnX.length && waveTimer >= 1.0f + wave1SpawnIndex * 0.267f) {
            float x = spawnX[wave1SpawnIndex++];
            Fairy f = EntityFactory.createFairy(x, 480f, "Left S-Curve Fairy", 8, "fairy_idle_blue");

            // TODO (2 points): Set Movement Pattern on f as SCurveMovement with speed 130f, initialAngle (float) Math.toRadians(270), and mirrored false
            // TODO (1 point): Set Shooting Pattern on f as null

            fairy.add(f);
            entities.add(f);
        }
    }

    private void resetFairyWave() {
        waveTimer = 0f;
        wave1SpawnIndex = 0;
    }



    private void restartGame() {
        System.out.println("[Main] Restarting game...");
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(player);
        player.resetGame();

        fairy.clear();
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
    }

    // Generic Instance Method with Bounded Type Parameter <T extends GameObject>
    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                System.out.println("Removed via Generic Iterator: " + entity.getClass().getSimpleName());
                iterator.remove(); // Safe removal using Iterator!
            }
        }
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (gameHUD != null) {
            gameHUD.dispose();
        }
        AssetManager.getInstance().dispose();
    }
}
