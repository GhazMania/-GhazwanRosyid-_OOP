package com.name.frontend.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

//Single inheritance from GameObject

public abstract class GameObject implements Collidable {
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
        shapeRenderer.setColor(this.color);
        shapeRenderer.rect(this.x,this.y,this.width,this.height);
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
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public Rectangle getCoreHitbox() {
        // TODO: return a new Rectangle matching this object's x, y, width, height
        return new Rectangle(x,y,width,height);
    }

    @Override
    public Rectangle getGrazeHitbox() {
        // TODO: return a Rectangle with +10px padding on every side
        return new Rectangle(x+10,y+10,width+10,height+10);
    }

    @Override
    public void onCollision(Collidable other) {
        // Base collision handler (can be overridden by subclasses that need to react)
    }


}
