package com.name.frontend;

import java.awt.*;
//Hierarchical inheritance for Enemy
//Multilevel inheritance from GameObject
//Hybrid inheritance
public class Boss extends Enemy {
    public Boss(String name, int hp){
        super(380,400,48,48,Color.blue,name,hp,5000L);
    }
    public Boss(float x, float y, String name, int hp) {
        super(x,y,48,48,Color.blue,name,hp,5000L);
    }
}
