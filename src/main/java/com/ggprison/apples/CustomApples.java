package com.ggprison.apples;

import com.ggprison.apples.listeners.ConsumeListener;
import com.ggprison.apples.managers.ApplesCooldownManager;
import com.ggprison.apples.managers.ApplesManager;

import me.colingrimes.midnight.Midnight;

/**
 * Main plugin class for CustomApples, extending the Midnight framework.
 * Initializes managers, registers event listeners, and handles plugin lifecycle.
 */
public class CustomApples extends Midnight {
    private static CustomApples instance;
    private ApplesCooldownManager cooldownManager;
    private ApplesManager applesManager;

    /**
     * Called when the plugin is enabled.
     * Initializes the plugin instance, managers, and registers event listeners.
     */
    @Override
    public void enable() {
        instance = this;
        applesManager = new ApplesManager();
        cooldownManager = new ApplesCooldownManager();

        getServer().getPluginManager().registerEvents(new ConsumeListener(this), this);
    }

    /**
     * Gets the singleton instance of the CustomApples plugin.
     *
     * @return the CustomApples instance
     */
    public static CustomApples getInstance() {
        return instance;
    }

    /**
     * Gets the cooldown manager for handling apple consumption cooldowns.
     *
     * @return the ApplesCooldownManager instance
     */
    public ApplesCooldownManager getCooldownManager() {
        return cooldownManager;
    }

    /**
     * Gets the apples manager for handling apple-related operations.
     *
     * @return the ApplesManager instance
     */
    public ApplesManager getApplesManager() {
        return applesManager;
    }
}