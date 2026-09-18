package com.name.frontend.enemies;

import com.name.frontend.objects.GameObject;
import com.name.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;

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
        super(x,y,24,24,0, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue=scoreValue;
    }


    public boolean takeDamage(int damage) {
        setHp(getHp() - damage);
        // 2. HP must not become negative.
        if(getHp()<0){
            System.out.println("[" +this.name +"] was defeated\n");
            return true;
        }else {
            System.out.println("["+ this.name +"] took " + damage + " damage! HP: ["+ this.hp +"/"+this.maxHp+"]\n");
            return false;
        }
    }

    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.println("[" +this.name+ "] unleashes bullet barrage on [" +getName()+ "]!\n");
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

    //Setters and Getters
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }
    public int getHp(){
        return hp;
    }

    public void setName(String name) {
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public void setScoreValue(long scoreValue){
        this.scoreValue=scoreValue;
    }
    public long getScoreValue(){
        return scoreValue;
    }


}
