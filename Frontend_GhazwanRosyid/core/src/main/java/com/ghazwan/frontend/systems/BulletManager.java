package com.ghazwan.frontend.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ghazwan.frontend.objects.GameObject;
import com.ghazwan.frontend.objects.bullets.Bullet;
import com.ghazwan.frontend.objects.bullets.BulletType;
import com.ghazwan.frontend.objects.items.ItemType;
import com.ghazwan.frontend.objects.patterns.bulletStrategy.FantasySealMovement;
import com.ghazwan.frontend.objects.patterns.bulletStrategy.HomingBulletMovement;
import com.ghazwan.frontend.objects.patterns.shootingStrategy.RingShot;

import java.util.*;

public class BulletManager {
    // 1. Pool Queues for Object Recycling (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;
    private List<GameObject> entities;

    // 2. Lists of Active Bullets on Screen
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    public BulletManager() {
        // TODO:
        // 1. Initialize playerBulletPool and enemyBulletPool as empty LinkedLists
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();
        // 2. Initialize activePlayerBullets and activeEnemyBullets as empty ArrayLists
        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
    }

    // Borrowing Player Bullet from Pool (5 Points)
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage, String spriteKey) { // Add String spriteKey as a parameter
        String key = (spriteKey != null) ? spriteKey : "bullet_amulet"; // Add local String variable key
        Bullet bullet;
        if (!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
            bullet.setSprite(AssetManager.getInstance().getTextureRegion(key)); // Add setSprite using key
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage, key); // Add key as 4th argument
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        }
        activePlayerBullets.add(bullet);
        return bullet;
    }

    // [ADDITION] Add the following 5-parameter overloaded method (delegating to the 6-parameter method above):
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        return spawnPlayerBullet(x, y, vx, vy, damage, "bullet_amulet");
    }

    // Borrowing Enemy Bullet from Pool (5 Points)
    // [MODIFY] Update the Module 8 spawnEnemyBullet method with the following changes:
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage, String spriteKey) { // Add String spriteKey as a parameter
        String key = (spriteKey != null) ? spriteKey : "bullet_danmaku"; // Add local String variable key
        Bullet bullet;
        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
            bullet.setSprite(AssetManager.getInstance().getTextureRegion(key)); // Add setSprite using key
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, damage, key); // Add key as 4th argument
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        }
        activeEnemyBullets.add(bullet);
        return bullet;
    }

    // [ADDITION] Add the following 5-parameter overloaded method (delegating to the 6-parameter method above):
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        return spawnEnemyBullet(x, y, vx, vy, damage, "bullet_danmaku");
    }

    public void spawnBombOrbs(float originX, float originY, GameObject targetEnemy, List<GameObject> entities) {

        int orbCount = 8;
        int orbDamage = 35; // 35 damage x 8 orbs = 280 total spell card damage potential!

        List<GameObject> targetEntities = (entities != null) ? entities : this.entities;
        // TODO (2 points): Apply Strategy Composition — instantiate a RingShot object with bulletSpeed 150f, bullet count orbCount, damage orbDamage, and bullet movement strategy FantasySealMovement(targetEnemy, targetEntities), then call execute(originX, originY, this, true)
        RingShot bullet = new RingShot(150f,orbCount,orbDamage, new FantasySealMovement(targetEnemy,targetEntities));
        bullet.execute(originX, originY, this, true);
        System.out.println("[BulletManager] Spawned 8 Bomb Spirit Orbs via RingShot composed with FantasySealMovement!");
    }

    public void spawnBombOrbs(float originX, float originY, GameObject targetEnemy) {
        spawnBombOrbs(originX, originY, targetEnemy, null);
    }


    public void update(float delta, float screenWidth, float screenHeight) {
        // 1. Player bullet recycling & movement (5 Points)
        // TODO:
        // - Copy-paste the iterator loop from updateAndClean() in Main.java
        // - Change the class referenced by the iterator to `Bullet` instead of T
        //   and change the referenced list to activePlayerBullets
        // - Change the check to whether the bullet is off-screen
        //   OR the bullet is already marked as destroyed
        //   - If YES:
        //     * Mark the bullet as destroyed (destroy)
        //     * Safely remove the bullet from the active bullet list
        //     * Return the bullet to the pool queue
        Iterator<Bullet> itr = activePlayerBullets.iterator();
        while(itr.hasNext())
        {
            Bullet target = itr.next();

            if (entities != null && target.getMovementPattern() instanceof HomingBulletMovement homing && homing.getEntities() == null) {
                homing.setEntities(entities);
            }
            target.update(delta);
            if (target.isOffScreen(screenWidth,screenHeight)|| target.isDestroyed()){
                System.out.println("Removed via Generic Iterator: "+ target.getClass().getSimpleName());
                itr.remove();
                target.destroy();
                playerBulletPool.offer(target);
            }
        }

        // 2. Enemy bullet recycling & movement (5 Points)
        // TODO:
        // - Follow the exact same procedure as above for activeEnemyBullets
        // - Enemy bullets that go off-screen or are destroyed are returned to enemyBulletPool via offer()
        Iterator<Bullet> itr2 = activeEnemyBullets.iterator();
        while(itr2.hasNext())
        {
            Bullet target = itr2.next();
            target.update(delta);
            if (target.isOffScreen(screenWidth,screenHeight)|| target.isDestroyed()){
                System.out.println("Removed via Generic Iterator: "+ target.getClass().getSimpleName());
                itr2.remove();
                target.destroy();
                enemyBulletPool.offer(target);
            }
        }
    }



    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    // Spell Card (Bomb) Execution: Clear all active enemy bullets and convert them into POINT item drops!
    public void clearEnemyBullets(List<GameObject> entities) {
        // TODO:
        // 1. Create an Iterator<Bullet> to safely traverse activeEnemyBullets.
        Iterator<Bullet> itr = activeEnemyBullets.iterator();
        // 2. While the iterator has a next element:
        //    - Retrieve the current Bullet object.
        //    - If the entities parameter is not null:
        //      * Create an Item object of type ItemType.POINT using EntityFactory.createItem(...)
        //        at position (bullet.getX(), bullet.getY()), then add it to the entities list.
        //    - Mark the bullet as destroyed (bullet.destroy()).
        //    - Remove the bullet from activeEnemyBullets using remove() on the iterator from step 1.
        //    - Return the bullet to the enemyBulletPool queue using offer().
        // TIP: See the rough outline in `clearPlayerBullets()` below
        while(itr.hasNext())
        {
            Bullet bullet = itr.next();
            if (entities!=null){
                entities.add(EntityFactory.createItem(bullet.getX(), bullet.getY(), ItemType.POINT));
            }
            bullet.destroy();
            itr.remove();
            enemyBulletPool.offer(bullet);
        }
    }

    public void clearEnemyBullets() {
        clearEnemyBullets(null);
    }

    // Clear all player bullets when restarting the game (Copy-Paste):
    public void clearPlayerBullets() {
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.destroy();
            pIter.remove();
            playerBulletPool.offer(bullet);
        }
    }


    // Helper methods to access pools and active lists
    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }
    // [ADDITION] Add the following getters & setters at the bottom of the BulletManager class:
    public List<GameObject> getEntities() { return entities; }
    public void setEntities(List<GameObject> entities) { this.entities = entities; }

}

