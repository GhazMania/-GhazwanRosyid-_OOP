package com.name.frontend;

public class Enemy {
    String name;
    int hp;
    int maxHp;
    public Enemy(String name, int hp){
        this.name = "Cirno";
        this.hp = 100;
        this.maxHp = 15;
    }
    public void takeDamage(int damage) {
        this.hp = hp-damage;
        // 2. HP must not become negative.
        if(this.hp<0){
            this.hp=0;
            System.out.println("[" +this.name +"] was defeated\n");
        }else {
            System.out.println("["+ this.name +"] took " + damage + " damage! HP: ["+ this.hp +"/"+this.maxHp+"]\n");
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
