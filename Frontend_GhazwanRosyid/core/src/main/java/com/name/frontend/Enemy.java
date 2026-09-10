package com.name.frontend;

import java.awt.*;

public class Enemy extends GameObject {
    String name;
    int hp;
    int maxHp;
    protected long scoreValue;
    public Enemy(String name, int hp){
        super(200,380,24,24,0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue=100;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue){
        super(x,y,24,24,0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue=scoreValue;
    }


    public boolean takeDamage(int damage) {
        this.hp = hp-damage;
        // 2. HP must not become negative.
        if(this.hp<0){
            this.hp=0;
            System.out.println("[" +this.name +"] was defeated\n");
            return true;
        }else {
            System.out.println("["+ this.name +"] took " + damage + " damage! HP: ["+ this.hp +"/"+this.maxHp+"]\n");
            return false;
        }
    }

    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.println("[" +this.name+ "] unleashes bullet barrage on [" +player.name+ "]!\n");
        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }
    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if(this.hp>0){
            return true;
        }else
            return false;
    }

}
