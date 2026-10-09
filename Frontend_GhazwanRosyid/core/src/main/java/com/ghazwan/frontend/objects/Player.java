package com.ghazwan.frontend.objects;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ghazwan.frontend.objects.bullets.BulletType;
import com.ghazwan.frontend.objects.enemies.Enemy;
import com.ghazwan.frontend.objects.items.Item;
import com.ghazwan.frontend.objects.items.ItemType;
import com.ghazwan.frontend.objects.bullets.Bullet;
import com.ghazwan.frontend.systems.AssetManager;
import com.ghazwan.frontend.systems.EntityFactory;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int maxHp;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir;
    public Player(String name, int hp, int power, int spellCards){
        super(280,40,32,48,200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,48,200f, Color.RED);
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

        if (item.isDestroyed()) return; // Prevent the item from being collected twice in the same frame
        // ... switch-case for the item type that you created previously ...
        // TODO: Mark this item as destroyed so it can later be removed by the Iterator
        // Call the item's destroy() method here!
        item.destroy();
    }

    @Override
    public void update(float delta) {
        // TODO 1: Call GameObject's update(delta) through super.
        super.update(delta);

        // TODO 2: Declare a local float variable dx with an initial value of 0
        // (dx = delta x, tracks the change in horizontal direction for animation)
        float dx = 0;
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x -= speed * delta;
                // TODO 3: Adjust dx to match the direction.
                // (If you move left, what should happen to dx?)
                dx -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x += speed * delta;
                // TODO 4: Adjust dx to match the direction.
                // (If you move right, what should happen to dx?)
                dx += 1;
            }
        }

        // TODO 5: Call updateAnimationState(dx)
        updateAnimationState(dx);
    }

    public void updateAnimationState(float dx) {
        AssetManager assets = AssetManager.getInstance();
        if (dx < 0) {
            // TODO:
            // 1. Only make the following changes if currentDir is not -1.
            if (currentDir != -1) {
                // 2. Set currentDir to -1.
                currentDir = -1;
            }
            // 3. Retrieve the "player_left" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            Animation<TextureRegion> anim = assets.getAnimation("player_left");
            // 4. If anim is not null, assign it using setAnimation(...).
            if (anim!=null){
                setAnimation(anim);
            }
        } else if (dx > 0) {
            // TODO:
            // 1. Only make the following changes if currentDir is not 1.
            if (currentDir != 1) {
                // 2. Set currentDir to -1.
                currentDir = 1;
            }
            // 3. Retrieve the "player_right" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            Animation<TextureRegion> anim = assets.getAnimation("player_right");
            // 4. If anim is not null, assign it using setAnimation(...).
            if (anim!=null){
                setAnimation(anim);
            }
        } else {
            // TODO:
            // 1. Only make the following changes if currentDir is not 0.
            // 2. Set currentDir to 0.
            if (currentDir != 0) {
                // 2. Set currentDir to -1.
                currentDir = 0;
            }
            // 3. Retrieve the "player_idle" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            Animation<TextureRegion> anim = assets.getAnimation("player_right");
            // 4. If anim is not null, assign it using setAnimation(...).
            if (anim!=null){
                setAnimation(anim);
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

    //============================
    //Module 4
    //============================

    public Bullet shootBullet() {
        int damage = 10 + power;
        System.out.println(name + " shoots bullet dealing " + damage + " DMG!");
        // TODO: Return a Bullet using EntityFactory
        // with the same x and y formulas as in the previous implementation
        return EntityFactory.createPlayerBullet(x,y,damage);
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
