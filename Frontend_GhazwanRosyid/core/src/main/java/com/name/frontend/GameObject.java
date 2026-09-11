package com.name.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.awt.*;

//Single inheritance from GameObject

public class GameObject {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;

    public GameObject(float x, float y, float width, float height, float speed, Color color){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.speed=speed;
        this.color=color;

    }
    public void update(float delta){ //Used for subclass updates
    }
    //Renderer
    public void render(ShapeRenderer shapeRenderer){
    }

    //Setters and Getters
    public void setWidth(float width) {
        if (width > 0) this.width = width;
    }

    public void setHeight(float height) {
        if (height > 0) this.height = height;
    }

    public void setSpeed(float speed) {
        if (speed >= 0) this.speed = speed;
    }

    public void setY(float y){
        this.y=y;
    }
    public void setX(float x){
        this.x=x;
    }

    public void setColor(Color color){
        this.color=color;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public Color getColor(){
        return color;
    }


}
