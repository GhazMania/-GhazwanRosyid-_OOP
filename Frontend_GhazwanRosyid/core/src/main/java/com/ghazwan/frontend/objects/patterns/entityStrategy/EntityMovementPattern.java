package com.ghazwan.frontend.objects.patterns.entityStrategy;

import com.ghazwan.frontend.objects.GameObject;

public interface EntityMovementPattern {
    // TODO (4 points): Declare the `move` method with the contract and parameters described above.
    public void move(GameObject entity, float delta);
}
