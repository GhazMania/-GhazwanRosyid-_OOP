package com.ghazwan.frontend.objects.patterns;

import com.ghazwan.frontend.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}
