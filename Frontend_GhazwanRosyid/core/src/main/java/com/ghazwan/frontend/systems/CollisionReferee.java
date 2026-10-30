package com.ghazwan.frontend.systems;

import com.ghazwan.frontend.objects.GameObject;
import com.ghazwan.frontend.objects.Player;
import com.ghazwan.frontend.objects.bullets.Bullet;
import com.ghazwan.frontend.objects.enemies.Enemy;
import com.ghazwan.frontend.objects.items.Item;
import com.ghazwan.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        // Collision logic is divided into 3 stages
        //
        // TODO: Code for Question 3
        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;
            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        // Core Business Logic:
                        // Enemy takes damage, player gets score, etc.
                        enemy.takeDamage(bullet.getDamage());
                        player.addScore(enemy.getScoreValue());
                        // Bullet gets marked to be destroyed
                        bullet.isDestroyed();
                        newDrops.add(EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER));
                    }
                }
            }
        }
        // TODO: Code for Question 4
        //
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;
            for (GameObject entity : entities) {
                if (!player.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                        // Core Business Logic:
                        // Enemy takes damage, player gets score, etc.
                        player.takeDamage(bullet.getDamage());
                        // Bullet gets marked to be destroyed
                        bullet.isDestroyed();
                    }
                    if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                        // Core Business Logic:
                        // Enemy takes damage, player gets score, etc.
                        bullet.setGrazed(true);
                        // Bullet gets marked to be destroyed
                        player.addScore(50);
                    }
                }
            }
        }
        // TODO: Code for Question 5
// 3. Evaluate Player vs Items and Physical Enemy Contact
// TODO Code for Question 5:
// - Loop through each entity in entities:
//   - If the entity is already destroyed, skip it (continue)
//
//   - CASE A: Item Collection
//     - Check whether entity is an instance of Item item
//     - Check whether the item's Core Hitbox touches the player's Core Hitbox OR Graze Hitbox.
//     - If YES: call player.collectItem(item)
//
//   - CASE B: Physical Contact with Enemy
//     - Check whether entity is an instance of Enemy enemy
//     - If the player's Core Hitbox collides with the enemy's Core Hitbox.
//     - If YES: deal 10 body collision damage to the player: player.takeDamage(10)
        for (GameObject entity : entities) {
            if (entity.isDestroyed()) continue;
            if(entity instanceof Item item){
                if (item.getCoreHitbox().overlaps(player.getGrazeHitbox())){
                    player.collectItem(item);
                }
            }
            if(entity instanceof Enemy enemy){
                if (enemy.getCoreHitbox().overlaps(player.getCoreHitbox())){
                    player.takeDamage(10);
                }
            }
        }

        entities.addAll(newDrops);
    }

}
