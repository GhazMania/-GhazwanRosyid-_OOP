package com.ghazwan.frontend.commands;

import com.ghazwan.frontend.objects.Player;
import com.ghazwan.frontend.systems.BulletManager;

public class ShootCommand implements Command {
    // TODO: Override `execute` with the following business logic:
    // - Check if player exists (is not null)
    // - If so, call the player's shootBullet(bulletManager) method

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        player.shootBullet(bulletManager);
    }
}
