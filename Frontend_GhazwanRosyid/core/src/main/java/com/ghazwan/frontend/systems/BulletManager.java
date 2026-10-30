package com.ghazwan.frontend.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ghazwan.frontend.objects.bullets.Bullet;
import com.ghazwan.frontend.objects.bullets.BulletType;

import java.util.*;

public class BulletManager {
    // 1. Pool Queues for Object Recycling (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

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
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet; //Confusion
        // TODO:
        // 1. Check whether playerBulletPool is not empty.
        //    If so, poll from the pool. If not, create a new one using the Factory.
        if(!playerBulletPool.isEmpty()){
            bullet = playerBulletPool.poll();
        } else
            bullet = EntityFactory.createPlayerBullet(x, y, damage);
        // 2. Re-initialize the bullet.
        bullet.init(x,y,vx,vy, BulletType.AMULET,damage,true);
        // 3. Add the bullet to the activePlayerBullets list.
        activePlayerBullets.add(bullet);
        // 4. Return the bullet instance.
        return bullet;
    }

    // Borrowing Enemy Bullet from Pool (5 Points)
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        // TODO:
        // 1. Check whether enemyBulletPool is not empty:
        //    If so, poll from the pool. If not, create a new one using the Factory.
        if(!enemyBulletPool.isEmpty()){
            bullet = playerBulletPool.poll();
        } else
            bullet = EntityFactory.createEnemyBullet(x,y,damage);
        // 2. Re-initialize the bullet.
        bullet.init(x,y,vx,vy, BulletType.DANMAKU,damage,false);
        // 3. Add the bullet to the activeEnemyBullets list
        activeEnemyBullets.add(bullet);
        // 4. Return the bullet instance
        return bullet;
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
        while(itr.hasNext())
        {
            Bullet target = itr2.next();
            target.update(delta);
            if (target.isOffScreen(screenWidth,screenHeight)|| target.isDestroyed()){
                System.out.println("Removed via Generic Iterator: "+ target.getClass().getSimpleName());
                itr.remove();
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

    // Helper methods to access pools and active lists
    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }
}

