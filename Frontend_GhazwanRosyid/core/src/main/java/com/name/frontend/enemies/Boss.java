package com.name.frontend.enemies;

import com.name.frontend.items.Item;
import com.name.frontend.objects.Collidable;
import com.name.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;
//Hierarchical inheritance for Enemy
//Multilevel inheritance from GameObject
//Hybrid inheritance
public class Boss extends Enemy {
    public Boss(String name, int hp){
        super(380,400,48,48,Color.BLUE,name,hp,5000L);
    }
    public Boss(float x, float y, String name, int hp) {
        super(x,y,48,48,Color.BLUE,name,hp,5000L);
    }

    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if (other instanceof Player) {
            // TODO: Print "Player touches boss"
            System.out.println("Player touches boss");
        }
    }
}
