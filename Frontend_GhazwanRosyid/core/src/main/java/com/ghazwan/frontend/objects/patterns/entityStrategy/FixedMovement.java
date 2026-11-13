package com.ghazwan.frontend.objects.patterns.entityStrategy;

import com.ghazwan.frontend.objects.GameObject;

public class FixedMovement implements EntityMovementPattern {
    @Override
    public void move(GameObject entity, float delta) {
        // Fixed position: no positional displacement applied to entity
    }
}
