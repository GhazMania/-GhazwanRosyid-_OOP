package com.ghazwan.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ghazwan.frontend.objects.enemies.Boss;
import com.ghazwan.frontend.objects.enemies.Fairy;
import com.ghazwan.frontend.objects.items.Item;
import com.ghazwan.frontend.objects.items.ItemType;
import com.ghazwan.frontend.objects.GameObject;
import com.ghazwan.frontend.objects.Player;
import com.badlogic.gdx.Input;
import com.ghazwan.frontend.systems.*;

import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;

import static com.ghazwan.frontend.systems.EntityFactory.*;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    // TODO 1: Declare fields for Player, Fairy, Boss, Items, and List<GameObject>
    Player playerObject;
    Fairy fairyObject;
    Boss boss;
    Item itemObject;
    Item powerItem;
    Item pointItem;
    List<GameObject> entities;
    private float enemyShootTimer = 0f;
    BulletManager bulletManager;
    CollisionReferee collisionReferee;

    @Override
    public void create() {
        // TODO 1:
        // When initializing the renderer, create a SpriteBatch and store it in batch.
        // LibGDX hint: new SpriteBatch().
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // TODO 2:
        // Initialize the fairy and entities lists as empty ArrayLists.
        entities = new ArrayList<>();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        // TODO 3:
        // Before creating entities, get the AssetManager instance and call init().
        AssetManager.getInstance().init();
        SoundManager.getInstance().init();

        // TODO 4:
        // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
        // using the appropriate EntityFactory methods.
        // Add both Fairies to the fairy list using add(...).
        entities.add(createFairy(150,380,"Red Fairy", 20));
        entities.add(createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));

        // TODO 5:
        // Add all the objects you have just created to entities.
        playerObject = createPlayer(280, 40, "Reimu Hakurei", 100, 15, 3);
        entities.add(playerObject);
        entities.add(createBoss(380, 400, "Cirno", 150));
        entities.add(createItem(200, 450, ItemType.POWER));
        entities.add(createItem(320, 480, ItemType.POINT));
    }


    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        // 1. Get an Iterator<T> from the given list.
        // 2. While there are still elements available (hasNext()):
        //    a. Get the current element using next() and store it in a variable of type T.
        //    b. Call update(delta) on the element.
        //    c. If the element is off-screen (isOffScreen(screenWidth, screenHeight))
        //       OR isDestroyed():
        //       - Display the message: "Removed via Generic Iterator: " + [entity class name, using getClass().getSimpleName()]
        //       - Remove the element from the list using the Iterator's method
        //         (NOT list.remove()!).

        Iterator<T> itr = list.iterator();
        while(itr.hasNext())
        {
            T target = itr.next();
            target.update(delta);
            if (target.isOffScreen(screenWidth,screenHeight)|| target.isDestroyed()){
                System.out.println("Removed via Generic Iterator: "+ target.getClass().getSimpleName());
                itr.remove();
            }
        }

    }

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        //TEMPORARY ADDITIONAL ASSIGNMENT
        SoundManager.getInstance().playMusic("badapple");

        // TODO 1: If the Z key was just pressed, add a new bullet from player.shootBullet()
        // to the entities list.
        // Clue: Gdx.input.isKeyJustPressed()z
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            playerObject.shootBullet(bulletManager);
        }

        // TODO: Periodic firing scheduler for Boss Rumia
        updateEnemyScheduler(delta);

        // TODO: Generic update & clean for standard entities (Player, Enemy, Item)
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // TODO: Update and recycle Object Pool
        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // TODO: Evaluate & resolve all collisions via
        // Mediator Pattern
        collisionReferee.resolveCollisions(playerObject,entities,bulletManager);

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject entity : entities) {
            // TODO 3: Use an if statement to check whether the entity has not been destroyed (!entity.isDestroyed()).
            // If so, call entity.render(shapeRenderer);
            if(!entity.isDestroyed()){
                entity.render(shapeRenderer);
            }
        }
        shapeRenderer.end();

        // ... Keep the code above unchanged
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
                entity.render(batch);
            }
        }

        // TODO: Render all active bullets from the pool
        bulletManager.render(batch);

        batch.end();
    }



    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }

        // TODO: Call dispose on AssetManager to release the loaded Textures as well.
        AssetManager.getInstance().dispose();
        SoundManager.getInstance().dispose();
    }
}
