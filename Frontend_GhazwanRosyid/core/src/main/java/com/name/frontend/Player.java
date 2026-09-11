package com.name.frontend;

import java.awt.*;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int maxHp;
    private int power;
    private int spellCards;
    private long score;
    public Player(String name, int hp, int power, int spellCards){
        super(280,40,32,32,0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,32,0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }
    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        this.hp = hp-damage;
        // 2. HP must not become negative.
        if(this.hp<0){
            this.hp=0;
            System.out.println("You lose\n");
        }else {
            // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
            System.out.println("["+ this.name +"] took " + damage + " damage! Remaining HP: ["+ this.hp +"]\n");
        }
        // 4. If HP reaches 0, display a message that the Player has been defeated.
    }
    public void shoot(Enemy target) {
        // 1. Create an int named damage, calculated by adding 10 to power.
        int damage = this.power + 10;

        // 2. Display information that the Player is shooting the Enemy, in the format: [name] shoots [TargetName] dealing [damage] DMG!
        System.out.println("[" +this.name+ "] shoots [" + target.name+ "] dealing [" +damage+ "] DMG!");
        // 3. Call the Enemy object's takeDamage() method.
        target.takeDamage(damage);
    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if(this.hp>0){
            return true;
        }else
            return false;
    }
    public void addScore(long points) {
        // TODO: Add the value to the player's score if points is greater than 0.
        if(points>0){
            this.score=this.score+points;
        }
    }

}
