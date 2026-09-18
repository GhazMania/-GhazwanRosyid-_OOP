package com.name.frontend.objects;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.name.frontend.enemies.Enemy;
import com.name.frontend.items.Item;
import com.name.frontend.items.ItemType;

import java.awt.*;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int maxHp;
    private int power;
    private int spellCards;
    private long score;
    public Player(String name, int hp, int power, int spellCards){
        super(280,40,32,32,200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,32,200f, Color.RED);
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
        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    // 1. Increase power by type.getPowerBonus() via this.power
                    this.power += type.getPowerBonus();
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    System.out.println(getName()+"collected POWER item! Power increased to "+ getPower());
                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 2. Print: [name] collected POINT item!
                    System.out.println(getName()+" Collected POINT item!");
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    this.spellCards += 1;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    System.out.println(getName()+" collected BOMB item! SpellCards: "+ getSpellCards());
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    this.hp+= 20;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    System.out.println(getName()+" collected LIFE item! HP: "+ getHp());
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
    }

    @Override
    public void update(float delta) {
        if (Gdx.input != null) {
            // TODO: Check W / UP input   → y += speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.W)){
                y += speed * delta;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                y += speed * delta;
            }
            // TODO: Check S / DOWN input → y -= speed * delta
            else if (Gdx.input.isKeyPressed(Input.Keys.S)){
                y -= speed*delta;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                y -= speed*delta;
            }
            // TODO: Check A / LEFT input → x -= speed * delta
            else if (Gdx.input.isKeyPressed(Input.Keys.A)){
                x -= speed*delta;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                x -= speed*delta;
            }
            // TODO: Check D / RIGHT input → x += speed * delta
            else if (Gdx.input.isKeyPressed(Input.Keys.A)){
                x += speed*delta;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                x += speed*delta;
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        if (other instanceof Item) {
            // TODO: Print "Player touches items" then call collectItem((Item) other)
            collectItem((Item) other);
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
