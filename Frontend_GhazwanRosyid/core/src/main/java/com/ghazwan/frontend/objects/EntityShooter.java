package com.ghazwan.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.ghazwan.frontend.objects.patterns.ShootingPattern;
import com.ghazwan.frontend.objects.patterns.entityStrategy.EntityMovementPattern;
import com.ghazwan.frontend.systems.BulletManager;

public abstract class EntityShooter extends GameObject {

    protected ShootingPattern shootingPattern;
    protected EntityMovementPattern movementPattern;

    public EntityShooter(float x, float y, float width, float height, float speed, Color color) {
        super(x, y, width, height, speed, color);
    }

    public EntityShooter(float x, float y, float width, float height, float speed, Color color,
                         ShootingPattern shootingPattern, EntityMovementPattern movementPattern) {
        super(x, y, width, height, speed, color);
        this.shootingPattern = shootingPattern;
        this.movementPattern = movementPattern;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateMovement(delta);
    }

    // TODO (3 points): Delegate movement to movementPattern
    // - Perform null-check validation on movementPattern and ensure active is true
    // - Call move() on movementPattern passing this object (this) and delta
    public void updateMovement(float delta) {
        if(movementPattern!=null && active){
            movementPattern.move(this,delta);
        }
    }

    // TODO (3 points): Delegate uniform bullet shooting to shootingPattern
    // - Perform null-check validation on shootingPattern, ensure active is true, and !isDestroyed()
    // - Evaluate whether this entity is an instance of Player: boolean isPlayer = (this instanceof Player);
    // - Calculate spawn coordinates: originX = x + width / 2f - 8f, originY = y + (isPlayer ? height : 0f)
    // - Call execute() on shootingPattern
    public void shootBullet(BulletManager bulletManager) {
        if(shootingPattern!=null && active && !isDestroyed()){
            boolean isPlayer = (this instanceof Player);
            float originX = x + width / 2f - 8f;
            float originY = y + (isPlayer ? height : 0f);
            shootingPattern.execute(originX, originY, bulletManager, isPlayer);
        }
    }

    // TODO (2 points): Implement getters & setters for shootingPattern and movementPattern

    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    public EntityMovementPattern getMovementPattern() {
        return movementPattern;
    }

    public void setMovementPattern(EntityMovementPattern movementPattern) {
        this.movementPattern = movementPattern;
    }
}
