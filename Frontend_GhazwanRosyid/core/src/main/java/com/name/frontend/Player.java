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
        setHp(getHp() - damage);
        // 2. HP must not become negative.
        // 4. If HP reaches 0, display a message that the Player has been defeated.
    }
    public void shoot(Enemy target) {
        // 1. Create an int named damage, calculated by adding 10 to power.
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
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
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public void collectItem(Item item) {
        System.out.println(getName() + " collected " + item.getItemType() + "!");
        if (item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
        }
    }


    //Getters and Setters
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
    public void setPower(int power){
        this.power=power;
    }
    public int getPower(){
        return power;
    }
    public void setSpellCards(int spellCards){
        this.spellCards=spellCards;
    }
    public int getSpellCards(){
        return spellCards;
    }

    public long getScore(){
        return score;
    }

}
