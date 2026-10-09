package com.ghazwan.frontend.objects.bullets;

import com.ghazwan.frontend.objects.enemies.Enemy;
import com.ghazwan.frontend.objects.Collidable;
import com.ghazwan.frontend.objects.GameObject;
import com.badlogic.gdx.graphics.Color;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int damage;

    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 8, 16, 400f, Color.YELLOW);
        // TODO: initialize bulletType and damage from the parameters
        this.bulletType=bulletType;
        this.damage=damage;
    }

    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 8, 16, speed, Color.YELLOW);
        // TODO: initialize bulletType and damage from the parameters
        this.bulletType=bulletType;
        this.damage=damage;
    }

    public void update(float delta) {
        // TODO: increase the y position by speed * delta (bullet moves upward)
        this.y = y+speed*delta;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy enemy) {
            // 1. Display a message indicating that the Bullet hit the Enemy in the format:
            //    Bullet hit [EnemyName] for [damage] DMG!
            System.out.println("Bullet hit "+enemy.getName()+" for "+damage+" DMG!");

            // 2. Call the Enemy's takeDamage() method with this Bullet's damage.
            enemy.takeDamage(damage);

            // 3. Destroy the bullet after hitting the Enemy, regardless of the result
            //    (whether the enemy is defeated or still alive), because one bullet
            //    can only hit one target.
            destroy();
        }
    }

}
