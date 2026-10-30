package com.ghazwan.frontend.objects.bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ghazwan.frontend.objects.enemies.Enemy;
import com.ghazwan.frontend.objects.Collidable;
import com.ghazwan.frontend.objects.GameObject;
import com.badlogic.gdx.graphics.Color;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int damage;
    private float vx;
    private float vy;
    private float angle;
    boolean isPlayerBullet;
    boolean grazed;

    // Constructor with default speed (speed = 400f)
    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 16, 16, 400f, Color.YELLOW);
        // TODO:
        // 1. Initialize bulletType and damage from the parameters
        this.bulletType=bulletType;
        this.damage=damage;
        // 2. Set initial velocity: vx to 0 and vy to 400f (moves straight upward)
        this.vx=0;
        this.vy=400f;
        // 3. Set the visual rotation angle to 90 degrees upward
        this.angle=90f;
        // 4. Mark this bullet as belonging to the player (isPlayerBullet), and set graze status to inactive
        this.isPlayerBullet=true;
        this.grazed=false;
    }

    // Overloaded constructor with custom speed
    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 16, 16, speed, Color.YELLOW);
        // TODO:
        // 1. Initialize bulletType and damage from the parameters
        this.bulletType=bulletType;
        this.damage=damage;
        // 2. Set initial velocity: vx to 0 and vy to speed
        this.vx=0;
        this.vy=speed;
        // 3. Set visual rotation angle: if speed is negative use 270f (facing down), otherwise use 90f
        if(speed<0)
            this.angle=270f;
        else
            this.angle=90f;
        // 4. Mark this bullet as belonging to the player (isPlayerBullet), and set graze status to inactive
        this.isPlayerBullet=true;
        this.grazed=false;
    }


    public void update(float delta) {
        // TODO: increase the y position by speed * delta (bullet moves upward)
        this.x = x+vx*delta;
        this.y = y+vy*delta;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean hasBeenGrazed() {
        return grazed;
    }

    public void setGrazed(boolean grazed) {
        this.grazed = grazed;
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

    //PreCS OOP 7
    // Pool Re-initialization for the Object Pool Pattern
    public void init(float x, float y, float vx, float vy, BulletType bulletType, int damage, boolean isPlayerBullet) {
        // TODO:
        // 1. Update position (x, y) and velocity vector components (vx, vy) from the argument parameters
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        // 2. Calculate the visual rotation angle using Math.atan2 as explained above, then convert it to degrees
        this.angle = (float)Math.toDegrees(Math.atan2(vy, vx));
        // 3. Update speed by calculating the resultant length of both velocity vectors using Math.hypot as explained above
        this.speed = (float) Math.hypot(vx, vy);
        // 4. Save the bullet's identity attributes (bulletType, damage, and isPlayerBullet) from the parameters
        this.isPlayerBullet=isPlayerBullet;
        // 5. Reactivate this bullet and reset its animation state time from 0
        this.stateTime=0f;
        this.active=true;

        // 6. Make sure the grazed status is reset to inactive so this recycled bullet can be grazed again
        this.grazed=false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (sprite != null) {
                // TODO: Draw the bullet sprite using batch.draw() with the following parameters:
                // sprite, position (x, y), rotation pivot origin at center (width / 2f, height / 2f),
                // dimensions (width, height), scale (1f, 1f), and rotation angle (angle - 90f)
                batch.draw(sprite, x, y, width / 2f, height / 2f, width, height, 1f,1f, angle - 90f);
            } else {
                super.render(batch);
            }
        }
    }


}
