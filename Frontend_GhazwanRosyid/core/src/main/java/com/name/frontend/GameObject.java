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

    public void render(ShapeRenderer shapeRenderer){
    }


}
