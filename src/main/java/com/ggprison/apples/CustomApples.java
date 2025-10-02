package com.ggprison.apples;

import org.bukkit.Bukkit;

import com.ggprison.apples.managers.ApplesCooldownManager;
import com.ggprison.apples.managers.ApplesManager;

import me.colingrimes.midnight.Midnight;

public class CustomApples extends Midnight {
    private static CustomApples instance;
    private ApplesCooldownManager cooldownManager;
    private ApplesManager applesManager;

    @Override
    public void enable() {
        instance = this;

        cooldownManager = new ApplesCooldownManager();
        applesManager = new ApplesManager(instance);
    }

    @Override
    public void disable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static CustomApples getInstance() {
        return instance;
    }

    public ApplesCooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public ApplesManager getApplesManager() {
        return applesManager;
    }
}