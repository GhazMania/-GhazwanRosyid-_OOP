package com.ghazwan.frontend.objects.enemies;

import com.ghazwan.frontend.objects.Collidable;
import com.ghazwan.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;
import com.ghazwan.frontend.objects.patterns.ShootingPattern;
import com.ghazwan.frontend.objects.patterns.SpreadShot;
import com.ghazwan.frontend.systems.BulletManager;

//Hierarchical inheritance for Enemy
//Multilevel inheritance from GameObject
//Hybrid inheritance
public class Boss extends Enemy {
    private ShootingPattern shootingPattern = new SpreadShot(200f,3,30f);

    public Boss(String name, int hp){
        super(380,400,64,64,Color.BLUE,name,hp,5000L);
    }
    public Boss(float x, float y, String name, int hp) {
        super(x,y,64,64,Color.BLUE,name,hp,5000L);
    }

    public void shootBullet(BulletManager bulletManager){
        if (shootingPattern!=null){
            shootingPattern.execute(x + width / 2 - 8, y,bulletManager,false);
        }
    }

    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if (other instanceof Player) {
            // TODO: Print "Player touches boss"
            System.out.println("Player touches boss");
        }
    }

    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }
}
