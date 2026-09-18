package com.name.frontend.enemies;

import com.name.frontend.objects.Collidable;
import com.name.frontend.objects.Player;

import java.awt.*;
//Hierarchical inheritance for Enemy
//Multilevel inheritance from GameObject
//Hybrid inheritance
public class Fairy extends Enemy {
    public Fairy(String name, int hp){
        super(150,380,24,24,Color.pink,name,hp,500L);
    }
    public Fairy(float x, float y, String name, int hp) {
        super(x, y, 24, 24, Color.pink, name, hp, 500L);
    }

    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if (other instanceof Player) {
            // TODO: Print "Player touches fairy"
            System.out.println("Player touches fairy");
        }
    }
}
