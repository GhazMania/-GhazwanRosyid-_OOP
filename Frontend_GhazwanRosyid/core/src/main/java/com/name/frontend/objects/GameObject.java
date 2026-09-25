package com.name.frontend.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import static java.lang.Math.abs;

//Single inheritance from GameObject

public abstract class GameObject implements Collidable {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;
    protected boolean active = true;

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
    public void render(ShapeRenderer shapeRenderer) {
        if (shapeRenderer != null && color != null && this.active==true) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(x, y, width, height);
        }
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

    //==============================================
    //Module 4
    //==============================================
    public boolean isDestroyed() {
        // TODO: return true if the object is NOT active (active == false)
        if (this.active==false)
            return true;
        else
            return true;
    }

    public void destroy() {
        // TODO: mark this object as inactive
        this.active=false;
    }

    public boolean isOffScreen(float screenWidth, float screenHeight) {
        // TODO: return true if the x or y position is outside the screen boundaries
        // Use a 50px tolerance margin on each side, so objects that have only
        // slightly passed the edge of the screen are not immediately considered gone.
        if (x>screenWidth+50 || x<-50 || y>screenHeight+50 || y<-50)
            return true;
        else
            return false;
    }


}
