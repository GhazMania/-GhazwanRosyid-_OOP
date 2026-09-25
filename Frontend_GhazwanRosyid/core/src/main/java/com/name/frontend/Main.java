package com.name.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.name.frontend.enemies.Boss;
import com.name.frontend.enemies.Fairy;
import com.name.frontend.items.Item;
import com.name.frontend.items.ItemType;
import com.name.frontend.objects.GameObject;
import com.name.frontend.objects.Player;
import com.badlogic.gdx.Input;
import com.name.frontend.objects.bullets.Bullet;

import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    // TODO 1: Declare fields for Player, Fairy, Boss, Items, and List<GameObject>
    Player playerObject;
    Fairy fairyObject;
    Boss bossObject;
    Item itemObject;
    Item powerItem;
    Item pointItem;
    List<GameObject> entities;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();

        // TODO 2: Instantiate Player (Red square) at (280, 40)
        playerObject = new Player(280,40,"Red Square", 100, 15,3);

        // TODO 3: Instantiate Fairy (Pink square) at (150, 380)
        fairyObject = new Fairy(150, 380, "Pink Square", 100);


        // TODO 4: Instantiate Boss (Blue square) at (380, 400)
        bossObject = new Boss(380, 400, "Blue Square", 100);


        // TODO 5: Instantiate Items (White squares) with downward speeds
        itemObject = new Item(300,300,"String");
        powerItem = new Item(200, 450, 16, 16, 80f, ItemType.POWER, 500L);
        pointItem = new Item(320, 480, 12, 12, 120f, ItemType.POINT, 1000L);


        // TODO 6: Add all entities into the gameObjects list polymorphically
        entities.add(playerObject);
        entities.add(fairyObject);
        entities.add(bossObject);
        entities.add(itemObject);
        entities.add(powerItem);
        entities.add(pointItem);

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
            if (target.isOffScreen(screenWidth,screenHeight)){
                System.out.println("Removed via Generic Iterator: "+ target.getClass().getSimpleName());
                itr.remove();
            }
        }

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // TODO 1: If the Z key was just pressed, add a new bullet from player.shootBullet()
        // to the entities list.
        // Clue: Gdx.input.isKeyJustPressed()
        if (Gdx.input.isKeyPressed(Input.Keys.Z)){
            entities.add(playerObject.shootBullet());
        }

        // TODO 2: Call updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        // to update and clean up destroyed/off-screen entities.
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 3. Collision detection between entities (skip entities that are already destroyed)
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                if (!a.isDestroyed() && !b.isDestroyed()) {
                    if (a.getCoreHitbox().overlaps(b.getCoreHitbox())) {
                        a.onCollision(b);
                        b.onCollision(a);
                    }
                }
            }
        }

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
    }



    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
