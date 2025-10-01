package com.ggprison.managers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages cooldowns for players identified by UUIDs, associating cooldown keys with expiration times.
 * This class is thread-safe, using a {@link ConcurrentHashMap} to store player cooldown data.
 */
public class ApplesCooldownManager {
    private final Map<UUID, Map<String, Long>> map = new ConcurrentHashMap<>();

    /**
     * Sets a cooldown for a specific player and key with the given duration in seconds.
     * The cooldown expiration is calculated as the current time plus the specified seconds.
     *
     * @param player  the UUID of the player
     * @param key     the identifier for the cooldown
     * @param seconds the duration of the cooldown in seconds
     * @throws IllegalArgumentException if player or key is null, or seconds is negative
     */
    public void set(UUID player, String key, long seconds) {
        if (player == null || key == null) {
            throw new IllegalArgumentException("player UUID and key cannot be null");
        }

        if (seconds < 0) {
            throw new IllegalArgumentException("cooldown duration cannot be negative");
        }

        Map<String, Long> cooldowns = map.computeIfAbsent(player, k -> new ConcurrentHashMap<>());
        cooldowns.put(key, concurrentSeconds() + seconds);
    }

    /**
     * Checks if a player has an active cooldown for the specified key.
     * Removes expired cooldowns to optimize memory usage.
     *
     * @param player the UUID of the player
     * @param key    the identifier for the cooldown
     * @return true if the cooldown is active, false otherwise
     * @throws IllegalArgumentException if player or key is null
     */
    public boolean hasCooldown(UUID player, String key) {
        if (player == null || key == null) {
            throw new IllegalArgumentException("player UUID and key cannot be null");
        }

        Map<String, Long> cooldown = map.get(player);
        if (cooldown == null) return false;

        Long expiry = cooldown.get(key);
        if (expiry == null) return false;

        long current = concurrentSeconds();
        if (expiry <= current) {
            cooldown.remove(key);

            if (cooldown.isEmpty()) {
                map.remove(player);
            }

            return false;
        }

        return true;
    }

    /**
     * Retrieves the remaining time in seconds for a player's cooldown for the specified key.
     * If no cooldown exists or it has expired, returns 0. Removes expired cooldowns.
     *
     * @param player the UUID of the player
     * @param key    the identifier for the cooldown
     * @return the remaining cooldown time in seconds, or 0 if no active cooldown
     * @throws IllegalArgumentException if player or key is null
     */
    public long getRemainingCooldown(UUID player, String key) {
        if (player == null || key == null) {
            throw new IllegalArgumentException("player UUID and key cannot be null");
        }

        Map<String, Long> cooldown = map.get(player);
        if (cooldown == null) return 0;

        Long expiry = cooldown.get(key);
        if (expiry == null) return 0;

        long current = concurrentSeconds();
        long left = expiry - current;
        if (left <= 0) {
            cooldown.remove(key);

            if (cooldown.isEmpty()) {
                map.remove(player);
            }

            return 0;
        }

        return left;
    }

    /**
     * Returns the current time in seconds since the Unix epoch.
     *
     * @return the current time in seconds
     */
    private long concurrentSeconds() {
        return System.currentTimeMillis() / 1000L;
    }
}
