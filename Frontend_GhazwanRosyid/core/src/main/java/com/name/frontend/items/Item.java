package com.name.frontend.items;

import com.name.frontend.objects.GameObject;

import java.awt.*;

public class Item extends GameObject {
    private String itemType;
    private long scoreValue;
    private ItemType itemTypeEnum;
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

    public Item(float x, float y, ItemType itemTypeEnum){
        super(x,y,16,16,100F,Color.WHITE);
        this.scoreValue=itemTypeEnum.getScoreValue();
        this.itemTypeEnum=itemTypeEnum;
        this.itemType = itemTypeEnum.name();
    }

    public Item(int x, int y,float width, float height, float speed, ItemType itemTypeEnum, int scoreValue){
        super(x,y,16,16,100F,Color.WHITE);
        this.scoreValue=itemTypeEnum.getScoreValue();
        this.itemTypeEnum=itemTypeEnum;
        this.itemType = itemTypeEnum.name();
    }
    @Override
    public void update(float delta){
        this.y=this.y-speed*delta;
    }

    //Getters Setters
    public String getItemType(){
        return itemType;
    }
    public long getScoreValue(){return scoreValue;}
    public ItemType getItemTypeEnum() {
        return itemTypeEnum;
    }
}
