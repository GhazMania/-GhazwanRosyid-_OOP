package com.ghazwan.frontend.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ghazwan.frontend.objects.Player;
import com.ghazwan.frontend.objects.bullets.Bullet;
import com.ghazwan.frontend.objects.bullets.BulletType;
import com.ghazwan.frontend.objects.enemies.Boss;
import com.ghazwan.frontend.objects.enemies.Fairy;
import com.ghazwan.frontend.objects.items.Item;
import com.ghazwan.frontend.objects.items.ItemType;

public class EntityFactory {

    // Create a Player and assign the 'player_idle' animation from AssetManager
    public static Player createPlayer(float x, float y, String name, int hp, int power, int spellCards) {
        Player player = new Player(x, y, name, hp, power, spellCards);
        Animation<TextureRegion> anim = AssetManager.getInstance().getAnimation("player_idle");
        player.setAnimation(anim);
        return player;
    }

    // Create an Item and assign its sprite from AssetManager based on ItemType
    public static Item createItem(float x, float y, ItemType itemType) {
        Item item = new Item(x, y, itemType);
        String key = switch (itemType) {
            case POWER -> "item_power";
            case POINT -> "item_point";
            case BOMB  -> "item_bomb";
            case LIFE  -> "item_life";
        };
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(key);
        item.setSprite(sprite);
        return item;
    }

    // Create an enemy bullet (type DANMAKU, speed 0f, sprite bullet_danmaku)
    public static Bullet createEnemyBullet(float x, float y, int damage) {
        Bullet bullet = new Bullet(x, y, 0f, BulletType.DANMAKU, damage);
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
        bullet.setSprite(sprite);
        return bullet;
    }

    public static Boss createBoss(float x, float y, String name, int hp) {
        // TODO:
        // 1. Create a new Boss using x, y, name, and hp from the parameters;
        //    store it in a local variable named `boss`.
        Boss boss = new Boss(x,y,name,hp);
        // 2. Retrieve the "boss_idle" animation using getAnimation(...)
        //    from AssetManager.getInstance(). Store the result in
        //    a local variable named `idleAnim`.
        Animation idleAnim = AssetManager.getInstance().getAnimation("boss_idle");
        // 3. Assign idleAnim to boss using boss.setAnimation(...).
        boss.setAnimation(idleAnim);
        // 4. Return boss.
        return boss;
    }

    public static Fairy createFairy(float x, float y, String name, int hp) {
        // TODO:
        // 1. Create a new Fairy using x, y, name, and hp from the parameters;
        //    store it in a local variable named `fairy`.
        Fairy fairy = new Fairy(x,y,name,hp);
        // 2. Retrieve the "fairy_idle_red" animation using getAnimation(...)
        //    from AssetManager.getInstance(). Store the result in
        //    a local variable named `idleAnim`.
        Animation idleAnim = AssetManager.getInstance().getAnimation("fairy_idle_red");
        // 3. Assign idleAnim to fairy using fairy.setAnimation(...).
        fairy.setAnimation(idleAnim);
        // 4. Return fairy.
        return fairy;
    }

    public static Fairy createFairy(float x, float y, String name, int hp, String keyString) {
        // TODO:
        // 1. Create a new Fairy using x, y, name, and hp from the parameters;
        //    store it in a local variable named `fairy`.
        Fairy fairy = new Fairy(x,y,name,hp);
        // 2. Retrieve the animation for keyString using getAnimation(...)
        //    from AssetManager.getInstance(). Store the result in
        //    a local variable named `idleAnim`.
        Animation idleAnim = AssetManager.getInstance().getAnimation(keyString);
        // 3. Assign idleAnim to fairy using fairy.setAnimation(...).
        fairy.setAnimation(idleAnim);
        // 4. Return fairy.
        return fairy;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage, String spriteKey) {
        // TODO 1: Retrieve the TextureRegion for spriteKey using getTextureRegion from
        // AssetManager.getInstance(), then store it in a local variable named `sprite`.
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(spriteKey);

        // TODO 2:
        // Create a new Bullet using x, y, BulletType.AMULET, and damage;
        // store it in a local variable named `bullet`.
        Bullet bullet = new Bullet(x,y,damage,BulletType.AMULET,damage);

        // TODO 3:
        // Assign sprite to bullet using bullet.setSprite(...).
        bullet.setSprite(sprite);

        // TODO 4:
        // Return bullet.
        return bullet;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage) {
        // TODO 5:
        // Return the result of calling the previous createPlayerBullet overload with "bullet_amulet" as spriteKey.

        return createPlayerBullet(x,y,damage,"bullet_amulet");
    }


}
