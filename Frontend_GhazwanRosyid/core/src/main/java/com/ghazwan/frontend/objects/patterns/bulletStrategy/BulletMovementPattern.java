package com.ghazwan.frontend.objects.patterns.bulletStrategy;

import com.ghazwan.frontend.objects.bullets.Bullet;

public interface BulletMovementPattern {
    // TODO (4 points): Declare the `move` method with the contract and parameters described above.
    public void move(Bullet bullet, float delta);
}
