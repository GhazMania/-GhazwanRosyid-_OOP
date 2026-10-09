package com.ghazwan.frontend.objects.enemies;

import com.ghazwan.frontend.objects.Collidable;
import com.ghazwan.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;

//Hierarchical inheritance for Enemy
//Multilevel inheritance from GameObject
//Hybrid inheritance
public class Boss extends Enemy {
    public Boss(String name, int hp){
        super(380,400,64,64,Color.BLUE,name,hp,5000L);
    }
    public Boss(float x, float y, String name, int hp) {
        super(x,y,64,64,Color.BLUE,name,hp,5000L);
    }

    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if (other instanceof Player) {
            // TODO: Print "Player touches boss"
            System.out.println("Player touches boss");
        }
    }
}
