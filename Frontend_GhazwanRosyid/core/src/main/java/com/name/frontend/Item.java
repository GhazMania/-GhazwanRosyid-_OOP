package com.name.frontend;

import java.awt.*;

public class Item extends GameObject {
    private String itemType;
    private long scoreValue;
    public Item(float x, float y, String itemType){
        super(x,y,16,16,100f, Color.WHITE);
        this.scoreValue=1000L;
    }
    public Item(float x, float y, float width, float height, float speed, String itemType){
        super(x,y,width,height,100f, Color.WHITE);
        this.itemType=itemType;
        this.scoreValue=1000L;
    }
    public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue){
        super(x,y,width,height,100f, Color.WHITE);
        this.itemType=itemType;
        this.scoreValue=scoreValue;
    }
    public void update(float delta){
        this.y=this.y-speed*delta;
    }
}
