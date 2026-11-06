package com.ghazwan.frontend.commands;

import com.ghazwan.frontend.objects.Player;
import com.ghazwan.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}

