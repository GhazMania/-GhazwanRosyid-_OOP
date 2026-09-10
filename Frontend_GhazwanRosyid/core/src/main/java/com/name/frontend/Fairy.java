package com.name.frontend;

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
}
