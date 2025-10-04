package com.ggprison.apples.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.colingrimes.midnight.config.annotation.Configuration;
import me.colingrimes.midnight.config.option.Option;
import static me.colingrimes.midnight.config.option.OptionFactory.keys;
import static me.colingrimes.midnight.config.option.OptionFactory.option;
import me.colingrimes.midnight.util.bukkit.Items;

/**
 * Defines the configuration interface for custom apples loaded from apples.yml.
 * Provides options to access apple configurations and their properties.
 */
@Configuration("apples.yml")
public interface Apples {
    /**
     * Option to retrieve a map of all apples, keyed by their identifiers.
     */
    Option<Map<String, Apple>> APPLES = keys("apples", Apple::of);

    /**
     * Option to retrieve a set of all apple identifiers from the configuration.
     */
    Option<Set<String>> APPLES_ALL = option("apples", section -> section.getKeys(false));

    /**
     * Represents a custom apple with its properties, including item, effects, broadcast flag, cooldown, and rarity.
     */
    class Apple {
        private final ItemStack item;
        private final String identifier;
        private final List<PotionEffect> effects;
        private final Boolean broadcast;
        private final long cooldown;
        private final String rarity;

        /**
         * Creates an Apple instance from a configuration section and its identifier.
         *
         * @param section    the configuration section containing apple properties
         * @param identifier the unique identifier of the apple
         * @return a new Apple instance
         */
        static Apple of(ConfigurationSection section, String identifier) {
            ItemStack item = Items.create().config(section.getConfigurationSection("item")).build();
            List<PotionEffect> effects = new ArrayList<>();
            List<?> effectsList = section.getList("effects");

            if (effectsList != null) {
                for (Object obj : effectsList) {
                    if (!(obj instanceof Map)) {
                        continue;
                    }

                    @SuppressWarnings("unchecked")
                    Map<String, Object> effectMap = (Map<String, Object>) obj;
                    String typeName = ((String) effectMap.getOrDefault("type", "")).toUpperCase(Locale.ROOT);

                    @SuppressWarnings("deprecation")
                    PotionEffectType effectType = PotionEffectType.getByName(typeName);
                    if (effectType == null) {
                        continue;
                    }

                    int level = ((Number) effectMap.getOrDefault("level", 1)).intValue();
                    int duration = ((Number) effectMap.getOrDefault("duration-seconds", 5)).intValue();

                    effects.add(new PotionEffect(effectType, duration * 20, Math.max(0, level - 1)));
                }
            }

            Boolean broadcast = section.getBoolean("broadcast", false);
            long cooldown = section.getLong("cooldown-seconds", 0L);
            if (cooldown < 0) cooldown = 0;

            String rarity = section.getString("rarity", "COMMON");
            return new Apple(item, identifier, Collections.unmodifiableList(effects), broadcast, cooldown, rarity);
        }

        /**
         * Constructs a new Apple instance with the specified properties.
         *
         * @param item       the ItemStack representing the apple
         * @param identifier the unique identifier of the apple
         * @param effects    the list of potion effects applied when the apple is consumed
         * @param broadcast  the flag indicating whether to broadcast a message when the apple is consumed
         * @param cooldown   the cooldown duration in seconds
         * @param rarity     the rarity of the apple
         */
        private Apple(
            ItemStack item,
            String identifier,
            List<PotionEffect> effects,
            Boolean broadcast,
            long cooldown,
            String rarity
        ) {
            this.item = item;
            this.identifier = identifier;
            this.effects = effects;
            this.broadcast = broadcast;
            this.cooldown = cooldown;
            this.rarity = rarity;
        }

        /**
         * Gets the ItemStack representing the apple.
         *
         * @return the apple's ItemStack
         */
        public ItemStack getItem() {
            return this.item;
        }

        /**
         * Gets the unique identifier of the apple.
         *
         * @return the apple's identifier
         */
        public String getIdentifier() {
            return this.identifier;
        }

        /**
         * Gets the list of potion effects applied when the apple is consumed.
         *
         * @return an unmodifiable list of potion effects
         */
        public List<PotionEffect> getEffects() {
            return effects;
        }

        /**
         * Gets the flag indicating whether to broadcast a message when the apple is consumed.
         *
         * @return true if a broadcast message should be sent, false otherwise
         */
        public Boolean getBroadcast() {
            return broadcast;
        }

        /**
         * Gets the cooldown duration in seconds for consuming the apple.
         *
         * @return the cooldown duration in seconds
         */
        public long getCooldown() {
            return cooldown;
        }

        /**
         * Gets the rarity of the apple.
         *
         * @return the rarity of the apple
         */
        public String getRarity() {
            return rarity;
        }
    }
}